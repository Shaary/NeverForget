package com.shaary.neverforget.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.shaary.neverforget.database.GrudgeDao;
import com.shaary.neverforget.database.MyDatabase;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class GrudgePit {

    private static GrudgePit sGrudgePit;
    private LiveData<List<Grudge>> grudges;
    private Context context;
    private GrudgeDao grudgeDao;

    public static GrudgePit getInstance(Context context) {
        if (sGrudgePit == null) {
            sGrudgePit = new GrudgePit(context);
        }
        return sGrudgePit;
    }

    private GrudgePit(Context context) {
        this.context = context.getApplicationContext();
        //TODO: move to back thread
        MyDatabase database = MyDatabase.getDatabase(context);
        grudgeDao = database.grudgeDao();
        grudges = grudgeDao.getAll();
    }

    public void addGrudge(Grudge grudge) {
        new insertAsyncTask(grudgeDao).execute(grudge);
    }

    public LiveData<List<Grudge>> getGrudgeLiveList() {
        return grudges;
    }

    public Grudge getGrudge(UUID id) {
        return grudgeDao.getGrudgeById(id);
    }

//    public void deleteGrudgeById(UUID id) {
//        database.grudgeDao().deleteGrudgeById(id);
//    }

    public void deleteGrudge(Grudge grudge) {
        new deleteAsyncTask(grudgeDao).execute(grudge);
    }

    public File getPhotoFile(Grudge grudge) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, grudge.getPhotoFilename());
    }

    private static class insertAsyncTask extends AsyncTask<Grudge, Void, Void> {

        private GrudgeDao mAsyncGrudgeDao;

        insertAsyncTask(GrudgeDao dao) {
            mAsyncGrudgeDao = dao;
        }

        @Override
        protected Void doInBackground(final Grudge... params) {
            mAsyncGrudgeDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Grudge, Void, Void> {

        private GrudgeDao mAsyncGrudgeDao;

        deleteAsyncTask(GrudgeDao dao) {
            mAsyncGrudgeDao = dao;
        }

        @Override
        protected Void doInBackground(final Grudge... params) {
            mAsyncGrudgeDao.delete(params[0]);
            return null;
        }
    }

//    private static GrudgePit sGrudgePit;
//    private Context context;
//    private SQLiteDatabase database;
//
//    public static GrudgePit get(Context context) {
//        if (sGrudgePit == null) {
//            sGrudgePit = new GrudgePit(context);
//        }
//        return sGrudgePit;
//    }
//
//    private GrudgePit(Context context) {
//        this.context = context.getApplicationContext();
//        database = new GrudgeBaseHelper(this.context).getWritableDatabase();
//    }
//
//    public List<Grudge> getGrudges() {
//
//        List<Grudge> grudges = new ArrayList<>();
//
//        GrudgeCursorWrapper cursor = queryGrudges(null, null);
//
//        try {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                grudges.add(cursor.getGrudge());
//                cursor.moveToNext();
//            }
//        } finally {
//            cursor.close();
//        }
//        return grudges;
//    }
//
//    public Grudge getGrudge(UUID id) {
//        GrudgeCursorWrapper cursor = queryGrudges(
//                GrudgeTable.Cols.UUID + " = ?",
//                new String[] {id.toString()}
//        );
//
//        try {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//
//            cursor.moveToFirst();
//            return cursor.getGrudge();
//        } finally {
//            cursor.close();
//        }
//    }
//
//    public File getPhotoFile(Grudge grudge) {
//        File filesDir = context.getFilesDir();
//        return new File(filesDir, grudge.getPhotoFilename());
//    }
//
//    public void addGrudge(Grudge grudge) {
//        ContentValues values = getContentValues(grudge);
//        database.insert(GrudgeTable.NAME, null, values);
//    }
//
//    public void deleteGrudge(Grudge grudge) {
//        database.execSQL("DELETE FROM " + GrudgeTable.NAME
//                        + " WHERE " + GrudgeTable.Cols.UUID + "= '"
//                        + grudge.getId() + "'");
//    }
//
//    public void updateGrudge(Grudge grudge) {
//        String uuidString = grudge.getId().toString();
//        ContentValues values = getContentValues(grudge);
//
//        database.update(GrudgeTable.NAME, values,
//                GrudgeTable.Cols.UUID + " = ?",
//                new String[] {uuidString});
//    }
//
//    private GrudgeCursorWrapper queryGrudges(String whereClause, String[] whereArgs) {
//        Cursor cursor = database.query(
//                GrudgeTable.NAME,
//                null, // columns - null selects all columns
//                whereClause,
//                whereArgs,
//                null, // groupBy
//                null, // having
//                null // orderBy
//        );
//        return new GrudgeCursorWrapper(cursor);
//    }
//
//    private static ContentValues getContentValues(Grudge grudge) {
//        ContentValues values = new ContentValues();
//        values.put(GrudgeTable.Cols.UUID, grudge.getId().toString());
//        values.put(GrudgeTable.Cols.TITLE, grudge.getTitle());
//        values.put(GrudgeTable.Cols.DATE, grudge.getDate().getTime());
//        values.put(GrudgeTable.Cols.TIME, grudge.getTime());
//        values.put(GrudgeTable.Cols.REMIND, grudge.isRemind() ? 1 : 0);
//        values.put(GrudgeTable.Cols.REVENGE, grudge.isRevenge() ? 1 : 0);
//        values.put(GrudgeTable.Cols.REVENGED, grudge.isRevenged() ? 1 : 0);
//        values.put(GrudgeTable.Cols.FORGIVE, grudge.isForgiven() ? 1 : 0);
//        values.put(GrudgeTable.Cols.VICTIM, grudge.getVictim());
//        values.put(GrudgeTable.Cols.DESCRIPTION, grudge.getDescription());
//        values.put(GrudgeTable.Cols.GENDER, grudge.getGender());
//
//        return values;
//    }
}
