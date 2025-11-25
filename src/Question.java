public class Question {
    private String question;
    private String[] choices;
    private int answerIndex;
    private String category;
    private String difficulty;

    public Question(String question, String[] choices, int answerIndex, String category, String difficulty) {
        this.question = question;
        this.choices = choices;
        this.answerIndex = answerIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestion() { return question; }
    public String[] getChoices() { return choices; }
    public int getAnswerIndex() { return answerIndex; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
}
