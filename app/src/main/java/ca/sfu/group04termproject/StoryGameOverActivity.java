package ca.sfu.group04termproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * StoryGameOverActivity class manages UI application flow from the StoryGameOver activity.
 *
 */

public class StoryGameOverActivity extends Activity {

    private String title;
    private String difficultyId;
    private String levelId;
    private String topicId;

    private int roomId;

	private int quizScore;
    private int quizTotalScore;
    private int quizValidScore;


    private String PROFILE_SETTING;
    private String NUMBER_OF_CLUES;
    private String ROOMS_ESCAPED;
    private String ROOMS_FAILED;

    private DBAccess dbAccess;
	private ProfileModel profileModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_game_over);

        profileModel = new ProfileModel(this);
        dbAccess = DBAccess.getInstance(this);

	    PROFILE_SETTING = getString(R.string.shared_preferences_profile_setting);
	    NUMBER_OF_CLUES = getString(R.string.shared_preferences_number_of_clues);
	    ROOMS_ESCAPED = getString(R.string.shared_preferences_rooms_escaped);
	    ROOMS_FAILED = getString(R.string.shared_preferences_rooms_failed);

        getInfo();
        respondToResult();

        TextView tvQuizScore = (TextView) findViewById(R.id.textView_story_game_over_quiz_score);
        tvQuizScore.setText("Your Score: " + quizScore + "/" + quizTotalScore);
    }


    // Get Topic ID, Difficulty ID, Level ID, Title, and Quiz Score from caller activity.
    private void getInfo() {
        Bundle extras = getIntent().getExtras();

        topicId = extras.getString(getString(R.string.extras_topic_id));
        difficultyId = extras.getString(getString(R.string.extras_difficulty_id));
        levelId = extras.getString(getString(R.string.extras_level_id));
        title = extras.getString(getString(R.string.extras_title_id));
        quizScore = extras.getInt(getString(R.string.extras_score_id));
        quizTotalScore = extras.getInt(getString(R.string.extras_total_score_id));
	    quizValidScore = extras.getInt(getString(R.string.extras_valid_score_id));

        dbAccess.open();
        roomId = dbAccess.getRoomId(title);
        dbAccess.close();
    }

    private void respondToResult() {
        // Successful Escape
        if (isPassScore()) {

            if (isNewEscape()){
                updateStat(NUMBER_OF_CLUES);
                updateStat(ROOMS_ESCAPED);
            }

            setGameOverMessage(getString(R.string.story_game_over_activity_textView_you_escaped));
        }
        // Failed Escape
        else {
            updateStat(ROOMS_FAILED);
            setGameOverMessage(getString(R.string.story_game_over_activity_textView_escaped_failed));
        }
    }

	// Check if the User has passed the Quiz.
    public boolean isPassScore() {
        return (quizScore >= quizValidScore);
    }


    private void updateStat(String statistic) {
        int newStatValue = 1 + profileModel.getStat(statistic);
        profileModel.updateStat(statistic, newStatValue);
    }


    private boolean isNewEscape() {
        boolean isNewEscape = false;

	    // Assume Room 1 corresponds to roomId 1.
        if (roomId >= profileModel.getStat(ROOMS_ESCAPED)) {
            isNewEscape = true;
        }

        return isNewEscape;
    }


    private void setGameOverMessage(String message) {
        TextView textView = (TextView) findViewById(R.id.textView_story_game_over_result);
        textView.setText(message);
    }


    // After clicking on Main Menu (android:onClick):
    public void startMainActivity(View view) {
        Intent intent = new Intent(StoryGameOverActivity.this, MainActivity.class);
        startActivity(intent);
    }


    // After clicking on Continue (android:onClick):
    public void setUpContinue(View view){

        // Start next room level if current room was passed.
        if (isPassScore()) {
            startNextActivity();
        }

        // Start same room level if current room was failed.
        else {
            startQuiz(title);
        }
    }


    public void startNextActivity(){
        //Get next room
        dbAccess.open();
        String nextRoom = dbAccess.getNextRoom(title);
        dbAccess.close();

        // If current room is MAX.
        if (title.equals(nextRoom)) {
            startStoryLevelSelectActivity();
        }

        // Start next Quiz if next room is available.
        else {
            startQuiz(nextRoom);
        }

    }

	// Start StoryLevelSelect activity.
    public void startStoryLevelSelectActivity() {
        Intent intent = new Intent(StoryGameOverActivity.this, StoryLevelSelectActivity.class);
        startActivity(intent);
    }


    // Start Quiz activity.
    private void startQuiz(String nextRoom) {
        dbAccess = DBAccess.getInstance(this);
        dbAccess.open();
        String description = dbAccess.getRoomDescription(nextRoom);
        dbAccess.close();

        Intent intent = new Intent(StoryGameOverActivity.this, StoryTellerActivity.class);
        intent.putExtra(getString(R.string.extras_title_id), nextRoom);
        intent.putExtra(getString(R.string.extras_description_id), description);
        intent.putExtra(getString(R.string.extras_level_id), getString(R.string.level_story_id));
        startActivity(intent);
    }
}
