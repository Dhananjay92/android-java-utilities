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

package com.m7amdelbana.javautilities.helpers;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.m7amdelbana.javautilities.utilities.Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Logger {

    private static Logger _instance = null;
    private BufferedWriter write = null;
    private boolean canWrite = false;

    static public Logger instance() {
        if (_instance == null) _instance = new Logger();
        return _instance;
    }

    private Logger() {
        File root = Environment.getExternalStorageDirectory();
        if (root.canRead() && root.canWrite()) {
            String loggerFileName = "Base_Logger.txt";
            File toWriteFile = new File(root, loggerFileName);
            try {
                write = new BufferedWriter(new FileWriter(toWriteFile, true));
                canWrite = true;
            } catch (IOException e) {
                Log.v("Create Logger File", Objects.requireNonNull(e.getMessage()));
            }
        }
    }

    public String getLoggingTime() {
        String currentTime;
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        currentTime = sdf.format(cal.getTime());
        return currentTime;
    }

    public void v(String tag, Object msg) {
        v(tag, msg, false);
    }

    public void v(String tag, Object msg, boolean writeToFile) {
        Log.v("MyApp " + tag, msg + "");
        if (canWrite && writeToFile) {
            try {
                write.write(tag + "\t" + getLoggingTime() + "\t\t" + msg);
                write.newLine();
                write.flush();
            } catch (IOException e) {
                Log.v("verbose log", Objects.requireNonNull(e.getMessage()));
            }
        }
    }

    public void e(String tag, Object msg, boolean writeToFile) {
        Log.e(tag, msg + "");
    }

    public static void updateLogFile(File fileName, int newLength) {
        BufferedWriter write;
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(fileName, "rw");
            long length = randomAccessFile.length();
            if (length > (newLength)) {
                System.out.println(getFileSize(fileName) + " " + length);
                randomAccessFile.seek(newLength);
                byte[] buffer = new byte[((int) length - newLength)];
                randomAccessFile.read(buffer);
                String newStr = new String(buffer);
                System.out.println(newStr);
                randomAccessFile.close();

                String[] temp = newStr.split("\n");
                System.out.println("temp " + temp.length);

                write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
                write.write(newStr.replaceFirst(temp[0], ""));
                write.flush();
                write.close();
            }
            randomAccessFile.close();
        } catch (IOException ex) {
            Log.v("UpdateFile", Objects.requireNonNull(ex.getMessage()));
        }
    }

    public static long getFileSize(File file) {
        if (!file.exists() || !file.isFile()) {
            System.out.println("File doesn\'t exist");
            return -1;
        }
        return file.length();
    }

    public void logFullMessage(String tag, Object message) {
        if (!Utilities.isNullString(message.toString()) && message.toString().length() > 4000) {
            int chunkCount = message.toString().length() / 4000;
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= message.toString().length()) {
                    v(tag, message.toString().substring(4000 * i), false);
                } else {
                    v(tag, message.toString().substring(4000 * i, max), false);
                }
            }
            return;
        }
        v(tag, message + "", false);
    }
}
