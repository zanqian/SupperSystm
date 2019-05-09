package examplet.com.suppersystm.member;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import examplet.com.suppersystm.staff.RecordsUserHelper;

/**
 * Created by pc on 2019/5/5.
 */

public class RecordsMemberHelper extends SQLiteOpenHelper {
    private final static String CREATE_RECORDSMEMBER="create table recordsMember1 (_id integer primary key autoincrement,"
            + "records text)";
    public RecordsMemberHelper(Context context){
        super(context,"RecordsMember1.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECORDSMEMBER);
        System.out.println("创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists recordsMember1");
        onCreate(sqLiteDatabase);
    }
}
