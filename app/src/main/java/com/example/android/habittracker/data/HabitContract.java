package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by ana on 23/06/2017.
 */

public class HabitContract {

    public static abstract class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_TYPE = "type";
        public static final String COLUMN_HABIT_DAY = "day";
        public static final String COLUMN_HABIT_ENJOY = "enjoy";

        /**
         * Possible values for the gender of the habit.
         */
        public static final int DAY_MONDAY = 0;
        public static final int DAY_TUESDAY = 1;
        public static final int DAY_WEDNESDAY = 2;
        public static final int DAY_THURSDAY = 3;
        public static final int DAY_FRIDAY = 4;
        public static final int DAY_SATURDAY = 5;
        public static final int DAY_SUNDAY = 6;

    }
}
