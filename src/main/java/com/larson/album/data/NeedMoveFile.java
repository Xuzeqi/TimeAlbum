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
package com.larson.album.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeedMoveFile {

    public static int choose_item=-1;

    //当前选中的照片地址
    public static ArrayList<String> needmoveFile = new ArrayList<String>();

    public static ArrayList<String> getNeedMoveFile() {
        return needmoveFile;
    }



    public static void addNeedmovefile(String filepath) {
        if (!isinNeedmovefile(filepath))
            needmoveFile.add(filepath);
    }

    public static void removeall() {
        needmoveFile.clear();
    }

    public static void addNeedmovefileList(List<String> filepathlist) {
        for (String string : filepathlist) {
            addNeedmovefile(string);
        }
    }

    public static void removeNeedmovefileList(List<String> filepathlist) {
        for (String string : filepathlist) {
            removefile(string);
        }
    }

    public static void removefile(String filepath) {
        if (isinNeedmovefile(filepath))
            needmoveFile.remove(filepath);
    }

    public static Boolean isinNeedmovefile(String filepath) {
        Log.i("taoshi_yiyou", needmoveFile.size()+"");
        for (String item : needmoveFile) {
            if (filepath.equals(item))
                return true;
        }
        return false;
    }

    public static  Boolean isExistinList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (needmoveFile.contains(list.get(i))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static Map<String, Integer> positemap = new HashMap<String, Integer>();

    public static ArrayList<String> needdeleteFile=new ArrayList<String>();

    public static ArrayList<String>  needdeletePhoto=new ArrayList<String>();

    public static boolean getPositemap(int posite) {
        if (positemap.get(String.valueOf(posite)) == null) {
            return false;
        } else {
            int i = positemap.get(String.valueOf(posite));
            if (i == 1)
                return true;
            else
                return false;
        }
    }

    public static void putPositemap(int posite, Integer item) {
        positemap.put(String.valueOf(posite), item);
    }

    public static void clearPositemap() {
        positemap.clear();
    }

    /**
     * 判断当前需要删除的list是够包含filepath
     * @param filepath
     * @return
     */
    public static Boolean isinNeeddeletefile(String filepath) {
        for (String item : needdeleteFile) {
            if (filepath.equals(item))
                return true;
        }
        return false;
    }

    /**
     * 添加一个filepath到需要删除的list中
     * @param filepath
     */
    public static void addNeeddeletefile(String filepath) {
        if (!isinNeedmovefile(filepath))
            needdeleteFile.add(filepath);
    }

    /**
     *  在需要删除的list中移除filepath
     * @param filepath
     */
    public static void removedeletefile(String filepath) {
        if (isinNeeddeletefile(filepath))
            needdeleteFile.remove(filepath);
    }

    /**
     * 获取所有需要删除的list
     * @return
     */
    public static ArrayList<String> getdeletefile() {
        return needdeleteFile;
    }

    /**
     * 移除所有需要删除的list
     */

    public static void removealldeletefile() {
        needdeleteFile.clear();
    }


    /**
     * 判断当前需要删除的list是够包含filepath
     * @param filepath
     * @return
     */
    public static Boolean isinNeeddeletePhoto(String filepath) {
        for (String item : needdeletePhoto) {
            if (filepath.equals(item))
                return true;
        }
        return false;
    }

    /**
     * 添加一个filepath到需要删除的list中
     * @param filepath
     */
    public static void addNeeddeletePhoto(String filepath) {
        if (!isinNeedmovefile(filepath))
            needdeletePhoto.add(filepath);
    }

    /**
     *  在需要删除的list中移除filepath
     * @param filepath
     */
    public static void removedeletePhoto(String filepath) {
        if (isinNeeddeletePhoto(filepath))
            needdeletePhoto.remove(filepath);
    }

    /**
     * 获取所有需要删除的list
     * @return
     */
    public static ArrayList<String> getdeletePhoto() {
        return needdeletePhoto;
    }

    /**
     * 移除所有需要删除的list
     */

    public static void removealldeletePhoto() {
        needdeletePhoto.clear();
    }




    //将要添加到相册源
    public static ArrayList<String> WillAddPhotoSource=new ArrayList<String>();

    public static ArrayList<String> getWillAddPhotoSource() {
        return WillAddPhotoSource;
    }

    public static void AddWillAddPhotoSource(String filepath) {
        if (!IsWillAddPhotoSource(filepath))
            WillAddPhotoSource.add(filepath);
    }

    public static void RemoveAllWillAddPhotoSource() {
        WillAddPhotoSource.clear();
    }

    public static void RemoveWillAddPhotoSource(String filepath) {
        if (IsWillAddPhotoSource(filepath))
            WillAddPhotoSource.remove(filepath);
    }

    public static Boolean IsWillAddPhotoSource(String filepath) {
        for (String item : WillAddPhotoSource) {
            if (filepath.equals(item))
                return true;
        }
        return false;
    }


}
