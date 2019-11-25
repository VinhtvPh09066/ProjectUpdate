package com.example.project1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.project1.model.Word1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseTranslate extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    private static String DB_PATH = "";
    private static String DB_NAME = "translate_data.db";// Database name
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    // do đường dẫn ở phiên bản API > 17 thay đổi nên chúng ta cần kiểm tra nhé
    public DataBaseTranslate(Context context) {
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

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }




    public List<Word1> searchVietNhat(String text) {
        List<Word1> words = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String SQL = "SELECT origin,a.mean as 'mean word',a.spell as 'spell word',c.content as 'content example',c.mean as 'mean example',c.spell as 'spell examples',e.name as 'type' from words a" +
                " INNER JOIN word_examples b ON a.id=b.word_id " +
                " INNER JOIN examples c ON b.example_id=c.id" +
                " INNER JOIN means d ON a.id=d.word_id" +
                " INNER JOIN types e ON d.type_id=e.id" +
                " WHERE a.mean LIKE '" + text + "%'  " +
                "  GROUP BY a.id";
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    Word1 word = new Word1();

                    word.origin_word = cursor.getString(cursor.getColumnIndex("origin"));
                    word.mean_word = cursor.getString(cursor.getColumnIndex("mean word"));
                    word.spell_word = cursor.getString(cursor.getColumnIndex("spell word"));
                    word.spell_example = cursor.getString(cursor.getColumnIndex("spell examples"));
                    word.content_example = cursor.getString(cursor.getColumnIndex("content example"));
                    word.mean_example = cursor.getString(cursor.getColumnIndex("mean example"));
                    word.type_word = cursor.getString(cursor.getColumnIndex("type"));
                    words.add(word);
                    cursor.moveToNext();

                }
                cursor.close();
            }
        }

        return words;
    }


    public List<Word1> searchNhatViet(String text) {
        List<Word1> words = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String SQL = "SELECT origin,a.mean as 'mean word',a.spell as 'spell word',c.content as 'content example',c.mean as 'mean example',c.spell as 'spell examples',e.name as 'type' from words a" +
                " INNER JOIN word_examples b ON a.id=b.word_id " +
                " INNER JOIN examples c ON b.example_id=c.id" +
                " INNER JOIN means d ON a.id=d.word_id" +
                " INNER JOIN types e ON d.type_id=e.id" +
                " WHERE a.spell LIKE '" + text + "%'  " +
                "  GROUP BY a.id";
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    Word1 word = new Word1();

                    word.origin_word = cursor.getString(cursor.getColumnIndex("origin"));
                    word.mean_word = cursor.getString(cursor.getColumnIndex("mean word"));
                    word.spell_word = cursor.getString(cursor.getColumnIndex("spell word"));
                    word.spell_example = cursor.getString(cursor.getColumnIndex("spell examples"));
                    word.content_example = cursor.getString(cursor.getColumnIndex("content example"));
                    word.mean_example = cursor.getString(cursor.getColumnIndex("mean example"));
                    word.type_word = cursor.getString(cursor.getColumnIndex("type"));
                    words.add(word);
                    cursor.moveToNext();

                }
                cursor.close();
            }
        }

        return words;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}