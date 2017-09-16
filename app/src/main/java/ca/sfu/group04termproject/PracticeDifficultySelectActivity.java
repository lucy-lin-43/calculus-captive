package ca.sfu.group04termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * PracticeDifficultySelectActivity class manages UI application flow
 * from the PracticeDifficultySelect activity based on the selected difficulty.
 *
 */

public class PracticeDifficultySelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice_difficulty_select);
	}

	// Start PracticeLevelSelect activity.
	public void startPracticeLevelSelectActivity(View view) {
		Intent intent = new Intent(PracticeDifficultySelectActivity.this, PracticeLevelSelectActivity.class);
		startActivity(intent);
	}

	// Start Quiz activity.
	public void startQuizActivity(View view) {
		Bundle extras = getIntent().getExtras();
		String topic = extras.getString(getString(R.string.extras_topic_id));

		Button button = (Button) view;
		String difficulty = button.getText().toString();

		// Pass selected Topic ID, Difficulty ID, and Level ID to QuizActivity.
		Intent intent = new Intent(PracticeDifficultySelectActivity.this, QuizActivity.class);
		intent.putExtra(getString(R.string.extras_topic_id), topic);
		intent.putExtra(getString(R.string.extras_difficulty_id), difficulty);
        intent.putExtra(getString(R.string.extras_level_id), getString(R.string.level_practice_id));

        startActivity(intent);
    }
}
