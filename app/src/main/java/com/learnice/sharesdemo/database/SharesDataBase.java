package com.learnice.sharesdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public class SharesDataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME="tb_shares";
    public static final String ID="id";
    public static final String SHARES_TYPE="sharesType";
    public static final String SHARES_NAME="sharesName";
    public static final String SQL="create table "+TABLE_NAME+"("+ID+" integer primary key autoincrement,"
            +SHARES_TYPE+" text not null,"
            +SHARES_NAME+" text not null)";

    public SharesDataBase(Context context) {
        super(context, "shares.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
