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

package com.m7amdelbana.javautilities.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.m7amdelbana.javautilities.local.LocaleManager;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;
    @SuppressLint("StaticFieldLeak")
    private static App application = null;
    @SuppressLint("StaticFieldLeak")
    private static LocaleManager localeManager;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        application = this;

        localeManager = new LocaleManager(context);
        localeManager.storeAppLanguage(localeManager.getAppLanguage());
    }

    public static Context getContext() {
        return context;
    }

    public static App getApplication() {
        return application;
    }

    public static LocaleManager getLocaleManager() {
        return localeManager;
    }
}
