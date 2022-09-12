package com.choonham.lck_manager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.choonham.lck_manager.dao.UserDAO;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.room.AppDatabase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginActivity extends AppCompatActivity {

    private final ActivityTagEnum TAG = ActivityTagEnum.LOGIN_ACTIVITY;

    public GoogleSignInClient mGoogleSignInClient;
    UserDAO userDAO;
    AppDatabase db;

    private FirebaseAuth mAuth = null;
    private static final int RC_SIGN_IN = 9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        AndPermission.with(this)
                .runtime()
                .permission(Permission.GET_ACCOUNTS)
                .start();

        // 로그인 이력 확인을 위한 gsa
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();

        updateUI(mAuth.getCurrentUser());

        // Build a GoogleAccountHolder with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(view -> {
            signIn();
        });


    }

    /**
     * 로그인 완료 후, Main Activity 로 화면 이동
     */
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Main Activity 로 이동 후, Google 계정 정보 DB에 저장
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        db = AppDatabase.getInstance(this);

        if (requestCode == RC_SIGN_IN) {

            // The Task returned from this call is always completed, no need to attach
            // a listener.
            userDAO = db.userDAO();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                String personName = account.getDisplayName();
                String personId = account.getId();
                String personEmail = account.getEmail();

                firebaseAuthWithGoogle(account);

                userDAO.countUserEntitiesByUserID(personId)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(count -> {
                            Log.d("count: ", count.toString());

                            if (count == 0) {
                                UserEntity userEntity = new UserEntity();
                                userEntity.setUserId(personId);
                                userEntity.setUserEmail(personEmail);
                                userEntity.setUserName(personName);

                                insertUserIDInfo(userEntity);

                                Intent intent = new Intent(this, InitialSettingActivity.class);
                                intent.putExtra("userId", personId);
                                intent.putExtra("userEmail", personEmail);
                                intent.putExtra("userName", personName);

                                startActivity(intent);
                            }

                        })
                        .doOnError(error -> {
                            Log.e("auth error :", error.getMessage());
                        })
                        .subscribe();
            } catch (Exception e) {
                Log.e("error: ", e.getMessage());
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }

    /**
     * 회원 정보 삭제(설정에 추가 예정)
     */
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AccountManager am = AccountManager.get(getApplication());
                        Account[] accounts = am.getAccountsByType("com.google");

                        for (Account account : accounts) {
                            am.removeAccount(account, null, null);
                            Log.d("removed account: ", account.name);
                        }

                    }
                });
    }

    private void insertUserIDInfo(UserEntity userEntity) {

        userDAO.insertUserEntity(userEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(insertValue -> {
                    Log.d("Insert data: ", insertValue.toString());
                    checkInsertYN(insertValue);
                })
                .doOnError(error -> {
                    Log.e("insert error :", error.getMessage());
                })
                .subscribe();
    }

    private void checkInsertYN(Long insertCode) {
        userDAO.loadUserEntityById(insertCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    Log.d("insertedID", loadValue.getUserId());
                })
                .doOnError(error -> {
                    Log.e("check error :", error.getMessage());
                })
                .subscribe();
    }


}
