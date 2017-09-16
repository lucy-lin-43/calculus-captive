package ca.sfu.group04termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ProfileActivity class manages UI elements content in the Profile activity.
 *
 */

public class ProfileActivity extends Activity {

    private String NUMBER_OF_CLUES;
    private String ROOMS_ESCAPED;
    private String ROOMS_FAILED;

    private int maxClues;

    private DBAccess dbAccess;

    private ProfileModel profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbAccess = DBAccess.getInstance(this);
        profileModel = new ProfileModel(this);

        NUMBER_OF_CLUES = getString(R.string.shared_preferences_number_of_clues);
	    ROOMS_ESCAPED = getString(R.string.shared_preferences_rooms_escaped);
	    ROOMS_FAILED = getString(R.string.shared_preferences_rooms_failed);

        displayClues();
        displayProgressBar();
        displayRoomsEscaped();
        displayRoomsFailed();
    }

	// Start Main activity.
	public void startMainActivity(View view) {
		Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
		startActivity(intent);
	}

    // Extract and display clues collected so far.
    private void displayClues() {

        // Get number of clues the user has collected from SharedPreferences.
        int numberOfClues = profileModel.getStat(NUMBER_OF_CLUES);
        if (numberOfClues > 0) {
            dbAccess.open();
            String cluesCollected = dbAccess.getCollectedClues(numberOfClues);
            TextView textViewClue = (TextView) findViewById(R.id.textView_profile_clue);
            textViewClue.setText(cluesCollected);
            dbAccess.close();
        }
    }


    private void displayRoomsEscaped() {
        String roomsEscaped = Integer.toString(profileModel.getStat(ROOMS_ESCAPED));

        String message = getString(R.string.profile_activity_textView_statistics_games_won) + " " + roomsEscaped;
        TextView textViewRoomsEscaped = (TextView) findViewById(R.id.textView_profile_statistics_games_won);
        textViewRoomsEscaped.setText(message);
    }


    private void displayRoomsFailed() {
        String roomsFailed = Integer.toString(profileModel.getStat(ROOMS_FAILED));

        String message = getString(R.string.profile_activity_textView_statistics_games_lost) + " " + roomsFailed;
        TextView textViewRoomsFailed = (TextView) findViewById(R.id.textView_profile_statistics_games_lost);
        textViewRoomsFailed.setText(message);
    }


    private void displayProgressBar() {
        dbAccess.open();
        maxClues = dbAccess.getNumRooms();
        dbAccess.close();

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_profile_story);
        progressBar.setMax(maxClues);
        progressBar.setProgress(profileModel.getStat(NUMBER_OF_CLUES));
    }
}
