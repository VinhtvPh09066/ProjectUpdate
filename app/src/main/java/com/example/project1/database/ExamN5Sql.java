package com.example.project1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.project1.model.Question1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamN5Sql extends SQLiteOpenHelper {
    private static String DB_NAME = "quiz_data.db";
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    private SQLiteDatabase mDataBase;
    private static String DB_PATH = "";
    private final Context mContext;
    //

    private String TABLE_NAME = "quizn5_grammar";
    private String TABLE_NAME1 = "quizn5_kanji";
    private String TABLE_NAME2 = "quizn5_vocabulary";
    public String CAUHOI = "cauhoi";
    public String DAPAN1 = "dapan1";
    public String DAPAN2 = "dapan2";
    public String DAPAN3 = "dapan3";
    public String DAPAN4 = "dapan4";
    public String CAUTRALOI = "ctraloi";
    public String GIAITHICH = "giaithich";


    // do đường dẫn ở phiên bản API > 17 thay đổi nên chúng ta cần kiểm tra nhé
    public ExamN5Sql(Context context) {
        super(context, DB_NAME, null, 1);// 1? Its database Version
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDataBase() {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    public List<Question1> getAllQuizKanji() {
        List<Question1> questionArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                Question1 question = new Question1();

                String questText = c.getString(c.getColumnIndex(CAUHOI));
                question.setQuestion(questText);

                String choice1Text = c.getString(c.getColumnIndex(DAPAN1));
                question.setChoice(0, choice1Text);

                String choice2Text = c.getString(c.getColumnIndex(DAPAN2));
                question.setChoice(1, choice2Text);

                String choice3Text = c.getString(c.getColumnIndex(DAPAN3));
                question.setChoice(2, choice3Text);

                String choice4Text = c.getString(c.getColumnIndex(DAPAN4));
                question.setChoice(3, choice4Text);

                String answerText = c.getString(c.getColumnIndex(CAUTRALOI));
                question.setAnswer(answerText);
                String explainText = c.getString(c.getColumnIndex(GIAITHICH));


                // adding to Questions list
                questionArrayList.add(question);
            } while (c.moveToNext());
            Collections.shuffle(questionArrayList);
        }
        return questionArrayList;
    }

    public List<Question1> getAllQuestionsList() {
        List<Question1> questionArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                Question1 question = new Question1();

                String questText = c.getString(c.getColumnIndex(CAUHOI));
                question.setQuestion(questText);

                String choice1Text = c.getString(c.getColumnIndex(DAPAN1));
                question.setChoice(0, choice1Text);

                String choice2Text = c.getString(c.getColumnIndex(DAPAN2));
                question.setChoice(1, choice2Text);

                String choice3Text = c.getString(c.getColumnIndex(DAPAN3));
                question.setChoice(2, choice3Text);

                String choice4Text = c.getString(c.getColumnIndex(DAPAN4));
                question.setChoice(3, choice4Text);

                String answerText = c.getString(c.getColumnIndex(CAUTRALOI));
                question.setAnswer(answerText);
                String explainText = c.getString(c.getColumnIndex(GIAITHICH));

                // adding to Questions list
                questionArrayList.add(question);
            } while (c.moveToNext());
            Collections.shuffle(questionArrayList);
        }
        return questionArrayList;
    }

    public List<Question1> getAllVocabulary() {
        List<Question1> questionArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME2;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                Question1 question = new Question1();

                String questText = c.getString(c.getColumnIndex(CAUHOI));
                question.setQuestion(questText);

                String choice1Text = c.getString(c.getColumnIndex(DAPAN1));
                question.setChoice(0, choice1Text);

                String choice2Text = c.getString(c.getColumnIndex(DAPAN2));
                question.setChoice(1, choice2Text);

                String choice3Text = c.getString(c.getColumnIndex(DAPAN3));
                question.setChoice(2, choice3Text);

                String choice4Text = c.getString(c.getColumnIndex(DAPAN4));
                question.setChoice(3, choice4Text);

                String answerText = c.getString(c.getColumnIndex(CAUTRALOI));
                question.setAnswer(answerText);
                String explainText = c.getString(c.getColumnIndex(GIAITHICH));

                // adding to Questions list
                questionArrayList.add(question);
            } while (c.moveToNext());
            Collections.shuffle(questionArrayList);
        }
        return questionArrayList;
    }
    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    ///


}
