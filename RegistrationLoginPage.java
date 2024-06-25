package MainClass;
import Models.User;
import Models.Question;
import Services.UserDatabase;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class RegistrationLoginPage {
    private static boolean quizCompleted = false; // Initialize the quiz completion status
    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;
        while (true) {
            if (loggedInUser == null) {
                System.out.println("1. Student Login");
                System.out.println("2. Teacher Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the scanner buffer
                switch (choice) {
                    case 1:
                        boolean studentLoginSuccess = false;
                        do {
                            System.out.println("1. Register");
                            System.out.println("2. Login");
                            System.out.println("3. Back");
                            System.out.print("Enter your choice: ");
                            int studentChoice = scanner.nextInt();
                            scanner.nextLine(); // Clear the scanner buffer
                            switch (studentChoice) {
                                case 1:
                                    System.out.print("Enter student username: ");
                                    String studentUsername = scanner.nextLine();
                                    System.out.print("Enter student password: ");
                                    String studentPassword = scanner.nextLine();

                                    if (userDatabase.addUser(studentUsername, studentPassword)) {
                                        System.out.println("Registration successful!");
                                    } else {
                                        System.out.println("Username already exists. Please choose a different username.");
                                    }
                                    break;
                                case 2:
                                    System.out.print("Enter student username: ");
                                    String studentUsernameLogin = scanner.nextLine();
                                    System.out.print("Enter student password: ");
                                    String studentPasswordLogin = scanner.nextLine();

                                    if (!userDatabase.isUserRegistered(studentUsernameLogin)) {
                                        System.out.println("Please register first.");
                                    } else {
                                        User student = userDatabase.getUser(studentUsernameLogin);

                                        if (student.getPassword().equals(studentPasswordLogin)) {
                                            System.out.println("Student login successful! Welcome, " + studentUsernameLogin + ".");
                                            loggedInUser = student;
                                            studentLoginSuccess = true;
                                            // Start the quiz for the student
                                            startQuiz(loggedInUser);
                                            loggedInUser = null; // Reset loggedInUser for possible future logins
                                        } else {
                                            System.out.println("Incorrect student details. Please enter correct credentials.");
                                        }
                                    }
                                    break;
                                case 3:
                                    studentLoginSuccess = true; // Return to the main menu
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } while (!studentLoginSuccess); // Repeat until student login is successful or back to the main menu
                      break;

                    case 2:
                        System.out.print("Enter teacher username: ");
                        String teacherUsername = scanner.nextLine();
                        System.out.print("Enter teacher password: ");
                        String teacherPassword = scanner.nextLine();

                        // Check if the teacher login is valid using the isValidTeacher method
                        if (isValidTeacher(teacherUsername, teacherPassword)) {
                            System.out.println("Teacher login successful! Welcome, " + teacherUsername + ".");
                            // Add teacher-specific functionality here
                            // For example, allow teachers to add quiz questions
                        } else {
                            System.out.println("Incorrect teacher details. Please enter correct credentials.");
                        }
                        break;

                    case 3:
                        System.out.println("Exiting the application.");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
    // Method to validate teacher login
    private static boolean isValidTeacher(String username, String password) {
        // Define a list of valid teacher usernames and their corresponding passwords
        Map<String, String> validTeacherCredentials = new HashMap<>();
        validTeacherCredentials.put("teacher1", "teacherpassword");
        validTeacherCredentials.put("teacher2", "teacherpassword2");
        // Add more teacher credentials as needed
        return validTeacherCredentials.containsKey(username) && validTeacherCredentials.get(username).equals(password);
    }
    private static void startQuiz(User user) {
        Scanner scanner = new Scanner(System.in);
        // Define quiz questions (replace this with your actual questions)
        List<Question> quizQuestions = new ArrayList<>();
        // Add more questions here
        quizQuestions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Rome"}, 0));
        quizQuestions.add(new Question("What is the largest mammal?", new String[]{"Elephant", "Blue Whale", "Lion", "Giraffe"}, 1));
        quizQuestions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        quizQuestions.add(new Question("Which planet is known as the Red Planet?", new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, 1));
        quizQuestions.add(new Question("What is the chemical symbol for gold?", new String[]{"Au", "Ag", "Cu", "Fe"}, 0));
        quizQuestions.add(new Question("Who wrote 'Romeo and Juliet'?", new String[]{"Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"}, 2));
        quizQuestions.add(new Question("What is the largest organ in the human body?", new String[]{"Heart", "Liver", "Lungs", "Skin"}, 3));
        int totalQuestions = quizQuestions.size();
        int correctAnswers = 0;
        System.out.println("Welcome, " + user.getUsername() + "!");
        System.out.println("Quiz started! You have 10 seconds to complete each question.");
        for (int i = 0; i < totalQuestions; i++) {
            Question currentQuestion = quizQuestions.get(i);
            System.out.println(currentQuestion.getQuestionText());
            // Display answer options
            String[] options = currentQuestion.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }
            System.out.print("Enter your choice (1-" + options.length + "): ");
            // Set a timer for each question
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Time's up!");
                    displayCorrectAnswer(currentQuestion);
                    quizCompleted = true; // Mark the quiz as completed
                }
            }, 10000); // 10 seconds timer
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            timer.cancel();
            if (userChoice - 1 == currentQuestion.getCorrectOptionIndex()) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Incorrect!");
                displayCorrectAnswer(currentQuestion);
            }
        }
        displayTotalScore(correctAnswers, totalQuestions); // Display quiz results
    }
    private static void displayTotalScore(int correctAnswers, int totalQuestions) {
        if (!quizCompleted) { // Check if the quiz is not completed to avoid displaying the score multiple times
            System.out.println("Quiz Completed!");
            System.out.println("Your final score: " + correctAnswers + " out of " + totalQuestions);
        }
    }
    private static void displayCorrectAnswer(Question question) {
        int correctOptionIndex = question.getCorrectOptionIndex();
        System.out.println("Correct Answer: " + question.getOptions()[correctOptionIndex]);
    }}
