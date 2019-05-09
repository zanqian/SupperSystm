package examplet.com.suppersystm.goods;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2019/4/18.
 */

public class RecordsGoodsHelper extends SQLiteOpenHelper {
    private String CREATE_RECORDSGOODS="create table recordsGoods(_id integer primary key autoincrement,"
            + " name text,"
            + "code text,"
            + "stock integer,"
            + "bid double,"
            + "price double ,"
            + "supplierId integer,"
            + "shelfId integer)";
    public RecordsGoodsHelper(Context context){
        super(context,"RecordGoods.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECORDSGOODS);
        System.out.println("创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists recordsGoods");
        onCreate(sqLiteDatabase);
    }
}
