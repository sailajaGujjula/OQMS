package MainClass;
import Models.User;
import Models.Question;
import Services.UserDatabase;
import Services.QuizImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class QuizManagement {
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
                        // Implement teacher login logic here (not shown in this example)
                        System.out.print("Enter teacher username: ");
                        String teacherUsername = scanner.nextLine();
                        System.out.print("Enter teacher password: ");
                        String teacherPassword = scanner.nextLine();

                        // Placeholder for teacher login logic

                        System.out.println("Teacher login successful! Welcome, " + teacherUsername + ".");
                        // Add teacher-specific functionality here
                        // For example, allow teachers to add quiz questions
                        break;

                    case 3:
                        System.out.println("Exiting the application.");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // Once a user is logged in, you can perform actions or start the quiz here.
                if (loggedInUser.getUsername().equals("teacher")) {
                    // Implement teacher-specific actions
                    // For example, add quiz questions
                    System.out.println("Teacher actions: Add quiz questions");
                } else {
                    // Start the quiz for students
                    startQuiz(loggedInUser);
                }
                loggedInUser = null; // Reset loggedInUser for future logins
            }
        }
    }

    static void startQuiz(User user) {
        Scanner scanner = new Scanner(System.in);

        // Define quiz questions (replace this with your actual questions)
        List<Question> quizQuestions = new ArrayList<>();
        quizQuestions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Rome"}, 0));
        quizQuestions.add(new Question("What is the largest mammal?", new String[]{"Elephant", "Blue Whale", "Lion", "Giraffe"}, 1));
        // Add more questions here

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
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (userChoice - 1 == currentQuestion.getCorrectOptionIndex()) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Incorrect!");
                System.out.println("Correct Answer: " + options[currentQuestion.getCorrectOptionIndex()]);
            }

            // You can add a timer to limit the time to answer each question (not shown in this example).
        }

        // Display quiz results
        System.out.println("Quiz Completed!");
        System.out.println("Your final score: " + correctAnswers + " out of " + totalQuestions);
    }
}
