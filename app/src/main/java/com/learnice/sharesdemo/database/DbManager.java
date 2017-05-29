package com.learnice.sharesdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public class DbManager {
    SharesDataBase sharesDataBase;
    SQLiteDatabase database;
    public DbManager(Context context) {
        sharesDataBase=new SharesDataBase(context);
        database=sharesDataBase.getWritableDatabase();
    }

    /**
     * 添加股票数据 股票类型+股票代号
     * @param values
     * @return
     */
    public long add(ContentValues values){
       return database.insert(SharesDataBase.TABLE_NAME,null,values);
    }

    /**
     * 查找收藏的股票 返回游标
     * @return
     */
    public Cursor select(){
        return  database.query(SharesDataBase.TABLE_NAME,null,null,null,null,null,null);
    }

    /**
     * 查询此股票是否已存在
     * @param selection
     * @param selectionArgs
     * @return
     */
    public Cursor selectOne(String selection,String[] selectionArgs){
        return database.query(SharesDataBase.TABLE_NAME,null,selection,selectionArgs,null,null,null);
    }

    /**
     * 删除数据，需要股票的类型+代号
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public int  delete(String whereClause, String[] whereArgs){
        return database.delete(SharesDataBase.TABLE_NAME,whereClause,whereArgs);
    }

    /**
     * 删除全部用户收藏的股票数据
     * @return
     */
    public int clearData(){
        return database.delete(SharesDataBase.TABLE_NAME,null,null);
    }
}
