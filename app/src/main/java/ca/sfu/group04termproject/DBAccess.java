package ca.sfu.group04termproject;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBAccess class implements data extraction from QuestionsDB database.
 *
 */

public class DBAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DBAccess instance;

    public static final String KEY_ROW_ID = "_ID";
    public static final String KEY_TOPIC = "TOPIC";
    public static final String KEY_DIFFICULTY = "DIFFICULTY";
    public static final String KEY_QUESTION = "QUESTION";
    public static final String KEY_ANSWER = "ANSWER";
    public static final String KEY_HINT = "HINT";
	public static final String KEY_TYPE = "TYPE";
    public static final String KEY_INCORRECT_ANSWER_01 = "INCORRECT_ANSWERS_01";
    public static final String KEY_INCORRECT_ANSWER_02 = "INCORRECT_ANSWERS_02";
    public static final String KEY_INCORRECT_ANSWER_03 = "INCORRECT_ANSWERS_03";

    public static final int COL_ROW_ID = 0;
    public static final int COL_TOPIC = 1;
    public static final int COL_DIFFICULTY = 2;
    public static final int COL_QUESTION = 3;
    public static final int COL_ANSWER = 4;
    public static final int COL_HINT = 5;
	public static final int COL_TYPE = 6;
    public static final int COL_INCORRECT_ANSWER_01 = 7;
    public static final int COL_INCORRECT_ANSWER_02 = 8;
    public static final int COL_INCORRECT_ANSWER_03 = 9;


    public static final String CLUES_TABLE = "CluesTable";
    public static final int CLUES_COL_ROW_ID = 0;
    public static final int CLUES_COL_ROOM = 1;
    public static final int CLUES_COL_ROOM_ID = 2;
    public static final int CLUES_COL_CLUE = 3;

    public static final String ROOMS_TABLE = "RoomsTable";
    public static final int ROOMS_COL_ROW_ID = 0;
    public static final int ROOMS_COL_ROOM = 1;
    public static final int ROOMS_COL_ROOM_ID = 2;
    public static final int ROOMS_COL_DESCRIPTION = 3;


    private DBAccess(Context ctx){
        this.openHelper = new DBAssetHelper(ctx);
    }

    public static DBAccess getInstance(Context ctx) {
        if (instance == null){
            instance = new DBAccess(ctx);
        }

        return instance;
    }


    public void open() {
        this.db = openHelper.getWritableDatabase();
    }


    public void close() {
        if (this.db != null) {
            this.db.close();
        }
    }


    // Extract new clue for each Room.
    public String getNewClues(String room ){
        Cursor cursor = this.db.rawQuery("select * from " + CLUES_TABLE + " where ROOM = ?", new String[] {room});
        String newClue = cursor.getString(CLUES_COL_CLUE);

	    cursor.close();
	    this.close();

        return newClue;
    }


    // Extract all collected clues from CluesCollectedTable.
    public String getCollectedClues(int numberOfClues) {
        String allClues = "";

        Cursor cursor = this.db.rawQuery("select * from " + CLUES_TABLE, null);
        cursor.moveToFirst();

        for (int i = 0; i < numberOfClues; i++) {
            allClues = allClues +cursor.getString(CLUES_COL_CLUE)+ "\n\n";
            cursor.moveToNext();
        }

	    cursor.close();
	    this.close();

        return allClues;
    }


    // Get Room ID from RoomsTable.
    public int getRoomId(String room ) {
        Cursor cursor = this.db.rawQuery("select * from " + ROOMS_TABLE + " where ROOM = ?", new String[] {room});
        cursor.moveToFirst();
        int roomId = cursor.getInt(ROOMS_COL_ROOM_ID);

        cursor.close();
        this.close();

        return roomId;
    }


    // Get Room description from RoomsTable.
    public String getRoomDescription(String room ) {
        Cursor cursor = this.db.rawQuery("select * from " + ROOMS_TABLE + " where ROOM = ?", new String[] {room});
        cursor.moveToFirst();
        String roomDescription = cursor.getString(ROOMS_COL_DESCRIPTION);

        cursor.close();
        this.close();

        return roomDescription;
    }


    // Get next Room title.
    public String getNextRoom(String currRoom){
        String result = currRoom;
        Cursor cursor = this.db.rawQuery("select * from " + ROOMS_TABLE, null);
        cursor.moveToFirst();

        do {
            if (currRoom.equals(cursor.getString(ROOMS_COL_ROOM))){
                int currRoomId = cursor.getInt(ROOMS_COL_ROOM_ID);
                if (cursor.moveToNext() && (cursor.getInt(ROOMS_COL_ROOM_ID) > currRoomId) ){
                    result = cursor.getString(ROOMS_COL_ROOM);
                    cursor.close();
                    this.close();
                    return result;
                }
            }
        } while(cursor.moveToNext());

        cursor.close();
        this.close();

        return result;
    }


    public int getNumRooms() {
        int numRooms = 0;
        Cursor cursor = this.db.rawQuery("select * from " + ROOMS_TABLE, null);
        numRooms = cursor.getCount();

        cursor.close();
        this.close();

        return numRooms;
    }


    // Get a list of questions that contain specified Topic and Difficulty.
    public ArrayList<QuestionModel> getQuestions(String topic, String difficulty) {
	    Cursor cursor = this.db.rawQuery("select * from QuestionsTable where TOPIC = ? and DIFFICULTY = ? order by random()", new String[] {topic, difficulty});

	    ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
	    QuestionModel q;

	    for (int i = 0; i < cursor.getCount(); i++) {
		    cursor.moveToNext();

		    q = new QuestionModel(cursor.getString(COL_TOPIC),
				    cursor.getString(COL_DIFFICULTY), cursor.getString(COL_QUESTION),
				    cursor.getString(COL_ANSWER), cursor.getString(COL_HINT),
				    cursor.getString(COL_INCORRECT_ANSWER_01), cursor.getString(COL_INCORRECT_ANSWER_02), cursor.getString(COL_INCORRECT_ANSWER_03));

		    questions.add(q);
	    }

	    cursor.close();
	    this.close();

        return questions;
    }


    // Get a random question that contains specified Topic and Difficulty.
    public QuestionModel getRandomQuestion(String topic, String difficulty) {
        Cursor cursor = this.db.rawQuery("select * from QuestionsTable where TOPIC = ? and DIFFICULTY = ? order by random() limit 1", new String[] {topic, difficulty});

	    QuestionModel question = new QuestionModel(cursor.getString(COL_TOPIC),
			    cursor.getString(COL_DIFFICULTY), cursor.getString(COL_QUESTION),
			    cursor.getString(COL_ANSWER), cursor.getString(COL_HINT), cursor.getString(COL_INCORRECT_ANSWER_01), cursor.getString(COL_INCORRECT_ANSWER_02), cursor.getString(COL_INCORRECT_ANSWER_03));

	    cursor.close();
	    this.close();

        return question;
    }
}
