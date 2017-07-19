package com.learnice.sharesdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.learnice.sharesdemo.bean.Shares;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public class DbServices {
    DbManager dbManager;

    public DbServices(Context context) {
        dbManager = new DbManager(context);
    }

    public long add(Shares shares) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SharesDataBase.SHARES_TYPE, shares.getSharesType());
        contentValues.put(SharesDataBase.SHARES_NAME, shares.getSharesName());
        long num = dbManager.add(contentValues);
        return num;
    }

    /**
     * 查询所有数据
     * @return
     */
    public Cursor select() {
        Cursor cursor = dbManager.select();
        return cursor;
    }

    public Cursor selectOne(Shares shares) {
        String selection=SharesDataBase.SHARES_TYPE+"=? and "+SharesDataBase.SHARES_NAME+"=?";
        String[] selectionArgs=new String[]{shares.getSharesType(),shares.getSharesName()};
        Cursor cursor = dbManager.selectOne(selection,selectionArgs);
        return cursor;
    }

    public int delete(Shares shares) {
        String whereClause = SharesDataBase.SHARES_TYPE + "=? and " + SharesDataBase.SHARES_NAME + "=?";
        String[] whereArgs = new String[]{shares.getSharesType(), shares.getSharesName()};
        int num = dbManager.delete(whereClause, whereArgs);
        return num;
    }
    public int clearData(){
        int num = dbManager.clearData();
        return num;
    }

}
