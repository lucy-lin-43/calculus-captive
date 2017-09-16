package ca.sfu.group04termproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * StoryLevelSelectActivity class manages UI application flow
 * from the StoryLevelSelect activity based on the selected level.
 *
 */

public class StoryLevelSelectActivity extends Activity {

    private String ROOMS_ESCAPED;
    private String STORY_LEVEL;

    private String description;
    private String titleId;

    private int roomId;

    private DBAccess dbAccess;
    private ProfileModel profileModel;

    Button level01button;
    Button level02button;
    Button level03button;
    Button level04button;
    Button level05button;
    Button level06button;
    Button level07button;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_level_select);

        ROOMS_ESCAPED = getString(R.string.shared_preferences_rooms_escaped);
        STORY_LEVEL = getString(R.string.level_story_id);

        profileModel= new ProfileModel(this);

        initializeButtonState();
    }

    private void initializeButtonState() {

        level01button = (Button) findViewById(R.id.button_story_level_select_level_01);
        titleId = level01button.getText().toString();
        if (!isUnlocked(1)) { level01button.setSelected(true); }

        level02button = (Button) findViewById(R.id.button_story_level_select_level_02);
        titleId = level02button.getText().toString();
        if (!isUnlocked(2)) { level02button.setSelected(true); }

        level03button = (Button) findViewById(R.id.button_story_level_select_level_03);
        titleId = level03button.getText().toString();
        if (!isUnlocked(3)) { level03button.setSelected(true); }

        level04button = (Button) findViewById(R.id.button_story_level_select_level_04);
        titleId = level04button.getText().toString();
        if (!isUnlocked(4)) { level04button.setSelected(true); }

        level05button = (Button) findViewById(R.id.button_story_level_select_level_05);
        titleId = level05button.getText().toString();
        if (!isUnlocked(5)) { level05button.setSelected(true); }

        level06button = (Button) findViewById(R.id.button_story_level_select_level_06);
        titleId = level06button.getText().toString();
        if (!isUnlocked(6)) { level06button.setSelected(true); }

        level07button = (Button) findViewById(R.id.button_story_level_select_level_07);
        titleId = level07button.getText().toString();
        if (!isUnlocked(7)) { level07button.setSelected(true); }

    }


    // Called after any Room button is clicked (android:onClick).
	public void loadRoom(View view) {

        Button button = (Button) view;
        titleId = button.getText().toString();

        dbAccess = DBAccess.getInstance(this);
        dbAccess.open();
        roomId = dbAccess.getRoomId(titleId);
        dbAccess.close();


        if (isUnlocked(roomId)) {
            startQuiz();
        } else {
            showMessage(getString(R.string.story_level_select_activity_alertdialog_locked_room_title), getString(R.string.story_level_select_activity_alertdialog_locked_room_message));
        }
    }


	// Check if the Room has been unlocked.
    private boolean isUnlocked(int id){
        boolean result = true;

        int currRoom = profileModel.getStat(ROOMS_ESCAPED);

        if (id > currRoom){
            result = false;
        }

        return result;
    }


    // Start Main activity.
	public void startMainActivity(View view) {
		Intent intent = new Intent(StoryLevelSelectActivity.this, MainActivity.class);
		startActivity(intent);
	}


    // Start Quiz activity.
    private void startQuiz() {
        dbAccess = DBAccess.getInstance(this);
        dbAccess.open();
        description = dbAccess.getRoomDescription(titleId);
        dbAccess.close();

        Intent intent = new Intent(StoryLevelSelectActivity.this, StoryTellerActivity.class);
        intent.putExtra(getString(R.string.extras_title_id), titleId);
        intent.putExtra(getString(R.string.extras_description_id), description);
        intent.putExtra(getString(R.string.extras_level_id), STORY_LEVEL);
        startActivity(intent);
    }


	// Show AlertDialog pop-up message.
	private void showMessage(String title, String message) {
        final Dialog dialog = new Dialog(StoryLevelSelectActivity.this);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.locked_room_dialog_layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.GRAY800);
        dialog.show();

        TextView textViewLockedRoomDes = (TextView) dialog.findViewById(R.id.textView_locked_room_description);
        textViewLockedRoomDes.setText(message);

        Button buttonOk = (Button) dialog.findViewById(R.id.button_locked_room_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
	}
}
