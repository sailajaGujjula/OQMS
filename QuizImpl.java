package Services;
import Models.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class QuizImpl implements Models.Quiz {
    private List<Question> questions;
    private int totalScore;
    private int questionIndex;
    private Timer timer;
    private int timeLimitInSeconds;
    public QuizImpl(int timeLimitInSeconds) {
        this.questions = new ArrayList<>();
        this.totalScore = 0;
        this.questionIndex = 0;
        this.timeLimitInSeconds = timeLimitInSeconds;
    }
    @Override
    public void addQuestion(Question question) {
        questions.add(question);
    }
    @Override
    public void removeQuestion(Question question) {
        questions.remove(question);
    }
    @Override
    public void start() {
        System.out.println("Quiz started! You have " + timeLimitInSeconds + " seconds to complete each question.");

        timer = new Timer();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println(question.getQuestionText());
            String[] options = question.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }
            // Initialize the question timer
            TimerTask questionTimer = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up for this question!");
                    displayCorrectAnswer(questionIndex);
                    totalScore -= 1; // Deduct a point for running out of time
                    nextQuestion();
                }
            };
            timer.schedule(questionTimer, timeLimitInSeconds * 1000);

            System.out.print("Enter your choice (1-" + options.length + "): ");
            int userChoice = getUserChoice();
            // Cancel the question timer as the user has entered an answer
            questionTimer.cancel();

            if (userChoice - 1 == question.getCorrectOptionIndex()) {
                System.out.println("Correct!");
                totalScore++;
            } else {
                System.out.println("Incorrect!");
                displayCorrectAnswer(i);
            }
        }

        timer.cancel();
        System.out.println("Quiz completed!");
        System.out.println("Your total score: " + getTotalScore() + " out of " + questions.size());
    }
    @Override
    public int getTotalScore() {
        return totalScore;
    }
    private void displayCorrectAnswer(int questionIndex) {
        Question question = questions.get(questionIndex);
        int correctOptionIndex = question.getCorrectOptionIndex();
        System.out.println("Correct Answer: " + question.getOptions()[correctOptionIndex]);
    }
    private int getUserChoice() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        boolean validChoice = false;
        while (!validChoice) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= questions.get(questionIndex).getOptions().length) {
                    validChoice = true;
                } else {
                    System.out.print("Invalid choice. Please enter a number between 1 and " +
                            questions.get(questionIndex).getOptions().length + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid choice. Please enter a number between 1 and " +
                        questions.get(questionIndex).getOptions().length + ": ");
            }
        }
        return choice;
    }
    private void nextQuestion() {
        questionIndex++;
    }
    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
