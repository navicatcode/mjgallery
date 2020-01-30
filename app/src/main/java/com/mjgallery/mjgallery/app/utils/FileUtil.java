package com.mjgallery.mjgallery.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {

    public static void copyFile(File source, File dest, String filePath, String fileName, @NonNull Context context) {
        try (FileInputStream fileInputStream = new FileInputStream(source);
             FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
    }

    public static boolean isFile(String filePath) {
        return new File(filePath).isFile();
    }
}
