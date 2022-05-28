package com.choonham.lck_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;
import com.choonham.lck_manager.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.HashSet;

public class LeagueScheduleDecorator implements DayViewDecorator {

    private final Drawable calenderSelectBorder;
    ArrayList<CalendarDay> matchDays;
    public LeagueScheduleDecorator(Context context, ArrayList<CalendarDay> matchDays) {

        this.calenderSelectBorder =  context.getResources().getDrawable(R.drawable.calender_selector, null);
        this.matchDays = matchDays;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return (matchDays.contains(day));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(calenderSelectBorder);

    }
}
