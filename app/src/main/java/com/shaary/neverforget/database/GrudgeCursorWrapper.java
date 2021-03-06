//package com.shaary.neverforget.database;
//
//import android.database.Cursor;
//import android.database.CursorWrapper;
//
//import com.shaary.neverforget.database.GrudgeDbSchema.GrudgeTable;
//import com.shaary.neverforget.model.Grudge;
//
//import java.util.Date;
//import java.util.UUID;
//
//public class GrudgeCursorWrapper extends CursorWrapper {
//    /**
//     * Creates a cursor wrapper.
//     *
//     * @param cursor The underlying cursor to wrap.
//     */
//    public GrudgeCursorWrapper(Cursor cursor) {
//        super(cursor);
//    }
//
//    public Grudge getGrudge() {
//        String uuidString = getString(getColumnIndex(GrudgeTable.Cols.UUID));
//        String title = getString(getColumnIndex(GrudgeTable.Cols.TITLE));
//        long date = getLong(getColumnIndex(GrudgeTable.Cols.DATE));
//        String time = getString(getColumnIndex(GrudgeTable.Cols.TIME));
//        int isRemind = getInt(getColumnIndex(GrudgeTable.Cols.REMIND));
//        int isRevenge = getInt(getColumnIndex(GrudgeTable.Cols.REVENGE));
//        int isRevenged = getInt(getColumnIndex(GrudgeTable.Cols.REVENGED));
//        int isForgive = getInt(getColumnIndex(GrudgeTable.Cols.FORGIVE));
//        String victim = getString(getColumnIndex(GrudgeTable.Cols.VICTIM));
//        String description = getString(getColumnIndex(GrudgeTable.Cols.DESCRIPTION));
//        String gender = getString(getColumnIndex(GrudgeTable.Cols.GENDER));
//
//        Grudge grudge = new Grudge(UUID.fromString(uuidString));
//        grudge.setTitle(title);
//        grudge.setDate(new Date(date));
//        grudge.setFormattedTime(time);
//        grudge.setRemind(isRemind != 0);
//        grudge.setRevenge(isRevenge != 0);
//        grudge.setRevenged(isRevenged != 0);
//        grudge.setForgiven(isForgive != 0);
//        grudge.setName(victim);
//        grudge.setDescription(description);
//        grudge.setGender(gender);
//
//        return grudge;
//    }
//}
