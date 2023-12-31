package com.sclab.boot.paymentwalletapp.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static Date currentDate() {
        return plusDay(0);
    }

    public static Timestamp currentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    /**
     * @param yearAmount: 1
     * @return java.util.Date
     */
    public static Date addYearInDate(int yearAmount) {
        return plusYear(yearAmount);
    }

    public static Date addDayInDate(int dayAmount) {
        return plusDay(dayAmount);
    }

    private static Date plusYear(int yearAmount) {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().plusDays(yearAmount));
    }

    public static Date plusDay(int dayAmount) {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().plusDays(dayAmount));
    }

    public static Date plusDay(Date date, int dayAmount) {
        return Date.valueOf(date.toLocalDate().plusDays(dayAmount));
    }

    public static Date minusDay(int dayAmount) {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().minusDays(dayAmount));
    }

    public static Date minusDay(Date date, int dayAmount) {
        return Date.valueOf(date.toLocalDate().minusDays(dayAmount));
    }

    public static long differenceInDays(Date date1, Date date2) {
        long days = ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate());
        return days;
    }

    public static java.util.Date dateInSimpleFormat(String dateValue, String datePattern) throws ParseException {
        java.util.Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        date = simpleDateFormat.parse(dateValue);
        Date sqlStartDate = new Date(date.getTime());
        return sqlStartDate;
    }

    public static void sleep(int millisecond){
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}