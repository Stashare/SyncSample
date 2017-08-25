package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 01/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    String querryString;
    List<String> temp;
    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "Survey";

    //public static final String TABLE_NAME = "questions";
    public static final String QUE_TABLE = "questions";
    public static final String SUBQUE_TABLE = "subque_table";
    public static final String RESPONDENT_INTRO_TABLE = "respondent_intro_table";
    public static final String SECTION_ANSWERS = "section_answers";
    public static final String COLUMN_ID = "_id";
    public static final String ID = "id";
    public static final String QUE = "que";
    public static final String SUB_QUE = "sub_que";
/*


    public static final String COLUMN_QUE = "question";
    public static final String COLUMN_SINGLE_RESPONSE = "single_response";
    public static final String COLUMN_QUE_NO = "que_no";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_SUB_SECTIONS = "sub_section";
    public static final String COLUMN_QUE_TYPE = "que_type";
    public static final String COLUMN_IS_SUBQUE = "is_subque";
    public static final String COLUMN_SELECTIONS = "selections";

    public static final String SUB_QUE_TABLE = "sub_questions";

    public static final String COLUMN_PARENTQUE_NO = "parentque_no";
    public static final String COLUMN_SUB_SELECTIONS = "sub_selections";
    public static final String COLUMN_SUB_SINGLE_RESPONSE = "sub_singleresponse";
    public static final String COLUMN_UNIQUEID = "unique_id";
    public static final String COLUMN_SUBQUE = "sub_question";
    public static final String COLUMN_SUBQUE_TYPE = "subque_type";

    public static final String GEN_RESPODENT_TABLE = "intro_table";
*/

    /*  public static final String GEN_RESPODENT_TABLE = "questions";
      public static final String SECTION1_TABLE = "section1";
      public static final String SECTION2_TABLE = "section2";
      public static final String SECTION3_TABLE = "section3";
  */
    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUE_NO +
                " VARCHAR, " + COLUMN_QUE +
                " VARCHAR, " + COLUMN_SELECTIONS +
                " VARCHAR, " + COLUMN_SINGLE_RESPONSE +
                " VARCHAR, " + COLUMN_SECTION +
                " VARCHAR, " + COLUMN_SUB_SECTIONS +
                " VARCHAR, " + COLUMN_QUE_TYPE +
                " VARCHAR, " + COLUMN_IS_SUBQUE +
                " VARCHAR);";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE " + SUB_QUE_TABLE
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SUBQUE +
                " VARCHAR, " + COLUMN_UNIQUEID +
                " VARCHAR, " + COLUMN_PARENTQUE_NO +
                " VARCHAR, " + COLUMN_SUB_SELECTIONS +
                " VARCHAR, " + COLUMN_SUBQUE_TYPE +
                " VARCHAR, " + COLUMN_SUB_SINGLE_RESPONSE +
                " VARCHAR);";
        db.execSQL(sql2);
*/
       /* String gen_respodent = "CREATE TABLE " + GEN_RESPODENT_TABLE
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(gen_respodent);

        String section1 = "CREATE TABLE " + SECTION1_TABLE
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(section1);

        String section2 = "CREATE TABLE " + SECTION2_TABLE
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(section2);
        String section3 = "CREATE TABLE " + SECTION3_TABLE
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(section3);*/
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*String sql = "DROP TABLE IF EXISTS questions";

        String sql2 = "DROP TABLE IF EXISTS sub_questions";

        db.execSQL(sql);

        db.execSQL(sql2);*/

        onCreate(db);
    }

    /*public boolean addQuestions(String que_no, String single_response, String que, String section, String sub_sections,
                                String type, String is_subquestion, String selections) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_QUE_NO, que_no);
        contentValues.put(COLUMN_QUE, que);
        contentValues.put(COLUMN_SINGLE_RESPONSE, single_response);
        contentValues.put(COLUMN_SECTION, section);
        contentValues.put(COLUMN_SUB_SECTIONS, sub_sections);
        contentValues.put(COLUMN_QUE_TYPE, type);
        contentValues.put(COLUMN_IS_SUBQUE, is_subquestion);
        contentValues.put(COLUMN_SELECTIONS, selections);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;


    }

  *//*  public boolean addSubQuestions(String subque, String uniqueid, String que_no, String type, String selections, String single_type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SUBQUE, subque);
        contentValues.put(COLUMN_UNIQUEID, uniqueid);
        contentValues.put(COLUMN_PARENTQUE_NO, que_no);
        contentValues.put(COLUMN_SUBQUE_TYPE, type);
        contentValues.put(COLUMN_SUB_SELECTIONS, selections);
        contentValues.put(COLUMN_SUB_SINGLE_RESPONSE, single_type);


        db.insert(SUB_QUE_TABLE, null, contentValues);
        db.close();
        return true;
    }
*//*
    *//*
    * this method will give us all the name stored in sqlite
    * *//*
   */
    /*
  * this method will give us all the name stored in sqlite
  * */
    /*public Cursor getSectionQuestions(String section) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME + " where section= '"+ section + "'" + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getSubQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + SUB_QUE_TABLE + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getJoinSubQue(String queNo) {

        SQLiteDatabase db;

        db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT * FROM questions INNER JOIN sub_questions ON questions.que_no = sub_questions.parentque_no WHERE questions.que_no = ?";

        Cursor c = db.rawQuery(MY_QUERY, new String[]{String.valueOf(queNo)});

        return c;
    }
*/

    public Cursor getQue(String tbl,String order_col) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + tbl + " ORDER BY " + order_col + " ASC;";
        Cursor c = db.rawQuery(sql, null);
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


            //query the ids of the tables, compare if the id with one supplied
            String q_id = answers.get(0);

            String col_ID = columns.get(0);

            Cursor c = db.rawQuery("select " + col_ID + " from " + tbl_name + " where " + col_ID + " = '"
                    + q_id + "'", null);

            if (c.getCount() == 0) {
                for (int i = 0; i < answers.size(); i++) {

                    String outp= columns.get(i)+", "+answers.get(i);

                    Log.d("ROW, VALUE", String.valueOf(outp));

                    cv.put(columns.get(i), answers.get(i));

                }
                db.insert(tbl_name, null, cv);

                db.close();



            }
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
                cv.put(answers.get(i), answers.get(i));

            }
            db.insert(tbl_name, null, cv);
            db.close();

        }
    }







    private Cursor checkTableExists(String table_name) {
        SQLiteDatabase db;

        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + table_name + "'", null);

        return cursor;

    }
    /*public boolean addColumnValue(){
        return true;
    }
    public Boolean update(String queNo) {
        return c;
    }*/
}