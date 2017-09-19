package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 01/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class DatabaseHelper extends SQLiteOpenHelper {

    String querryString;
    List<String> temp;
    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "Survey";

    //public static final String TABLE_NAME = "questions";
    public static final String QUE_TABLE = "questions";
    public static final String SUBQUE_TABLE = "subque_table";
    public static final String GENERALINFO_TABLE = "generalinfo_table";
    public static final String GENERALINFO_ANSWERS = "generalinfo_answers";
    public static final String SECTION_ANSWERS = "section_answers";
    public static final String COLUMN_ID = "_id";
    public static final String ID = "id";
    public static final String QUE = "que";
    public static final String USERID = "user_id";
    public static final String RANDOMID = "random_id";
    public static final String SUB_QUE = "sub_que";

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    public Cursor getQue2(String tbl,String order_col,String survey_id,String asseso_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //String sq= "SELECT EMP_ID, NAME, DEPT FROM COMPANY LEFT OUTER JOIN DEPARTMENT ON COMPANY.ID = DEPARTMENT.EMP_ID";


       /* Cursor cursor = db.rawQuery("select "+ USERID + " from "+ tbl + " where  "+ USERID + " = '"
                + user_id + "'", null);*/

        String sqt= "SELECT * FROM " + tbl + " where survey_id = '" + survey_id + "' AND assessment_id = '" + asseso_id + "';";
        //String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sqt, null);
        return c;
    }

    public Cursor getAssessments(String tbl,String order_col,String survey_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //String sq= "SELECT EMP_ID, NAME, DEPT FROM COMPANY LEFT OUTER JOIN DEPARTMENT ON COMPANY.ID = DEPARTMENT.EMP_ID";


       /* Cursor cursor = db.rawQuery("select "+ USERID + " from "+ tbl + " where  "+ USERID + " = '"
                + user_id + "'", null);*/

        String sqt= "SELECT * FROM " + tbl + " where survey_id = '"
                + survey_id + "';";
        //String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sqt, null);
        return c;
    }


    public Cursor getQue3(String tbl,String order_col,String parentque_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //String sq= "SELECT EMP_ID, NAME, DEPT FROM COMPANY LEFT OUTER JOIN DEPARTMENT ON COMPANY.ID = DEPARTMENT.EMP_ID";


       /* Cursor cursor = db.rawQuery("select "+ USERID + " from "+ tbl + " where  "+ USERID + " = '"
                + user_id + "'", null);*/

        String sqt= "SELECT * FROM " + tbl + " where parent_question_id = '"
                + parentque_id + "';";
        //String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sqt, null);
        return c;
    }

    public Cursor getQue(String tbl,String order_col) {
        SQLiteDatabase db = this.getReadableDatabase();

        //String sq= "SELECT EMP_ID, NAME, DEPT FROM COMPANY LEFT OUTER JOIN DEPARTMENT ON COMPANY.ID = DEPARTMENT.EMP_ID";

        String sqt= "SELECT * FROM questions LEFT OUTER JOIN subque_table ON questions.que_no = subque_table.parent_question" + " ORDER BY " + order_col + " ASC;";


        //String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sqt, null);
        return c;
    }
    public Cursor getAnyQue(String tbl,String order_col) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getGenQueAns(String tbl) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + tbl + ";";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getGenQue(String tbl,String order_col) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void createGenInfoTable(String tbl, String[] que_no, String[] ans){

        SQLiteDatabase db;
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Cursor tblExists = checkTableExists(tbl);
        String que;
        temp = new ArrayList<>();

        if (tblExists.getCount() > 0) {

            Log.d("TABLE_EXISTS", String.valueOf("YES"));
            //check whether que colomns exists else alter with the table and add the columns
            //if columns exists,update the info

          /* String DATABASE_ALTER_TEAM_TO_V2 = "ALTER TABLE "
                    + tbl + " ADD COLUMN " + COLUMN_COACH + " TEXT;";
*/

            for (String aQue_no : que_no) {
                //check whether que is part of the columns

                boolean bla = existsColumnInTable(db,tbl,aQue_no);
                if(!bla){

                    String alterDb = "ALTER TABLE "
                            + tbl + " ADD COLUMN " + aQue_no + " VARCHAR;";

                    db.execSQL(alterDb);
                }
            }
            //select an id, if it doesnt exist insert new row else update the existing row

            String user_id = ans[0];

            Log.d("RANDOM_ID", user_id);

            Cursor rowExists = checkGenInfoID(user_id,tbl);

            if (rowExists.getCount() > 0) {

                for (int i = 0; i < que_no.length; i++) {


                    if (Objects.equals(ans[i], "")){

                        ans[i]="none";
                    }

                    String outp= que_no[i]+", "+ans[i];

                    //Log.d("ROW, VALUE", String.valueOf(outp));
                    cv.put(que_no[i], ans[i]);

                }

                db.update(tbl,cv,"user_id ="+user_id, null);
                db.close();

            }
            else {

                for (int i = 0; i < que_no.length; i++) {


                    if (Objects.equals(ans[i], "")){

                        ans[i]="none";
                    }

                    String outp= que_no[i]+", "+ans[i];

                    Log.d("ROW, VALUE", String.valueOf(outp));
                    cv.put(que_no[i], ans[i]);

                }
                db.insert(tbl, null, cv);
                db.close();

            }

        }

        else{
            for (String aQue_no : que_no) {


                que = aQue_no;
                que += " VARCHAR";

                temp.add(que);
            }

            String listComma = android.text.TextUtils.join(",", temp);


            querryString = "CREATE TABLE IF NOT EXISTS " + tbl + "(" + COLUMN_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + listComma + ");";

            db.execSQL(querryString);


            for (int i = 0; i < que_no.length; i++) {


                if (Objects.equals(ans[i], "")){

                    ans[i]="none";
                }

                String outp= que_no[i]+", "+ans[i];

                Log.d("ROW, VALUE", String.valueOf(outp));
                cv.put(que_no[i], ans[i]);

            }
            db.insert(tbl, null, cv);
            db.close();



        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Cursor createUsersTable(String tbl, String[] que_no, String[] ans){
        SQLiteDatabase db;
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Cursor tblExists = checkTableExists(tbl);
        String que;
        temp = new ArrayList<>();

        if (tblExists.getCount() > 0) {

            Log.d("TABLE_EXISTS", String.valueOf("YES"));
            //check whether que colomns exists else alter with the table and add the columns
            //if columns exists,update the info

          /* String DATABASE_ALTER_TEAM_TO_V2 = "ALTER TABLE "
                    + tbl + " ADD COLUMN " + COLUMN_COACH + " TEXT;";
*/

            for (String aQue_no : que_no) {
                //check whether que is part of the columns

                boolean bla = existsColumnInTable(db,tbl,aQue_no);
                if(!bla){

                    String alterDb = "ALTER TABLE "
                            + tbl + " ADD COLUMN " + aQue_no + " VARCHAR;";

                    db.execSQL(alterDb);
                }
            }
            //select an id, if it doesnt exist insert new row else update the existing row

            String user_id = ans[0];

            Log.d("USER_ID", user_id);

            Cursor rowExists = checkUserID(user_id,tbl);

            if (rowExists.getCount() > 0) {

                for (int i = 0; i < que_no.length; i++) {


                    if (Objects.equals(ans[i], "")){

                        ans[i]="none";
                    }

                    String outp= que_no[i]+", "+ans[i];

                    //Log.d("ROW, VALUE", String.valueOf(outp));
                    cv.put(que_no[i], ans[i]);

                }

                db.update(tbl,cv,"user_id ="+user_id, null);
                db.close();

            }
            else {

                for (int i = 0; i < que_no.length; i++) {


                    if (Objects.equals(ans[i], "")){

                        ans[i]="none";
                    }

                    String outp= que_no[i]+", "+ans[i];

                    Log.d("ROW, VALUE", String.valueOf(outp));
                    cv.put(que_no[i], ans[i]);

                }
                db.insert(tbl, null, cv);
                db.close();

            }


        }
        else{
            for (String aQue_no : que_no) {


                que = aQue_no;
                que += " VARCHAR";

                temp.add(que);
            }

            String listComma = android.text.TextUtils.join(",", temp);


            querryString = "CREATE TABLE IF NOT EXISTS " + tbl + "(" + COLUMN_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + listComma + ");";

            db.execSQL(querryString);


            for (int i = 0; i < que_no.length; i++) {


                if (Objects.equals(ans[i], "")){

                    ans[i]="none";
                }

                String outp= que_no[i]+", "+ans[i];

                Log.d("ROW, VALUE", String.valueOf(outp));
                cv.put(que_no[i], ans[i]);

            }
            db.insert(tbl, null, cv);
            db.close();



        }

        db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + tbl +";";
        Cursor c = db.rawQuery(sql, null);
        return c;

    }

    private boolean existsColumnInTable(SQLiteDatabase inDatabase, String inTable, String columnToCheck) {
        Cursor mCursor = null;
        try {
            // Query 1 row
            mCursor = inDatabase.rawQuery("SELECT * FROM " + inTable + " LIMIT 0", null);

            // getColumnIndex() gives us the index (0 to ...) of the column - otherwise we get a -1
            if (mCursor.getColumnIndex(columnToCheck) != -1)
                return true;
            else
                return false;

        } catch (Exception Exp) {
            // Something went wrong. Missing the database? The table?
            Log.d("error occurred: ", Exp.getMessage());
            return false;
        } finally {
            if (mCursor != null) mCursor.close();
        }
    }

    public Cursor getColumns(String tbl,String user_id){
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor c = db.rawQuery("select * from "+ tbl + " where  "+ USERID + " = '"
                + user_id + "'", null);
        return c;

    }

    public void CreateTableRow(String tbl_name, List<String> columns, List<String> answers) {

        String que;
        temp = new ArrayList<>();

        SQLiteDatabase db;
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Cursor tblExists = checkTableExists(tbl_name);

        if (tblExists.getCount() > 0) {


         /*   //query the ids of the tables, compare if the id with one supplied
            String q_id = answers.get(0);

            String col_ID = columns.get(0);

            Cursor c = db.rawQuery("select " + col_ID + " from " + tbl_name + " where " + col_ID + " = '"
                    + q_id + "'", null);*/

            String q_id = answers.get(1);

            String col_ID = columns.get(1);

            Cursor c = db.rawQuery("select " + col_ID + " from " + tbl_name + " where " + col_ID + " = '"
                    + q_id + "'", null);

            for (int i = 0; i < answers.size(); i++) {

                String outp= columns.get(i)+", "+answers.get(i);

                Log.d("ROW, VALUE", String.valueOf(outp));

                cv.put(columns.get(i), answers.get(i));

            }
            db.insert(tbl_name, null, cv);

            db.close();


           /* if (c.getCount() == 0) {
                for (int i = 0; i < answers.size(); i++) {

                    String outp= columns.get(i)+", "+answers.get(i);

                    Log.d("ROW, VALUE", String.valueOf(outp));

                    cv.put(columns.get(i), answers.get(i));

                }
                db.insert(tbl_name, null, cv);

                db.close();



            }*/
        }
        else {

            Log.d("TABLE_EXISTS", String.valueOf("NO"));

            for (int i = 0; i < columns.size(); i++) {

                que = columns.get(i);
                que += " VARCHAR";

                temp.add(que);
            }
            String listComma = android.text.TextUtils.join(",", temp);


            querryString = "CREATE TABLE IF NOT EXISTS " + tbl_name + "(" + COLUMN_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + listComma + ");";

            db.execSQL(querryString);


            for (int i = 0; i < columns.size(); i++) {

                String outp= columns.get(i)+", "+answers.get(i);

                Log.d("ROW, VALUE", String.valueOf(outp));

                cv.put(columns.get(i), answers.get(i));

            }
            db.insert(tbl_name, null, cv);
            db.close();

        }
    }





    private Cursor checkGenInfoID(String user_id,String tbl) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("select "+ USERID + " from "+ tbl + " where  "+ USERID + " = '"
                + user_id + "'", null);

        return cursor;

    }
    private Cursor checkUserID(String user_id,String tbl) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("select "+ USERID + " from "+ tbl + " where  "+ USERID + " = '"
                + user_id + "'", null);

        return cursor;

    }

    private Cursor checkTableExists(String table_name) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + table_name + "'", null);

        return cursor;

    }

}