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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.larson.album.R;
import com.larson.album.data.NeedMoveFile;
import com.larson.album.util.FileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadPhotoToViewActivity_Gridview_Adapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> listfilepath = new ArrayList<String>();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private int mListViewPosition;


    public LoadPhotoToViewActivity_Gridview_Adapter(Context context, ArrayList<String> list, int position) {
        this.mListViewPosition=position;
        this.context = (Activity) context;
        this.listfilepath = list;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .cacheInMemory(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showStubImage(R.drawable.yujiazai)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageForEmptyUri(R.drawable.yujiazai)
                .showImageOnFail(R.drawable.yujiazai).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    @Override
    public int getCount() {

        return listfilepath.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listfilepath.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Viewholder viewholder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item,null);
            convertView = view;
            viewholder = new Viewholder();
            viewholder.imageview = (MyImageview) view.findViewById(R.id.imageview);
            viewholder.imageviewtip = (ImageView) view.findViewById(R.id.ImageView_tip);
            viewholder.chakanphoto = (ImageView) view.findViewById(R.id.chakan_photo);
            viewholder.video_mark = (ImageView) view.findViewById(R.id.video_mark);
            convertView.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (Viewholder) view.getTag();
        }

        final File file = new File(listfilepath.get(position));
        Uri uri = Uri.fromFile(file);
        ImageLoader.getInstance().displayImage(uri+"", viewholder.imageview,options);

//        imageLoader.displayImage("file:///" + listfilepath.get(position), viewholder.imageview,
//                options);
        if (NeedMoveFile.isinNeedmovefile(listfilepath.get(position))) {
            viewholder.imageviewtip.setVisibility(View.VISIBLE);
        } else {
            viewholder.imageviewtip.setVisibility(View.GONE);
        }

        if(FileUtils.isVideoFile(listfilepath.get(position))){
            viewholder.video_mark.setVisibility(View.VISIBLE);
            viewholder.chakanphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent it = new Intent(Intent.ACTION_VIEW);
                    it.setDataAndType(Uri.fromFile(file), "video/*");
                    context.startActivity(it);
                }
            });
        }else {
            viewholder.video_mark.setVisibility(View.GONE);
            viewholder.chakanphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.setDataAndType(Uri.fromFile(file), "image/*");
                    context.startActivity(it);
                }
            });
        }

		viewholder.imageviewtip.setId(position);
        return view;
    }


    public List<String> getListfilename() {
        return listfilepath;
    }

    class Viewholder {
        MyImageview imageview;
        ImageView imageviewtip;
        ImageView chakanphoto;
        ImageView video_mark;
    }
}
