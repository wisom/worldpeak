package com.worldpeak.chnsmilead.test;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoService {
    private Context context;
    public ContactInfoService(Context context) {
        this.context = context;
    }
    public List<ContactBean> getContactList(){
        List<ContactBean> mContactBeanList=new ArrayList<>();
        ContactBean mContactBean=null;
        ContentResolver mContentResolver=context.getContentResolver();
        Uri uri= Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri=Uri.parse("content://com.android.contacts/data");
        Cursor cursor =mContentResolver.query(uri,null,null,null,null);
        while (cursor.moveToNext()){
          mContactBean=new ContactBean();
            String id=cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            String title=cursor.getString(cursor.getColumnIndexOrThrow("display_name"));//获取联系人姓名
            String firstHeadLetter=cursor.getString(cursor.getColumnIndexOrThrow("phonebook_label"));//这个字段保存了每个联系人首字的拼音的首字母
            mContactBean.setTitle(title);
            mContactBean.setFirstHeadLetter(firstHeadLetter);
            Cursor dataCursor=mContentResolver.query(dataUri,null,"raw_contact_id= ?",new String[]{id},null);
            while(dataCursor.moveToNext()){
                String type=dataCursor.getString(dataCursor.getColumnIndexOrThrow("mimetype"));
                if (type.equals("vnd.android.cursor.item/phone_v2")){//如果得到的mimeType类型为手机号码类型才去接收
                    String phoneNum=dataCursor.getString(dataCursor.getColumnIndexOrThrow("data1"));//获取手机号码
                    mContactBean.setPhoneNum(phoneNum);
                }
            }
            dataCursor.close();
            if (mContactBean.getTitle()!=null&&mContactBean.getPhoneNum()!=null){
                mContactBeanList.add(mContactBean);
            }
        }
        cursor.close();
        return mContactBeanList;
    }
}