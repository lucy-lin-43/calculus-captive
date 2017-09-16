package ca.sfu.group04termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * PracticeLevelSelectActivity class manages UI application flow
 * from the PracticeLevelSelect activity based on the selected topic.
 *
 */

public class PracticeLevelSelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice_level_select);
	}

	// Start PracticeDifficultySelect activity.
	public void startPracticeDifficultySelectActivity(View view) {
		Button button = (Button) view;
		String topic = button.getText().toString();

        Intent intent = new Intent(PracticeLevelSelectActivity.this, PracticeDifficultySelectActivity.class);
		intent.putExtra(getString(R.string.extras_topic_id), topic);

        startActivity(intent);
    }

	// Start Main activity.
	public void startMainActivity(View view) {
		Intent intent = new Intent(PracticeLevelSelectActivity.this, MainActivity.class);
		startActivity(intent);
	}
}
