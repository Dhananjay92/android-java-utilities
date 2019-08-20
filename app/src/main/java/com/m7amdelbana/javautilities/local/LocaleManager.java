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

package com.m7amdelbana.javautilities.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class LocaleManager {

    private static final String TAG = "LocaleManager";

    private Context ctx;
    private Resources res;
    private SharedPreferences settingPrefs;

    public LocaleManager(Context ctx) {
        this.ctx = ctx;
        this.res = ctx.getResources();
    }

    public void restoreAppLanguage() {
        Log.v(TAG, "restoreAppLanguage");
        settingPrefs = ctx.getSharedPreferences("ConfigData", MODE_PRIVATE);
        String lang = settingPrefs.getString("AppLanguage", "");
        if (!settingPrefs.getAll().isEmpty() && lang.length() == 2) {
            Locale myLocale;
            myLocale = new Locale(lang);
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.locale = myLocale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    public void storeAppLanguage(String lang) {
        Log.v(TAG, "storeAppLanguage -> " + lang);
        settingPrefs = ctx.getSharedPreferences("ConfigData", MODE_PRIVATE);
        SharedPreferences.Editor ed = settingPrefs.edit();
        Locale myLocale;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        ed.putString("AppLanguage", lang);
        ed.apply();
    }

    public void setAppLanguage(String lang) {
        Log.v(TAG, "setAppLanguage -> " + lang);
        Locale myLocale;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public String getAppLanguage() {
        String APP_LANG;
        settingPrefs = ctx.getSharedPreferences("ConfigData", MODE_PRIVATE);
        String lang = settingPrefs.getString("AppLanguage", "");
        if (!settingPrefs.getAll().isEmpty() && lang.length() == 2) {
            APP_LANG = lang;
        } else {
            APP_LANG = Locale.getDefault().getLanguage();
        }
        Log.v(TAG, "getAppLanguage = " + APP_LANG);
        return APP_LANG;
    }

    public void setStoredLanguage() {
        Log.v(TAG, "setStoredLanguage");
        Locale locale = new Locale(getAppLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        ctx.getResources().updateConfiguration(config, null);
    }
}