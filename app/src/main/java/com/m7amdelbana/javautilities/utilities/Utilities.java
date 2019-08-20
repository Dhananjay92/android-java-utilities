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
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import com.m7amdelbana.javautilities.app.App;
import com.m7amdelbana.javautilities.helpers.Logger;
import com.m7amdelbana.javautilities.local.LocaleManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class Utilities {

    public static Context getContext() {
        return App.getContext();
    }

    public static String errorToString(Throwable t) {
        StringBuilder errorMessage = new StringBuilder();
        if (errorMessage.length() == 0 && t.getCause() != null && t.getCause().getLocalizedMessage() != null)
            errorMessage.append(t.getCause().getLocalizedMessage());

        if (errorMessage.length() == 0 && t.getCause() != null && t.getCause().getMessage() != null)
            errorMessage.append(t.getCause().getMessage());

        if (errorMessage.length() == 0 && t.getLocalizedMessage() != null)
            errorMessage.append(t.getLocalizedMessage());

        if (errorMessage.length() == 0 && t.getMessage() != null)
            errorMessage.append(t.getMessage());

        if (errorMessage.length() == 0)
            errorMessage.append(t.getClass().getSimpleName());

        return errorMessage.toString();
    }

    public static String getLanguage() {
        LocaleManager localeManager = new LocaleManager(App.getContext());
        return localeManager.getAppLanguage();
    }

    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    public static String getDecodedString(String text) {
        if (!isNullString(text)) {
            try {
                text = URLDecoder.decode(text, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    public static void printMemoryState() {
        ActivityManager activityManager = (ActivityManager) App.getContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        Objects.requireNonNull(activityManager).getMemoryInfo(memoryInfo);

        Logger.instance().v("Memory", " memoryInfo.availMem " + memoryInfo.availMem + "\n", false);
        Logger.instance().v("Memory", " memoryInfo.lowMemory " + memoryInfo.lowMemory + "\n", false);
        Logger.instance().v("Memory", " memoryInfo.threshold " + memoryInfo.threshold + "\n", false);
        int memoryClass = activityManager.getMemoryClass();
        long heapSize = Runtime.getRuntime().totalMemory();
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        long heapFreeSize = Runtime.getRuntime().freeMemory();

        Logger.instance().v("Memory", "memoryClass:" + memoryClass + " " + Runtime.getRuntime().maxMemory(), false);
        Logger.instance().v("Memory", "Heap: heapSize= " + (heapSize / 1024 / 1024) + " MB heapMaxSize= " + (heapMaxSize / 1024 / 1024) + " MB   heapFreeSize= " + (heapFreeSize / 1024 / 1024) + " MB", false);
    }

    public static String getHtmlFromUrl(String urlString) {
        URL url;
        String inputLine = "";
        try {
            url = new URL(urlString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = "";
            while ((line = in.readLine()) != null)
                inputLine += line;
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputLine;
    }

    public static double getDouble(String str) {
        double value = 0.0;
        try {
            str = str.replaceAll(",", ".");
            value = Double.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static double getDouble(BigDecimal bigDecimal) {
        double value = 0.0;
        try {
            value = bigDecimal.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isNullString(String str) {
        return str == null || str.compareToIgnoreCase("null") == 0 || str.trim().length() <= 0;
    }

    public static boolean isNullList(List<?> list) {
        return list == null || list.size() <= 0;
    }

    public static void showToastMsg(String msg, int duration) {
        Toast toastMsg = Toast.makeText(getContext(), msg, duration);
        toastMsg.setGravity(Gravity.CENTER, 0, 0);
        toastMsg.show();
    }

    public static void printHashKey() {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashkey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Logger.instance().v("KeyHash:", hashkey, false);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void restartActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }
}