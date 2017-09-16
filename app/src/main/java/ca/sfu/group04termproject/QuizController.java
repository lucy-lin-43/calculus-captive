package ca.sfu.group04termproject;

import java.util.ArrayList;

/**
 * QuizController class is a Controller component of the Quiz object.
 *
 * Private Variables:
 * - quizModel:                   Quiz Model component;
 * - currentCorrectAnswerIndex:   current correct question ID;
 * - questionCurrentId:           current question number;
 * - questionTotalCount:          total number of questions;
 *
 */

public class QuizController {

	private QuizModel quizModel;

	private int currentCorrectAnswerIndex;
	private int questionCurrentId;
	private int questionTotalCount;

	public QuizController(ArrayList<QuestionModel> questions) {
		this.currentCorrectAnswerIndex = 0;
		this.questionCurrentId = 0;
		this.questionTotalCount = questions.size();

		this.quizModel = new QuizModel(questions);
	}


	// GETTERS
	public int getCurrentCorrectAnswerIndex() { return currentCorrectAnswerIndex; }
	public int getQuestionCurrentId() { return questionCurrentId; }
	public int getQuestionTotalCount() { return questionTotalCount; }
	public int getQuizScore() { return quizModel.getScore(); }

	public ArrayList<Integer> getCorrectAnswerIds() { return quizModel.getCorrectAnswerIds(); }
	public QuestionModel getCurrentQuestion() { return quizModel.getQuestion(questionCurrentId); }
	public QuestionModel getQuestion(int questionId) { return quizModel.getQuestion(questionId); }


	// SETTERS
	public void setCurrentCorrectAnswerIndex(int correctAnswerIndex) { this.currentCorrectAnswerIndex = correctAnswerIndex; }
	public void setQuestionCurrentId(int currentId) { this.questionCurrentId = currentId; }
	public void setQuestionTotalCount(int totalCount) { this.questionTotalCount = totalCount; }


	public void incrementQuizScore() { quizModel.incrementScore(); }

	public void nextQuestion() {
		questionCurrentId++;
		currentCorrectAnswerIndex++;
	}
}
