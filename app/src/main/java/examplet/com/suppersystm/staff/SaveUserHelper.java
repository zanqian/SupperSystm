package examplet.com.suppersystm.staff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2019/4/15.
 */

public class SaveUserHelper extends SQLiteOpenHelper {
    private final static String CREATE_TBL="create table user1 (_id integer primary key autoincrement,"
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


    private  Context mContexxt;
  /*SQLiteOpenHelper子类必须有一个构造方法
   * context：上下文
   * name：数据库的名字
   * factory：设计模式
   * version：数据库的版本*/
  public SaveUserHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
      super(context, name, factory, version);
      mContexxt=context;
  }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        this.db=sqLiteDatabase;   //拿到这个对象，然后就可以进行增删查改等操作
//        db.execSQL(CREATE_TBL);  //创建表
        db.execSQL(CREATE_TBL);
        System.out.println("创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user1");
        onCreate(db);
    }

//    public void insert(ContentValues values, String dbname){
//        //获取到SQLiteDatabase对象
//        SQLiteDatabase db = getWritableDatabase();
//        //插入数据到数据库：insert方法
//        db.insert(dbname,null,values);
//        db.close();
//    }
//    //查询数据
//    public Cursor query(String dbname){
//        //获取到SQLiteDatabase对象
//        SQLiteDatabase db=getReadableDatabase();
//        //获取Cursor
//        Cursor cursor=db.query(dbname,null,null,null,null,null,null);
//        return cursor;
//    }
//
//    //删除数据
//    public void delete(int id,String dbname){
//        //获取到SQLiteDatabase对象
//        SQLiteDatabase db=getReadableDatabase();
//        db.delete(dbname,"id=?",new String[]{String.valueOf(id)});
//    }
//
//    //修改数据
//    public void update(ContentValues values, String whereClause, String[]whereArgs, String dbname){
//        //获取到SQLiteDatabase对象
//        SQLiteDatabase db=getReadableDatabase();
//        db.update(dbname,values,whereClause,whereArgs);
//    }
//
//    public void update_uid(ContentValues values, String[]whereArgs, String dbname){
//        //获取到SQLiteDatabase对象
//        SQLiteDatabase db=getReadableDatabase();
//        db.update(dbname,values,"id=?",whereArgs);
//    }
//    public void close(){
//        if (db!=null)db.close();
//    }
}


