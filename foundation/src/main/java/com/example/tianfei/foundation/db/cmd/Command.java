
/**
 * 执行命令(有返回值的和无返回值的)
 */

package com.example.tianfei.foundation.db.cmd;

import android.database.sqlite.SQLiteDatabase;

import com.example.tianfei.foundation.db.helper.DatabaseMgr;
import com.example.tianfei.foundation.db.listeners.DataListener;


public abstract class Command<T> {

    public DataListener<T> dataListener;

    public Command() {
    }

    public Command(DataListener<T> listener) {
        dataListener = listener;
    }

    public final T execute() {
        SQLiteDatabase database = DatabaseMgr.getDatabase();
        DatabaseMgr.beginTransaction();
        T result = doInBackground(database);
        DatabaseMgr.setTransactionSuccess();
        DatabaseMgr.endTransaction();
        //如果没有引用就释放db对象
        database.releaseReference();
        return result;
    }

    protected abstract T doInBackground(SQLiteDatabase database);

    /**
     * 无返回值的数据库命令
     *
     * @author mrsimple
     */
    public static abstract class NoReturnCmd extends Command<Void> {
    }


}

