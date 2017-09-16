package ca.sfu.group04termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * MainActivity class manages UI application flow from the Main activity.
 *
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// Start StoryLevelSelect activity.
	public void startStoryLevelSelectActivity(View view) {
        Intent intent = new Intent(MainActivity.this, StoryLevelSelectActivity.class);
        startActivity(intent);
    }

    // Start PracticeLevelSelect activity.
	public void startPracticeLevelSelectActivity(View view) {
        Intent intent = new Intent(MainActivity.this, PracticeLevelSelectActivity.class);
        startActivity(intent);
    }

    // Start Profile activity.
	public void startProfileActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
