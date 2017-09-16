package ca.sfu.group04termproject;

import java.util.ArrayList;
import java.util.Random;

/**
 * QuizModel class is a Model component of the Quiz object.
 *
 * Private Variables:
 * - question:            array list of Questions;
 * - correctAnswerIds:    randomly generated array list of correct answer IDs used to validate User answer.
 * - score:               total number of correctly answered questions;
 *
 */

public class QuizModel {

	private ArrayList<QuestionModel> questions;
	private ArrayList<Integer> correctAnswerIds;

	private int score;

	private static Random randomGenerator;
	
	public QuizModel(ArrayList<QuestionModel> questionList) {

		questions = questionList;
		correctAnswerIds = new ArrayList<Integer>();

		score = 0;
		randomGenerator = new Random();

		generateAnswerIds();
	}

	// GETTERS
	public ArrayList<Integer> getCorrectAnswerIds() { return correctAnswerIds; }
	public QuestionModel getQuestion(int questionId) { return questions.get(questionId); }
	public int getScore() { return score; }


	// SETTERS
	public void setScore(int score) { this.score = score; }
	public void incrementScore() { this.score++; }


	// Generate random Answer IDs.
	private void generateAnswerIds() {
		for (int i = 0; i < questions.size(); i++) {
			correctAnswerIds.add(randInt(0, 3));
		}
	}

	// Generate a random integer in the [MIN, MAX] range.
	private static int randInt(int min, int max) {
		return randomGenerator.nextInt((max - min) + 1) + min;
	}
}

