package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IssueEffectAdapter extends BaseAdapter {

    String[] effectsList = {"Transfer Window에서 Offer 가격", "3경기 주전 사용 금지 및 경험치 하락"};
    String[] effectsIndexList = {"- 30%", "- 100xp"};

    private LayoutInflater inflater;
    private Context context;

    public IssueEffectAdapter(Context context) {
        this.context = context;

        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return effectsList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(context != null) {
            view = inflater.inflate(R.layout.issue_effect_list_item, viewGroup, false);
        }

        TextView issue_effect_context = view.findViewById(R.id.issue_effect_context);
        TextView issue_effect_index = view.findViewById(R.id.issue_effect_index);

        issue_effect_context.setText(effectsList[i]);
        issue_effect_index.setText(": " + effectsIndexList[i]);
        return view;
    }
}
