package com.bizaia.zhongyin.util;

import android.content.Context;

import java.io.File;

/**
 * Created by yan on 2017/3/13.
 */

public class FilePathUtils {

    public static final String DIR_DOWNLOAD = "/download";
    public static final String DIR_DOWNLOAD_PDF = DIR_DOWNLOAD + "/pdf";
    public static final String DIR_DOWNLOAD_PDF_IMG = DIR_DOWNLOAD + "/pdfimg";
    public static final String DIR_DOWNLOAD_UPDATE = DIR_DOWNLOAD + "/update";

    public static File getFileDir(Context context) {
        return new File(getFileDirPath(context));
    }

    public static String getFileDirPath(Context context) {
        String mainFilePath;
        File fileDataDir = context.getExternalFilesDir(null);
        if (fileDataDir != null) {
            mainFilePath = fileDataDir.getAbsolutePath();
        } else {
            mainFilePath = context.getFilesDir().getAbsolutePath();
        }
        return mainFilePath;
    }

    public static String getDirDownloadPdfPath(Context context) {
        FileUtils.createOrExistsDir(getFileDirPath(context) + DIR_DOWNLOAD_PDF);
        return getFileDirPath(context) + DIR_DOWNLOAD_PDF;
    }

    public static String getDirDownloadPdfImgPath(Context context) {
        FileUtils.createOrExistsDir(getFileDirPath(context) + DIR_DOWNLOAD_PDF_IMG);
        return getFileDirPath(context) + DIR_DOWNLOAD_PDF_IMG;
    }

    public static String getDirDownloadUpdatePath(Context context) {
        FileUtils.createOrExistsDir(getFileDirPath(context) + DIR_DOWNLOAD_UPDATE);
        return getFileDirPath(context) + DIR_DOWNLOAD_UPDATE;
    }

}
