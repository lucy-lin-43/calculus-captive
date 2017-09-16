package ca.sfu.group04termproject;

/**
 * QuestionModel class is a Model component of the Question object.
 *
 * Private Variables:
 * - topic:                 question topic;
 * - difficulty:            question difficulty;
 * - description:           question description;
 * - answer:                question answer;
 * - hint:                  question hint;
 * - incorrectAnswer01:     incorrect answer 1;
 * - incorrectAnswer02:     incorrect answer 2;
 * - incorrectAnswer03:     incorrect answer 3;
 *
 */

public class QuestionModel {

    private String topic;
    private String difficulty;
    private String description;
    private String answer;
	private String hint;
    private String incorrectAnswer01;
    private String incorrectAnswer02;
    private String incorrectAnswer03;

    public QuestionModel(String topic, String difficulty, String description, String answer, String hint, String incorrectAnswer01, String incorrectAnswer02, String incorrectAnswer03) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.description = description;
        this.answer = answer;
	    this.hint = hint;
        this.incorrectAnswer01 = incorrectAnswer01;
        this.incorrectAnswer02 = incorrectAnswer02;
        this.incorrectAnswer03 = incorrectAnswer03;
    }

    // GETTERS
    public String getQuestionTopic() { return topic; }
    public String getQuestionDifficulty() { return difficulty; }
    public String getQuestionDescription() { return description; }
    public String getQuestionAnswer() { return answer; }
	public String getQuestionHint() { return hint; }
    public String getQuestionIncorrectAnswer01() { return incorrectAnswer01; }
    public String getQuestionIncorrectAnswer02() { return incorrectAnswer02; }
    public String getQuestionIncorrectAnswer03() { return incorrectAnswer03; }


    // SETTERS
    public void setQuestionTopic(String topic) { this.topic = topic; }
    public void setQuestionDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setQuestionDescription(String description) { this.description = description; }
    public void setQuestionAnswer(String answer) { this.answer = answer; }
	public void setQuestionHint(String hint) { this.hint = hint; }
}
