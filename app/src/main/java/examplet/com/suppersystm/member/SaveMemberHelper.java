package examplet.com.suppersystm.member;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2019/5/5.
 */

public class SaveMemberHelper extends SQLiteOpenHelper {
    private final static String CREATE_MEMBER="create table member1 (_id integer primary key autoincrement,"
            +"code text,"
            +"name text,"
            +"idCard text,"
            +"phone text,"
            +"integral text,"
            +"birthday text,"
            +"intime text)";
    private Context mContext;
    public SaveMemberHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        this.db=sqLiteDatabase;   //拿到这个对象，然后就可以进行增删查改等操作
//        db.execSQL(CREATE_TBL);  //创建表
        db.execSQL(CREATE_MEMBER);
        System.out.println("创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists member1");
        onCreate(db);
    }
}
