package ca.sfu.group04termproject;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * DBAssetHelper opens and manages updates of QuestionsDB database.
 *
 */

public class DBAssetHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "QuestionsDB.db";

	// INCREMENT AFTER EVERY DB UPDATE!!!
    private static final int DATABASE_VERSION = 177;

    public DBAssetHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }
}
