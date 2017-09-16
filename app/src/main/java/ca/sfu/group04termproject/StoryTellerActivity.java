package ca.sfu.group04termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * StoryTellerActivity class manages UI Story Mode text narrative.
 *
 */

public class StoryTellerActivity extends Activity {

	private static Random randomGenerator;

	private String title;
	private String description;
	private String levelId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_teller);

		randomGenerator = new Random();
		
		getInfo();
        setUpText();
	}

	private void getInfo() {
        Bundle extras = getIntent().getExtras();
        title = extras.getString(getString(R.string.extras_title_id));
        description = extras.getString(getString(R.string.extras_description_id));
        levelId = extras.getString(getString(R.string.extras_level_id));
    }

    private void setUpText() {
        TextView tvTitle = (TextView) findViewById(R.id.textView_story_teller_title);
        TextView tvDescription = (TextView) findViewById(R.id.textView_story_teller_description);

        tvTitle.setText(title);
        tvDescription.setText(description);
    }

	// Start StoryLevelSelect activity.
	public void startStoryLevelSelectActivity(View view) {
		Intent intent = new Intent(StoryTellerActivity.this, StoryLevelSelectActivity.class);
		startActivity(intent);
	}

	// Start Quiz activity.
	public void startQuizActivity(View view) {
        Intent intent = new Intent(StoryTellerActivity.this, QuizActivity.class);
		intent.putExtra(getString(R.string.extras_title_id), title);
		intent.putExtra(getString(R.string.extras_topic_id), getRandomTopic());
		intent.putExtra(getString(R.string.extras_difficulty_id), getRandomDifficulty());
		intent.putExtra(getString(R.string.extras_level_id), levelId);

        startActivity(intent);
    }

	// Get Random Difficulty
    private String getRandomDifficulty() {
	    ArrayList<String> diff = new ArrayList<String>();

	    diff.add(getString(R.string.db_difficulty_easy));
	    diff.add(getString(R.string.db_difficulty_medium));
	    diff.add(getString(R.string.db_difficulty_hard));

	    int index = randomGenerator.nextInt(diff.size());
	    return diff.get(index);
    }

    // Get Random Topic
	private String getRandomTopic() {
		ArrayList<String> topics = new ArrayList<String>();

		topics.add(getString(R.string.db_topic_differentiation));
		topics.add(getString(R.string.db_topic_functions));
		topics.add(getString(R.string.db_topic_integration));
		topics.add(getString(R.string.db_topic_limits));

		int index = randomGenerator.nextInt(topics.size());
		return topics.get(index);
	}
}
