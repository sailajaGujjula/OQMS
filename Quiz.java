package Models;
import java.util.List;
public interface Quiz {
    void addQuestion(Question question);
    void removeQuestion(Question question);
    void start();
    int getTotalScore();
	List<Question> getQuestions();
