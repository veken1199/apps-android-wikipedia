package org.wikipedia.search;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.wikipedia.database.DatabaseTable;
import org.wikipedia.database.column.Column;
import org.wikipedia.database.column.LongColumn;
import org.wikipedia.database.column.StrColumn;

import java.util.Date;

public class RecentSearchDatabaseTable extends DatabaseTable<RecentSearch> {

    private static final int DB_VER_INTRODUCED = 5;

    private static final String COL_TEXT = "text";
    private static final String COL_TIMESTAMP = "timestamp";

    public static final String[] SELECTION_KEYS = {
            COL_TEXT
    };

    @Override
    public RecentSearch fromCursor(Cursor c) {
        String title = getString(c, COL_TEXT);
        Date timestamp = new Date(getLong(c, COL_TIMESTAMP));
        return new RecentSearch(title, timestamp);
    }

    @Override
    protected ContentValues toContentValues(RecentSearch obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TEXT, obj.getText());
        contentValues.put(COL_TIMESTAMP, obj.getTimestamp().getTime());
        return contentValues;
    }

    @Override
    public String getTableName() {
        return "recentsearches";
    }

    @Override
    protected int getDBVersionIntroducedAt() {
        return DB_VER_INTRODUCED;
    }

    @Override
    public Column<?>[] getColumnsAdded(int version) {
        switch (version) {
            case DB_VER_INTRODUCED:
                return new Column<?>[] {
                        new LongColumn("_id", "integer primary key"),
                        new StrColumn(COL_TEXT, "string"),
                        new LongColumn(COL_TIMESTAMP, "integer"),
                };
            default:
                return super.getColumnsAdded(version);
        }
    }

    @Override
    protected String getPrimaryKeySelection(@NonNull RecentSearch obj, @NonNull String[] selectionArgs) {
        return super.getPrimaryKeySelection(obj, SELECTION_KEYS);
    }

    @Override
    protected String[] getUnfilteredPrimaryKeySelectionArgs(@NonNull RecentSearch obj) {
        return new String[] {
                obj.getText(),
        };
    }
}