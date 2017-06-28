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
package com.larson.album.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by larsonzhong on 2017/6/27 0015.
 */

/**
 * view层唯一的任务就是点击事件的响应和将数据装载到视图中
 */
public interface LoadPhotoToViewInterface {
    void InitView();   //初始化View和设置监听，资源
    void showLoadingData();//正在加载listview时出现的动画效果，即出现“正在分析中”的画面
    void LoadDataFinish();//数据分析完成，即出现“分析完成”的画面
//    void MovingData();//正在移动图片出现的动画效果，即出现“正在移动中”的画面
    void LoadListviewSuccess(LinkedHashMap<String, ArrayList<String>> filemap);//将分析好的数据装载到listview中去，通过设配器
    void LoadlistviewFail(); //如果分析好的数据为空，做出相应的处理，显示当前无数据的页面
    void CreateDeleteDialog();  //弹出删除对话框
    void CreateMoveGroup(int x, int y, String path);    //创建一个可以移动的图片集
//    void ChangeGridViewItem();//将listview中的Gridview中的item变成网格状的格子


}
