package com.learnice.sharesdemo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.learnice.sharesdemo.Stock.Shares;

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
        return dbManager.add(contentValues);
    }

    public Cursor select() {
        return dbManager.select();
    }

    public Cursor selectOne(Shares shares) {
        String selection=SharesDataBase.SHARES_TYPE+"=? and "+SharesDataBase.SHARES_NAME+"=?";
        String[] selectionArgs=new String[]{shares.getSharesType(),shares.getSharesName()};
        return dbManager.selectOne(selection,selectionArgs);
    }

    public int delete(Shares shares) {
        String whereClause = SharesDataBase.SHARES_TYPE + "=? and " + SharesDataBase.SHARES_NAME + "=?";
        String[] whereArgs = new String[]{shares.getSharesType(), shares.getSharesName()};
        return dbManager.delete(whereClause, whereArgs);
    }
    public int clearData(){
        return dbManager.clearData();
    }
}
