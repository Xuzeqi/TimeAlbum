package com.larson.album.view;

import com.larson.album.data.LoadResultListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/5/15 0015.
 */
public interface LoadPhotoToViewModelInterface {
   void loadMediaFilePathList(LoadResultListener loadResultListener);
   ArrayList<String> GetNeedMoveFile();
   LinkedHashMap<String, ArrayList<String>> GetDataChangedFile();
}
