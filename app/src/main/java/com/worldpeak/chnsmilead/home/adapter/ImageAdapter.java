package com.worldpeak.chnsmilead.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.worldpeak.chnsmilead.MyApp;
import com.worldpeak.chnsmilead.R;
import com.worldpeak.chnsmilead.base.activity.WebDetailActivity;
import com.worldpeak.chnsmilead.home.model.BannerData;
import com.worldpeak.chnsmilead.util.DensityUtils;
import com.worldpeak.chnsmilead.view.GlideRoundTransform;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public class ImageAdapter extends BannerAdapter<BannerData, ImageAdapter.BannerViewHolder> {

    private Context mContext;
    private int mRadius = -1;
    private boolean mCircle = false;

    public ImageAdapter(Context context, List<BannerData> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        mContext = context;
    }

    public ImageAdapter(Context context, List<BannerData> mDatas, int radiusDp) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        mContext = context;
        mRadius = radiusDp;
    }

    public ImageAdapter(Context context, List<BannerData> mDatas, boolean isCircle) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        mContext = context;
        mCircle = isCircle;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerData banner, int position, int size) {
        if (mCircle) {
            RequestOptions options = new RequestOptions(); //圆角
            options.bitmapTransform(new RoundedCorners(DensityUtils.dip2px(mContext, mRadius)));
        } else if (mRadius != -1) {
            RequestOptions options = new RequestOptions();
            options.centerCrop()
                    .placeholder(R.drawable.shape_bg_white_radius_8dp) //预加载
                    .error(R.drawable.shape_bg_white_radius_8dp)      //失败
                    .priority(Priority.IMMEDIATE) //优先级
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//设置缓存策略
                    .transform(new GlideRoundTransform(mRadius, 0));//设置四角圆角
            Glide.with(mContext).load(banner.getPicUrl()).apply(options).into(holder.imageView);
        } else {
            Glide.with(mContext)
                    .load(banner.getPicUrl())
                    .placeholder(R.drawable.shape_bg_white_radius_8dp)
                    .error(R.drawable.shape_bg_white_radius_8dp)
                    .into(holder.imageView);
        }
        if (!banner.getJumpUrl().isEmpty()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebDetailActivity.startActivity(holder.itemView.getContext(), banner.getTitle(), banner.getJumpUrl());
                }
            });
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
