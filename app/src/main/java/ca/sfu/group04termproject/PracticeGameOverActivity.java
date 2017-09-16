package ca.sfu.group04termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * PracticeGameOverActivity class manages UI application flow from the PracticeGameOver activity.
 *
 */

public class PracticeGameOverActivity extends Activity {

	private String topicId;
	private String difficultyId;
	private String levelId;

	private int quizScore;
	private int totalQuizScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice_game_over);

		// Get Topic ID, Difficulty ID, Level ID, and Quiz Score from caller activity.
		Bundle extras = getIntent().getExtras();
		topicId = extras.getString(getString(R.string.extras_topic_id));
		difficultyId = extras.getString(getString(R.string.extras_difficulty_id));
		levelId = extras.getString(getString(R.string.extras_level_id));
		quizScore = extras.getInt(getString(R.string.extras_score_id));
		totalQuizScore = extras.getInt(getString(R.string.extras_total_score_id));

		TextView tvQuizScore = (TextView) findViewById(R.id.textView_game_over_quiz_score);
		tvQuizScore.setText("Your Score: " + quizScore + "/" + totalQuizScore);
	}
	
	public void startMainActivity(View view) {
        Intent intent = new Intent(PracticeGameOverActivity.this, MainActivity.class);
        startActivity(intent);
    }
	
	public void startQuizActivity(View view) {
        Intent intent = new Intent(PracticeGameOverActivity.this, QuizActivity.class);
		intent.putExtra(getString(R.string.extras_topic_id), topicId);
		intent.putExtra(getString(R.string.extras_difficulty_id), difficultyId);
		intent.putExtra(getString(R.string.extras_level_id), levelId);

        startActivity(intent);
    }
}
