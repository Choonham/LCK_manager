package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.dao.PlayerDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.dao.TransferWindowDAO;
import com.choonham.lck_manager.entity.ChampionCounterEntity;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.TeamEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.joinedEntity.JoinedTransferWindow;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TransferWindow extends Fragment {
    private final ActivityTagEnum TAG = ActivityTagEnum.TRANSFER_WINDOW;

    ListView transferWindowListView;

    private List<JoinedTransferWindow> transferWindowEntityList;

    private ImageButton refresh;

    private AppDatabase appDatabase;

    boolean isTransferListLoad = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transfer_window, container, false);

        appDatabase = AppDatabase.getInstance(getContext());

        //ommon common = Common.getInstance();
        //playerEntityList =  common.getTempPlayerList(1);

        transferWindowEntityList = new ArrayList<>();

        loadTransferList();

        while(!isTransferListLoad) {}

        TransferWindowListAdapter transferWindowListAdapter = new TransferWindowListAdapter(getContext(), transferWindowEntityList);
        transferWindowListView = view.findViewById(R.id.weekly_transfer_window_list_view);

        transferWindowListView.setAdapter((ListAdapter) transferWindowListAdapter);

        refresh = view.findViewById(R.id.transfer_window_refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DD", "Hey!");
            }
        });

        transferWindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                intent.putExtra("tagInt",1122);

                intent.putExtra("tag", ActivityTagEnum.TRANSFER_WINDOW);

                intent.putExtra("playerEntity", transferWindowEntityList.get(i).playerEntity);

                intent.putExtra("seasonEntity", transferWindowEntityList.get(i).seasonEntity);

                intent.putExtra("transferWindowEntity", transferWindowEntityList.get(i).transferWindowEntity);

                startActivity(intent);
            }
        });

        return view;
    }

    public void loadTransferList() {
        TransferWindowDAO transferWindowDAO = appDatabase.transferWindowDAO();
        PlayerDAO playerDAO = appDatabase.playerDAO();

        transferWindowDAO.loadTransferWindowByWeek(1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(JoinedTransferWindow transferWindow : loadValue) {
                        transferWindowEntityList.add(transferWindow);
                    }

                    isTransferListLoad = true;
                })
                .doOnError(error -> {
                    Log.e("loadTransferList error:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }



}
