package com.choonham.lck_manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InGameActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InGameActionFragment extends Fragment {
    public InGameActionFragment() {
        // Required empty public constructor
    }
    public static InGameActionFragment newInstance(String param1, String param2) {
        InGameActionFragment fragment = new InGameActionFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_game_action, container, false);
    }
}