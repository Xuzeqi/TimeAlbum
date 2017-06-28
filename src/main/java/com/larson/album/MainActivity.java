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
package com.larson.album;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.larson.album.data.NeedMoveFile;
import com.larson.album.presenter.LoadPhotoToViewPresenter;
import com.larson.album.view.DialogBuilder;
import com.larson.album.view.LoadPhotoToViewActivity_ListView_Adapter;
import com.larson.album.view.LoadPhotoToViewInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends Activity implements LoadPhotoToViewInterface, View.OnClickListener {
    private Context mContext;

    private LinkedHashMap<String, ArrayList<String>> mfilemap;
    private List<String> datelist = new ArrayList<String>();

    private LoadPhotoToViewActivity_ListView_Adapter listviewadapter;


    private LoadPhotoToViewPresenter mLoadPhotoToViewPresenter;
    private ListView mListview;


    /**
     * 分析中对话框
     * 正在移动中对话框
     * 分析完成对话框
     */
    Dialog LoadingDialog;
    Dialog FinishDialog;

    /**
     * 显示当前选中的照片数量,控制该布局是否显示
     */
    RelativeLayout show_move_detail;
    TextView chose_text;
    TextView quit;
    TextView delete;

    /**
     * listview显示区域用来添加白色的遮罩
     */

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//用来取消状态栏
        setContentView(R.layout.activity_main);
        mContext = this;

        InitView();//初始化View和设置监听，资源

        mLoadPhotoToViewPresenter = new LoadPhotoToViewPresenter(this);
        mLoadPhotoToViewPresenter.InitListView();
    }



    //初始化View和设置监听，资源
    @Override
    public void InitView() {

        mListview = (ListView) findViewById(R.id.mylistview);
        show_move_detail = (RelativeLayout) findViewById(R.id.show_move_detail);
        chose_text = (TextView) findViewById(R.id.chose_text);
        quit = (TextView) findViewById(R.id.quit);
        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.listview_area);
        quit.setOnClickListener(this);

        LoadingDialog = DialogBuilder.createLoadingDialog(mContext, "正在分析照片");
        FinishDialog = DialogBuilder.createLoadingfinishDialog(mContext, "已完成");
    }

    /**
     * 正在加载listview时出现的动画效果，即出现“正在分析中”的画面
     */
    @Override
    public void showLoadingData() {
        LoadingDialog.show();
    }

    /**
     * 数据分析完成，即出现“分析完成”的画面
     */
    @Override
    public void LoadDataFinish() {
        LoadingDialog.dismiss();
        FinishDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FinishDialog.dismiss();
            }
        }, 1000);
    }


    /**
     * 将分析好的数据装载到listview中去，通过设配器
     *
     * @param filemap
     */

    @Override
    public void LoadListviewSuccess(LinkedHashMap<String, ArrayList<String>> filemap) {
        mfilemap = filemap;
        if (mfilemap.size() > 0) {
            datelist.clear();
            for (String key : mfilemap.keySet()) {
                datelist.add(key);
            }
        }
        listviewadapter = new LoadPhotoToViewActivity_ListView_Adapter(mContext, filemap, new LoadPhotoToViewActivity_ListView_Adapter.show_choose_detail_Listener() {
            @Override
            public void show_choose_detail_linearlayout(int size) {
                show_move_detail.setVisibility(View.VISIBLE);
                chose_text.setText("已选" + size + "张");
            }

            @Override
            public void hide_choose_detail_linearlayout() {
                show_move_detail.setVisibility(View.GONE);
            }
        });
        mListview.setAdapter(listviewadapter);

        listviewadapter.setGroup(new LoadPhotoToViewActivity_ListView_Adapter.IMovephotoGroup() {

            @Override
            public void CreateMoveGroup(int x, int y, String path) {
                mLoadPhotoToViewPresenter.CreateMoveGroup(x, y, path);
            }
        });
    }

    @Override
    public void LoadlistviewFail() {
        relativeLayout.setBackgroundResource(R.drawable.yujiazai);
        mListview.setVisibility(View.GONE);
    }

    @Override
    public void CreateDeleteDialog() {
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.delete_main_dialog, null);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth());
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(dialogView);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (NeedMoveFile.needmoveFile.size() != 0) {
                    show_move_detail.setVisibility(View.VISIBLE);
                } else {
                    show_move_detail.setVisibility(View.GONE);
                }
            }
        });
        RelativeLayout quxiao = (RelativeLayout) dialogView.findViewById(R.id.quxiao);
        RelativeLayout queding = (RelativeLayout) dialogView.findViewById(R.id.queding);
        TextView delete_text = (TextView) dialogView.findViewById(R.id.shanchutext);
        delete_text.setText("确定删除" + NeedMoveFile.needmoveFile.size() + "个文件?");
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadPhotoToViewPresenter.executeDeleteFileTask();
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    /**
     * 创建一个可以移动的图片集
     *
     * @param x
     * @param y
     * @param path
     */

    @Override
    public void CreateMoveGroup(int x, int y, String path) {
//        Dragview_flag = 1;
//        ListviewIsFirstUp = false;
//        show_move_detail.setVisibility(View.GONE);
//        relativeLayout.setBackgroundResource(R.drawable.white_copy);
//        mjianliImageview.setImageResource(R.drawable.jianli_green);
//        jiantou.setVisibility(View.VISIBLE);
//        mJiantouFlag = mJiantouFlickerTrue;
//        setFlickerAnimation(jiantou, 1, 0);
//        group = new MovePhotoGroup(
//                LoadPhotoToViewActivity.this);
//        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                ScreenUtils.dip2px(LoadPhotoToViewActivity.this, 108),
//                ScreenUtils.dip2px(LoadPhotoToViewActivity.this, 108));
//        lp.topMargin = x - 10;
//        lp.leftMargin = y - 35;
//        int listviewlinearlayout_top = listviewlinearlayout.getTop();
//        int listview_top = mListview.getTop();
//        int listviewframelayout_top = listview_framelayout.getTop();
//        top_area_height = listview_top + listviewlinearlayout_top + listviewframelayout_top;
//        view = new DragView(LoadPhotoToViewActivity.this,
//                BitmapUtils.fileTobitmap(new File(path), 206, 206), Pos, chakanPos, top_area_height, mListview.getLeft());
//        view.setLayoutParams(lp);
//        view.setMlistener(new DragView.createFilelistener() {
//            @Override
//            public void createFile() {
//                layout.removeView(group);
//                mLoadPhotoToViewPresenter.ShowNewDialog();
//            }
//
//            @Override
//            public void betrue_createFile(int flag) {
//                if (flag == 1) {
//                    if (mJianliImageviewFlag == mJianliImagviewFlickerFalse) {
//                        mJianliImageviewFlag = mJianliImageviewFlickerTrue;
//                        setFlickerAnimation(mjianliImageview, 1, 0.5f);
//                        setScaleAnimation(mjianliImageview, 1.1f);
//                    }
//                    jiantou.clearAnimation();
//                    mJiantouFlag = mJiantouFlickerFalse;
//                } else {
//                    if (mJiantouFlag == mJiantouFlickerFalse) {
//                        mJiantouFlag = mJiantouFlickerTrue;
//                        setFlickerAnimation(jiantou, 1, 0);
//                    }
//                    mJianliImageviewFlag = mJianliImagviewFlickerFalse;
//                    mjianliImageview.clearAnimation();
//                    setScaleAnimation(mjianliImageview, 1f);
//                }
//            }
//
//            @Override
//            public void remove_view() {
//                mjianliImageview.setImageResource(R.drawable.jianli);
//                jiantou.clearAnimation();
//                jiantou.setVisibility(View.GONE);
//                relativeLayout.setBackgroundResource(0);
//                mListview.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return false;
//                    }
//                });
//                show_move_detail.setVisibility(View.VISIBLE);
//                layout.removeView(group);
//            }
//
//            @Override
//            public void move_exist_file() {
//                layout.removeView(group);
//                mLoadPhotoToViewPresenter.ShowMovePhotoToExistFileDialog();
//            }
//
//            @Override
//            public void change_imageview(int flag) {
//                if (flag == changeTobackground) {
//                    top_area.setBackgroundColor(Color.parseColor("#C1F3B4"));
//                    view.clearAnimation();
//                    setScaleAnimation(view, 0.6f);
////                    ScaleAnimationHelper.ScaleInAnimation(view,1.0f,0.6f);
//                } else if (flag == changeToNormal) {
//                    top_area.setBackgroundColor(Color.parseColor("#E8E8E8"));
//                    view.clearAnimation();
//                    setScaleAnimation(view, 1.0f);
//                    //一闪一闪的动画取消
//                }
//            }
//        });
//        layout.addView(group, lp1);
//        group.addView(view, lp);
//        mListview.setClickable(false);
//        mListview.setFocusable(false);
//        final MotionEvent toucheevent;
//        measureView(view);
//        DragViewPos[0] = lp.leftMargin;
//        DragViewPos[1] = lp.leftMargin + view.getMeasuredWidth();
//        DragViewPos[2] = lp.topMargin;
//        DragViewPos[3] = lp.topMargin + view.getMeasuredHeight();
//        Log.i("DragViewPos", DragViewPos[0] + " " + DragViewPos[1] + " " + DragViewPos[2] + " " + DragViewPos[3] + " ");
//        mListview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("DragViewPos", (int) event.getRawX() + " " + (int) event.getRawY());
//                view.onTouchEvent(event);
//                return true;
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit:
                NeedMoveFile.removeall();
                listviewadapter.notifyDataSetChanged();
                show_move_detail.setVisibility(View.GONE);
                break;

            case R.id.delete:
                mLoadPhotoToViewPresenter.ShowDeleteDialog();
                break;
        }

    }
}
