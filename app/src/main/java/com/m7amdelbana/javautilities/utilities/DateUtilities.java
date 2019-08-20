//        MIT License
//
//        Copyright (c) 2019 Mohamed Elbana
//        @m7amdelbana
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package com.m7amdelbana.javautilities.utilities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilities {

    public static boolean isDateInPast(Date date) {
        if (date == null)
            return true;
        Date nowDate = Calendar.getInstance().getTime();
        return date.before(nowDate);
    }

    public static int getDaysBetweenDates(Date date1, Date date2) {
        float days = ((float) (date2.getTime() - date1.getTime()) / (1000.0f * 60.0f * 60.0f * 24.0f));
        return (int) Math.ceil(days);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        boolean sameYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
        boolean sameMonth = calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        boolean sameDay = calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
        return (sameDay && sameMonth && sameYear);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateOnly(String dateInString) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            if (dateInString != null) date = formatter.parse(dateInString);
            else return "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        return formatter2.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHoursOnly(String dateInString) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm");
        assert date != null;
        return formatter2.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHoursBase12(String dateInString) {
        Date date = null;
        String extension;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatterHours = new SimpleDateFormat("hh");
        SimpleDateFormat formatterMinutes = new SimpleDateFormat("mm");
        int minutes = Integer.parseInt(formatterMinutes.format(date));
        int hours = Integer.parseInt(formatterHours.format(date));
        if (hours >= 12) {
            extension = " AM";
            hours -= 12;
        } else extension = " PM";
        return hours + ":" + minutes + extension;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getEndHours(String dateInString, int duration) {
        Date date = null;
        String extension;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatterHours = new SimpleDateFormat("hh");
        SimpleDateFormat formatterMinutes = new SimpleDateFormat("mm");
        int minutes = Integer.parseInt(formatterMinutes.format(date));
        int hours = Integer.parseInt(formatterHours.format(date));
        int hoursAdd = 0;
        while (duration > 60) {
            duration = duration - 60;
            hoursAdd += 1;
        }
        hours += hoursAdd;
        if (hours >= 12) {
            extension = " AM";
            hours -= 12;
        } else extension = " PM";
        return hours + ":" + (minutes + duration) + extension;
    }
}
