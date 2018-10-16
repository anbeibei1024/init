package com.dashen.init.common.network.appUpdate;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;

/**
 * https://blog.csdn.net/qq_30574785/article/details/79024949
 */

public class comm {
    public static File getPathFile(String path) {
        String apkName = path.substring(path.lastIndexOf("/"));
        File outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), apkName);
        return outputFile;
    }

    public static void rmoveFile(String path) {
        File file = getPathFile(path);
        file.delete();
    }


//    public void uninstall(View v){
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_DELETE);
//        intent.setData(Uri.parse("package:com.njupt.htmlui1"));
//        startActivity(intent);
//    }
}
