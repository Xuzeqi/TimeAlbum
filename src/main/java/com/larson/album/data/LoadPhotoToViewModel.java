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

import android.os.AsyncTask;
import com.larson.album.util.FileUtils;
import com.larson.album.view.LoadPhotoToViewModelInterface;

import java.io.File;
import java.util.*;

/**
 * 数据加载器
 * Created by larsonzhong on 2017/6/29 0015.
 */
public class LoadPhotoToViewModel implements LoadPhotoToViewModelInterface {
    LoadResultListener mLoadResultListener;
    LinkedHashMap<String, ArrayList<String>> mFilemap;

    @Override
    public void loadMediaFilePathList(LoadResultListener loadResultListener) {
        mLoadResultListener = loadResultListener;
        new GetMediaFilesPaths().execute();
    }

    @Override
    public ArrayList<String> GetNeedMoveFile() {
        return NeedMoveFile.getNeedMoveFile();
    }

    /**
     * 获取listview删除item过后的数据地址
     * @return
     */
    public LinkedHashMap<String, ArrayList<String>>  GetDataChangedFile()
    {
        List<String> list = NeedMoveFile.getNeedMoveFile();
        List<String> filelist = new ArrayList<String>();
        for (String key : mFilemap.keySet()) {
            ArrayList<String> map = mFilemap.get(key);
            for (String file : list) {
                map.remove(file);
            }
            if (map.size() == 0)
                filelist.add(key);
        }
        for (String string : filelist) {
            Iterator<Map.Entry<String, ArrayList<String>>> it = mFilemap
                    .entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, ArrayList<String>> entry = it.next();
                String key = entry.getKey();
                if (key.equals(string)) {
                    it.remove();
                }
            }
        }
        NeedMoveFile.removeall();
        NeedMoveFile.clearPositemap();
        return mFilemap;
    }


    /**
     * 获取当前手机中按时间排序的媒体文件地址集
     */
    class GetMediaFilesPaths extends AsyncTask<Void, Integer, Void> {
        List<File> listfile = new ArrayList<>();
        LinkedHashMap<String, ArrayList<String>> filemap;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                listfile = FileUtils.getSDMediaFiles();
            } catch (Exception e) {
                e.printStackTrace();
            }
            filemap = new LinkedHashMap<>();
            if (listfile.size() != 0) {
                String date = FileUtils.lastModifiedToDate(listfile.get(0));
                filemap.put(date, new ArrayList<String>());
                filemap.get(FileUtils.lastModifiedToDate(listfile.get(0))).add(
                        listfile.get(0).getAbsolutePath());
                for (int i = 1; i < listfile.size(); i++) {
                    if (!filemap.containsKey(FileUtils.lastModifiedToDate(listfile
                            .get(i)))) {
                        filemap.put(FileUtils.lastModifiedToDate(listfile.get(i)),
                                new ArrayList<String>());
                        filemap.get(FileUtils.lastModifiedToDate(listfile.get(i)))
                                .add(listfile.get(i).getAbsolutePath());
                    } else {
                        filemap.get(FileUtils.lastModifiedToDate(listfile.get(i)))
                                .add(listfile.get(i).getAbsolutePath());
                    }
                }
            }
            mFilemap=filemap;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mLoadResultListener.LoadSuccess(filemap);
            super.onPostExecute(aVoid);
        }
    }
}
