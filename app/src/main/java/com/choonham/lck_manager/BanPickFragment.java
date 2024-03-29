package com.choonham.lck_manager;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BanPickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BanPickFragment extends Fragment {

    Button compButton;

    public BanPickFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BanPickFragment newInstance() {
        BanPickFragment fragment = new BanPickFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ban_pick, container, false);

        compButton = rootView.findViewById(R.id.comp_button);

        compButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(2, null);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}