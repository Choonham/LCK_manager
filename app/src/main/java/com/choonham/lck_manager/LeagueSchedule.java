package com.choonham.lck_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class LeagueSchedule extends Fragment {
    private MaterialCalendarView calendarView;

    ArrayList<CalendarDay> matchDayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_schedule, container, false);
        calendarView = view.findViewById(R.id.league_schedule_calender);
        matchDayList.add(CalendarDay.from(2022, 4, 25));
        matchDayList.add(CalendarDay.from(2022, 4, 26));
        matchDayList.add(CalendarDay.from(2022, 4, 27));
        matchDayList.add(CalendarDay.from(2022, 4, 28));
        calendarView.addDecorators(new LeagueScheduleDecorator(getContext(), matchDayList));
        DayFormatter dayFormatter = new DayFormatter() {
            @NonNull
            @NotNull
            @Override
            public String format(@NonNull @NotNull CalendarDay day) {
                return " ";
            }
        };

        calendarView.setDayFormatter(dayFormatter);
        return view;
    }

}
