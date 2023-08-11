package com.worldpeak.chnsmilead.test;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.classic.adapter.BaseAdapterHelper;
import com.worldpeak.chnsmilead.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestContactListAdapter extends CommonAdapter<ContactBean> {
    private final int VIEW_TYPE = 3;
    private Map<String, Integer> alphaIndexer;
    private List<String> sections;
    private List<ContactBean> listBeans;
    private OnGetAlphaIndexerAndSectionsListener listener;
    private boolean flag;//标志用于只执行一次代码

    public TestContactListAdapter(Context context, List<ContactBean> listBeans, int layoutId) {
        super(context, listBeans, layoutId);
        this.listBeans = listBeans;
        alphaIndexer = new HashMap<>();
        sections = new ArrayList<>();
        for (int i = 0; i < listBeans.size(); i++) {
            //当前汉语拼音的首字母
            String currentAlpha = listBeans.get(i).getFirstHeadLetter();
            //上一个拼音的首字母，如果不存在则为""
            String previewAlpha = (i - 1) >= 0 ? listBeans.get(i - 1).getFirstHeadLetter() : "";
            if (!previewAlpha.equals(currentAlpha)) {
                String firstAlpha = listBeans.get(i).getFirstHeadLetter();
                alphaIndexer.put(firstAlpha, i);
                sections.add(firstAlpha);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (position == 0) {
            type = 2;
        } else if (position == 1) {
            type = 1;
        }
        return type;
    }

    @Override
    public int getCount() {
        //注意：为什么没有直接把回调方法的调用写在构造器中呢？因为构造器只会调用一次，当第一次调用listener的时候是为空的
        //而要初始化listener对象,则需要先去创建对象再去通过对象调用set方法来初始化这个listener对象，再去new对象的时候又要去用到listener产生了矛盾
        //所以放在getCount中调用,只会调用一次,符合需求
        if (!flag) {
            if (listener != null) {
                listener.getAlphaIndexerAndSectionsListner(alphaIndexer, sections);
            }
            flag = true;
        }
        return listBeans.size();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public void convert(ViewHolder viewHolder, ContactBean contactBean, int postion) {
        int viewType = getItemViewType(postion);
        ImageView iv = viewHolder.getView(R.id.contact_icon_id);
        iv.setImageResource(R.drawable.ic_group);
        viewHolder.setText(R.id.contact_title, contactBean.getTitle()).setText(R.id.contact_phone_num, contactBean.getPhoneNum());
        if (postion >= 1) {
            String currentAlpha = listBeans.get(postion).getFirstHeadLetter();
            String previewAlpha = listBeans.get(postion - 1).getFirstHeadLetter();
            if (!previewAlpha.equals(currentAlpha)) {//不相等表示有新的字母项产生且为该类字母堆中的第一个字母索引项
                viewHolder.getView(R.id.first_alpha).setVisibility(View.VISIBLE);//把新的字母列表项设置VISIBlE
                TextView tv = viewHolder.getView(R.id.first_alpha);
                tv.setText(currentAlpha);
            } else {//表示没有新的字母堆出现,也就说明该item是属于同类字母堆中且不是第一个,那么就需要将这个索引项设置GONE
                viewHolder.getView(R.id.first_alpha).setVisibility(View.GONE);
            }
        }
    }

    public void setOnGetAlphaIndeserAndSectionListener(OnGetAlphaIndexerAndSectionsListener listener) {
        this.listener = listener;
    }

    public interface OnGetAlphaIndexerAndSectionsListener {
        public void getAlphaIndexerAndSectionsListner(Map<String, Integer> alphaIndexer, List<String> sections);
    }
}