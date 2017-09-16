package ca.sfu.group04termproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ProfileModel class is a Model component of the Profile object.
 *
 */

public class ProfileModel {

	private String ALL_STATS;
    private String PROFILE_SETTING;
    private String NUMBER_OF_CLUES;
    private String ROOMS_ESCAPED;
    private String ROOMS_FAILED;

    private Context context;
    private SharedPreferences preferences;


    public ProfileModel(Context activityContext) {
        context = activityContext;
        preferences = context.getSharedPreferences(PROFILE_SETTING, context.MODE_PRIVATE);

	    ALL_STATS = context.getString(R.string.shared_preferences_all_stats);
	    PROFILE_SETTING = context.getString(R.string.shared_preferences_profile_setting);
	    NUMBER_OF_CLUES = context.getString(R.string.shared_preferences_number_of_clues);
	    ROOMS_ESCAPED = context.getString(R.string.shared_preferences_rooms_escaped);
	    ROOMS_FAILED = context.getString(R.string.shared_preferences_rooms_failed);
    }

    // Get Specific Statistic
    public int getStat(String stat) {
        int retrievedStat = preferences.getInt(stat, 0);
        return retrievedStat;
    }


    // Update Specific Statistic
    public void updateStat(String stat, int newStat) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(stat, newStat);
        editor.commit();
    }

    // Remove Statistics
    public void removeStat(String stat){
        SharedPreferences.Editor editor = preferences.edit();

        if (stat == ALL_STATS) {
            editor.remove(NUMBER_OF_CLUES);
            editor.remove(ROOMS_ESCAPED);
            editor.remove(ROOMS_FAILED);
            editor.commit();
        } else {
            editor.remove(stat);
            editor.commit();
        }
    }
}
