package com.example.rdsh.testapp;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public class Values {

    public static final int DAY_MILLISECONDS = 86400000;
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat formatForTimeNow = new SimpleDateFormat("hh:mm");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd MMM yyyy");
}

