package examplet.com.suppersystm.staff;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2019/4/29.
 */

public class RecordsUserHelper extends SQLiteOpenHelper{
    private final static String CREATE_RECORDSUSER="create table recordsUser1 (_id integer primary key autoincrement,"
            +"code text,"
            +"name text,"
            +"idCard text,"
            +"phone text,"
            +"place text,"
            +"natives text,"
            +"edu text,"
            + "work text,"
            + "birthday text,"
            + "intime text,"
            + "type text)";
    public RecordsUserHelper(Context context){
        super(context,"RecordsUser1.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECORDSUSER);
        System.out.println("创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists recordsUser1");
        onCreate(sqLiteDatabase);
    }
}
