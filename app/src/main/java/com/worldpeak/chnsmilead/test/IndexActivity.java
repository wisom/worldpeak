package com.worldpeak.chnsmilead.test;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.worldpeak.chnsmilead.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class IndexActivity extends AppCompatActivity implements TestContactListAdapter.OnGetAlphaIndexerAndSectionsListener {
    private List<ContactBean> mContactBeanList;//所有联系人集合
    private ListView mContactListView;//联系人ListView
    private MikyouLetterListView mLetterListView;//字母表
    private TextView overLayout;//弹出对话框
    private OverlayThread overlayThread;
    private Map<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private Handler handler;
    private TestContactListAdapter adapter;
    private List<String> sections;// 存放存在的汉语拼音首字母

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initData();
        initView();
        initOverlay();
    }

    private void initView() {
        registerAllViewIds();
        registerAllViewAdapters();
        registerAllViewEvents();
    }

    private void registerAllViewIds() {
        mContactListView = (ListView) findViewById(R.id.id_listview);
        mLetterListView = (MikyouLetterListView) findViewById(R.id.id_letterview);
    }

    private void registerAllViewAdapters() {
        adapter = new TestContactListAdapter(this, mContactBeanList, R.layout.layout_index_item);
        adapter.setOnGetAlphaIndeserAndSectionListener(this);
        mContactListView.setAdapter(adapter);
    }

    private void registerAllViewEvents() {
        mLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
    }

    private void initData() {
        //alphaIndexer=new HashMap<>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        ContactInfoService mContactInfoService = new ContactInfoService(this);
        mContactBeanList = mContactInfoService.getContactList();//返回手机联系人对象集合
        //按拼音首字母表排序
        Collections.sort(mContactBeanList, comparator);
    }

    @Override
    public void getAlphaIndexerAndSectionsListner(Map<String, Integer> alphaIndexer, List<String> sections) {
        this.alphaIndexer = alphaIndexer;
        this.sections = sections;
        Log.d("list", alphaIndexer.toString() + "\n" + sections.toString());
    }

    /**
     * @Mikyou 字母列表点击滑动监听器事件
     */
    private class LetterListViewListener implements
            MikyouLetterListView.OnTouchingLetterChangedListener {
        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {//判断当前选中的字母是否存在集合中
                int position = alphaIndexer.get(s);//如果存在集合中则取出集合中该字母对应所在的位置,再利用对应的setSelection，就可以实现点击选中相应字母，然后联系人就会定位到相应的位置
                mContactListView.setSelection(position);
                overLayout.setText(s);
                overLayout.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1500);
            }
        }
    }

    /**
     * @mikyou 初始化汉语拼音首字母弹出提示框
     */
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overLayout = (TextView) inflater.inflate(R.layout.overlay, null);
        overLayout.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager =
                (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overLayout, lp);
    }

    /**
     * @Mikyou 首字母按a-z排序
     */
    Comparator<ContactBean> comparator = new Comparator<ContactBean>() {
        @Override
        public int compare(ContactBean t1, ContactBean t2) {
            String a = t1.getFirstHeadLetter();
            String b = t2.getFirstHeadLetter();
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    /**
     * @Mikyou 设置overlay不可见
     */
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overLayout.setVisibility(View.GONE);
        }
    }
}