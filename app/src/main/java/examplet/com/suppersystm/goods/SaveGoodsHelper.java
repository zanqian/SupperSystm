package examplet.com.suppersystm.goods;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2019/4/18.
 */

public class SaveGoodsHelper extends SQLiteOpenHelper {
    private String CREATE_GOODS_DE="create table goods(_id integer primary key autoincrement,"
            + " name text,"
            + "code text,"
            + "stock integer,"
            + "bid double,"
            + "price double ,"
            + "supplierId integer,"
            + "shelfId integer)";
    private Context mContexxt;
    public SaveGoodsHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContexxt=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GOODS_DE);
        System.out.println("创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists goods");
        onCreate(sqLiteDatabase);
    }
}
