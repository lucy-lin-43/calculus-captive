package ca.sfu.group04termproject;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * QuizActivity class is a View component of the Quiz object.
 * It manages UI elements content and application flow in the Quiz activity.
 *
 * Private Variables:
 * - quizController:      Quiz Controller component;
 * - dbAccess:            QuestionsDB Access Helper object;
 * - topicId:             Quiz topic ID;
 * - difficultyId:        Quiz difficulty ID;
 * - levelId:             Quiz level ID;
 *
 */

public class QuizActivity extends Activity {

	private final static int MAX_QUESTION_COUNT = 10;

	private QuizController quizController;
	private TextView textViewQuestionId;
	private TextView textViewQuestionDescription;

	private DBAccess dbAccess;

	private String topicId;
	private String difficultyId;
	private String levelId;
	private String title;

	private Button quizAnswerButton01;
	private Button quizAnswerButton02;
	private Button quizAnswerButton03;
	private Button quizAnswerButton04;

	ArrayList<Integer> correctAnswersArray;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		// Get Topic ID, Difficulty ID, and Level ID from caller activity.
		Bundle extras = getIntent().getExtras();
		topicId = extras.getString(getString(R.string.extras_topic_id));
		difficultyId = extras.getString(getString(R.string.extras_difficulty_id));
		levelId = extras.getString(getString(R.string.extras_level_id));
		title = extras.getString(getString(R.string.extras_title_id));

		// Open "QuestionsDB.db" database.
		dbAccess = DBAccess.getInstance(this);
		dbAccess.open();

		ArrayList<QuestionModel> questions = dbAccess.getQuestions(topicId, difficultyId);

		if (questions.size() > MAX_QUESTION_COUNT) {
			questions.subList(MAX_QUESTION_COUNT, questions.size()).clear();
		}

		quizController = new QuizController(questions);
		correctAnswersArray = quizController.getCorrectAnswerIds();

		textViewQuestionId = (TextView) findViewById(R.id.textView_quiz_question_number);
		textViewQuestionDescription = (TextView) findViewById(R.id.textView_quiz_question_description);

		setupQuizAnswerButton01();
		setupQuizAnswerButton02();
		setupQuizAnswerButton03();
		setupQuizAnswerButton04();

		setupHintButton();
		setupNextButton();

		updateQuestionNumber();
		updateQuestionDescription();
		updateAnswerButtons();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAccess.close();
	}


	// Setup Hint Button Listener
	private void setupHintButton() {
		Button buttonHint = (Button) findViewById(R.id.button_quiz_hint);
		buttonHint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(QuizActivity.this);
				dialog.setTitle("HINT");
				dialog.setContentView(R.layout.hint_dialog_layout);
				dialog.show();
				dialog.getWindow().setBackgroundDrawableResource(R.color.GRAY800);

				QuestionModel question = quizController.getCurrentQuestion();
				String questionTopic = question.getQuestionTopic();
				String questionHint = question.getQuestionHint();

				// Set Hint Title
				TextView textViewHintTitle = (TextView) dialog.findViewById(R.id.textView_hint_title);
				textViewHintTitle.setText(questionTopic);

				// Set Hint Description
				TextView textViewHintDescription = (TextView) dialog.findViewById(R.id.textView_hint_description);
				textViewHintDescription.setText(questionHint);

				Button buttonDone = (Button) dialog.findViewById(R.id.button_hint_done);
				buttonDone.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
			}
		});
	}


	// Setup Quiz Answer Buttons
	private void setupQuizAnswerButton01() {

		quizAnswerButton01 = (Button) findViewById(R.id.button_quiz_answer_01);
		quizAnswerButton01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				view.setSelected(true);
				quizAnswerButton02.setSelected(false);
				quizAnswerButton03.setSelected(false);
				quizAnswerButton04.setSelected(false);
			}
		});
	}

	private void setupQuizAnswerButton02() {
		quizAnswerButton02 = (Button) findViewById(R.id.button_quiz_answer_02);
		quizAnswerButton02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				view.setSelected(true);
				quizAnswerButton01.setSelected(false);
				quizAnswerButton03.setSelected(false);
				quizAnswerButton04.setSelected(false);
			}
		});
	}

	private void setupQuizAnswerButton03() {
		quizAnswerButton03 = (Button) findViewById(R.id.button_quiz_answer_03);
		quizAnswerButton03.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				view.setSelected(true);
				quizAnswerButton01.setSelected(false);
				quizAnswerButton02.setSelected(false);
				quizAnswerButton04.setSelected(false);
			}
		});
	}

	private void setupQuizAnswerButton04() {
		quizAnswerButton04 = (Button) findViewById(R.id.button_quiz_answer_04);
		quizAnswerButton04.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				view.setSelected(true);
				quizAnswerButton01.setSelected(false);
				quizAnswerButton02.setSelected(false);
				quizAnswerButton03.setSelected(false);
			}
		});
	}


	// Setup Next Button Listener
	private void setupNextButton() {
		Button buttonNext = (Button) findViewById(R.id.button_quiz_next);
		
		buttonNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				int pos = validateSelected();
				int correctPos = correctAnswersArray.get(quizController.getCurrentCorrectAnswerIndex());

				// Compare user selected position and correct answer position.
				if (correctPos == pos) {
					quizController.incrementQuizScore();
				}

				// DEBUG
				// Toast.makeText(getApplicationContext(), "Selected Answer: " + Integer.toString(pos+1) + " Correct Answer: " + Integer.toString(correctPos+1), Toast.LENGTH_SHORT).show();

				quizAnswerButton01.setSelected(false);
				quizAnswerButton02.setSelected(false);
				quizAnswerButton03.setSelected(false);
				quizAnswerButton04.setSelected(false);

				if (quizController.getQuestionCurrentId() < quizController.getQuestionTotalCount()-1){
					quizController.nextQuestion();
					updateQuestionNumber();
					updateQuestionDescription();
					updateAnswerButtons();
				} else {
					startGameOver();
				}
			}
		});
	}


	// Update Current Question Number
	private void updateQuestionNumber() {
		String questionNumber = String.format(Locale.getDefault(), "Question: %d / %d", quizController.getQuestionCurrentId()+1, quizController.getQuestionTotalCount());
		textViewQuestionId.setText(questionNumber);
	}


	// Update Current Question Description
	private void updateQuestionDescription() {
		QuestionModel question = quizController.getCurrentQuestion();
		String questionDescription = question.getQuestionDescription();

		textViewQuestionDescription.setText(questionDescription);
	}


	// Update Answer Buttons
	private void updateAnswerButtons() {

		QuestionModel question = quizController.getCurrentQuestion();
		int correctPos = correctAnswersArray.get(quizController.getCurrentCorrectAnswerIndex());

		switch (correctPos) {
			case 0:
				quizAnswerButton01.setText(question.getQuestionAnswer());
				quizAnswerButton02.setText(question.getQuestionIncorrectAnswer01());
				quizAnswerButton03.setText(question.getQuestionIncorrectAnswer02());
				quizAnswerButton04.setText(question.getQuestionIncorrectAnswer03());
				break;

			case 1:
				quizAnswerButton01.setText(question.getQuestionIncorrectAnswer01());
				quizAnswerButton02.setText(question.getQuestionAnswer());
				quizAnswerButton03.setText(question.getQuestionIncorrectAnswer02());
				quizAnswerButton04.setText(question.getQuestionIncorrectAnswer03());
				break;

			case 2:
				quizAnswerButton01.setText(question.getQuestionIncorrectAnswer01());
				quizAnswerButton02.setText(question.getQuestionIncorrectAnswer02());
				quizAnswerButton03.setText(question.getQuestionAnswer());
				quizAnswerButton04.setText(question.getQuestionIncorrectAnswer03());
				break;

			case 3:
				quizAnswerButton01.setText(question.getQuestionIncorrectAnswer01());
				quizAnswerButton02.setText(question.getQuestionIncorrectAnswer02());
				quizAnswerButton03.setText(question.getQuestionIncorrectAnswer03());
				quizAnswerButton04.setText(question.getQuestionAnswer());
				break;

			default:
				break;
		}
	}


	// Start Appropriate GameOver Activity
	private void startGameOver() {
		Bundle extras = getIntent().getExtras();
		Intent intent;

		if ((extras.getString(getString(R.string.extras_level_id))).equals(getString(R.string.level_practice_id))) {
			intent = new Intent(QuizActivity.this, PracticeGameOverActivity.class);
		} else {
			intent = new Intent(QuizActivity.this, StoryGameOverActivity.class);
		}

		intent.putExtra(getString(R.string.extras_score_id), quizController.getQuizScore());
		intent.putExtra(getString(R.string.extras_topic_id), topicId);
		intent.putExtra(getString(R.string.extras_difficulty_id), difficultyId);
		intent.putExtra(getString(R.string.extras_level_id), levelId);
		intent.putExtra(getString(R.string.extras_title_id), title);
		intent.putExtra(getString(R.string.extras_total_score_id), quizController.getQuestionTotalCount());
		intent.putExtra(getString(R.string.extras_valid_score_id), quizController.getQuestionTotalCount() / 2);

		startActivity(intent);
	}


	// Helper function that returns the position of button selected by user, with range from 0 to 3.
	private int validateSelected() {
		if(quizAnswerButton01.isSelected()){
			return 0;
		}
		if(quizAnswerButton02.isSelected()){
			return 1;
		}
		if(quizAnswerButton03.isSelected()){
			return 2;
		}
		if(quizAnswerButton04.isSelected()){
			return 3;
		}

		return -1;
	}
}
