/**
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 * by larsonzhong@163.com
 */
package com.larson.album.util;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.larson.album.app.Constant;
import com.larson.album.app.MyApplication;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtils {

    public static List<File> filelist = new ArrayList<File>();


    //------------------------------------------mediaSource---------------------
    /**
     * 获取当前手机手机中的默认相册源
     *
     * @return
     */
    public static ArrayList<File> getMediaSourcePath() {
        ArrayList<File> mediaSourcePath = new ArrayList<>();

        String sdPath = getSDPath2();
        mediaSourcePath.add(new File(sdPath+ Constant.Image_Path));
        mediaSourcePath.add(new File(sdPath+ Constant.Video_Path));

        return mediaSourcePath;
    }

    /**
     * 获取外置存储卡的根路径，如果没有外置存储卡，则返回null
     */
    private static String getSDPath2() {
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
//        return FileUtils.getSDPath(sd_default);
        return sd_default;
    }

    private static String getSDPath(String sd_default) {
        String sdcard_path = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.loggxl("can not find SD card");
            e.printStackTrace();
        }
        return sdcard_path;
    }

    //------------------------------------------mediaSource---------------------


    public static List<File> getSDMediaFiles() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<File> it = new ArrayList<>();

        for(File mediaDir:MyApplication.SourceList){
            if (mediaDir.listFiles() != null) {
                Log.i("path", "getSDMediaFiles:目录存在照片或视频 ");
                it.addAll(getFileList(mediaDir.getAbsolutePath()));
                filelist.clear();
            }
        }

        if (it.size() != 0) {
            Collections.sort(it, new FileComparator());
        }

        LogUtils.loggxl(it.size() + "文件长度");
        return it;
    }

    /**
     * 获取该目录下所有图片和视频文件
     *
     * @param strPath 文件源
     * @return 文件列表，包含图片和视频
     */
    public static List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    LogUtils.loggxl("find Directory");
                } else if (isImageFile(files[i].getPath())) {//图片
                    String strFileName = files[i].getAbsolutePath();
                    filelist.add(files[i]);
                    LogUtils.loggxl(strFileName);
                } else if (isVideoFile(files[i].getPath())) {//视频
                    String strFileName = files[i].getAbsolutePath();
                    filelist.add(files[i]);
                    LogUtils.loggxl(strFileName);
                }
            }
        }
        return filelist;
    }

    public static boolean isVideoFile(String fName) {
        boolean re;
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();
        if (end.equals("mp4") || end.equals("3gp") || end.equals("avi")||end.equals("3gpp") ) {
            re = true;
        } else {
            re = false;
        }
        return re;
    }


    public static boolean isImageFile(String fName) {
        boolean re;
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();
        if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            re = true;
        } else {
            re = false;
        }
        return re;
    }


    public static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {
            if (paishedate(lhs).before(paishedate(rhs)))
                return 1;
            else if (paishedate(lhs).after(paishedate(rhs)))
                return -1;
            else
                return 0;
        }
    }


    /**
     * 获取照片信息的经纬度和拍摄时间
     *
     * @param list
     * @return
     */
    public static String[] getFileLocation(ArrayList<String> list) {
        String[] information = new String[3];
        ExifInterface exif;
        for (int i = 0; i < Math.sqrt(list.size()); i++) {
            try {
                exif = new ExifInterface(list.get(i));
                information[0] = String.valueOf("nothing");
                information[1] = String.valueOf("nothing");
                information[2] = lastModifiedToDate(new File(list.get(i)));
                if (!TextUtils.isEmpty(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE))) {
                    final float[] latlons = {-1, -1};
                    exif.getLatLong(latlons);
                    information[0] = String.valueOf(latlons[0]);
                    information[1] = String.valueOf(latlons[1]);
                    //TODO 这里需要根据经纬度变成城市名
                    //fixme
                    return information;
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return information;
    }

    public static String lastModifiedToDate(File file) {
        String fileTimeStr = null;
        try {
            Date date1;
            String date = DateUtils.converToString(file.lastModified());
            if (!TextUtils.isEmpty(date)) {
                date1 = DateUtils.convertToDate(date);
            } else {
                date1 = DateUtils.convertToDate("1995:03:13 22:38:20");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            fileTimeStr = df.format(date1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileTimeStr;
    }


    public static Date paishedate(File file) {
        ExifInterface exif = null;
        Date date1 = null;
        try {
            exif = new ExifInterface(file.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
        try {
            if (!TextUtils.isEmpty(date)) {
                date1 = DateUtils.convertToDate(date);
            } else {
                date1 = DateUtils.convertToDate("1995:03:13 22:38:20");
            }
            Log.i("date", date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date1;
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
        }
    }

    public static void deleteFilelist(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            deleteFile(new File(list.get(i)));
        }
    }


    public static ArrayList<String> getExistFileList(String path) {
        ArrayList<String> filelist = new ArrayList<String>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                    filelist.add(files[i].getAbsolutePath());
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }


    public static ArrayList<String> getExistImageList(String path) {
        ArrayList<String> filelist = new ArrayList<String>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                filelist.add(files[i].getAbsolutePath());
            }
        }
        return filelist;
    }


    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}