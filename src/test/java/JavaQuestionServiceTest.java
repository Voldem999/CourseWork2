import org.example.exception.QuestionAlreadyExistException;
import org.example.exception.QuestionNotFoundException;
import org.example.model.Question;
import org.example.service.QuestionService;
import org.example.service.impl.JavaQuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void clear() {
        questionService.getAll().forEach(questionService::remove);
    }

    private void add(Question question) {
        int sizeBefore = questionService.getAll().size();
        questionService.add(question.getQuestion(), question.getAnswer());
        assertThat(questionService.getAll())
                .hasSize(sizeBefore+1)
                .contains(question);
    }

    @Test
    public void addTest1() {
        Question question1 = new Question("questionTest1", "answerTest1");
        add(question1);

        Question question2 = new Question("questionTest2", "answerTest2");
        questionService.add(question2);
        assertThat(questionService.getAll())
                .hasSize(2)
                .containsOnly(question1, question2);
    }

    @Test
    public void addTest2() {
        Question question = new Question("questionTest1", "answerTest1");
        add(question);
        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> questionService.add(question));
    }

    @Test
    public void removeTest1() {
        Question question = new Question("questionTest1", "answerTest1");
        add(question);
        questionService.remove(question);
        assertThat(questionService.getAll()).isEmpty();
    }
    @Test
    public void removeTest2() {
        Question question = new Question("questionTest1", "answerTest1");
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(question));

        add(question);
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("test1", "test2")));
    }

    @Test
    public void getRandomQuestionTest1() {
        for (int i = 0; i <= 5; i++) {
            add(new Question("question" + i, "answer" + i));
        }
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }

    @Test
    public void getRandomQuestionTest2() {
        assertThat(questionService.getAll()).isEmpty();
        assertThat(questionService.getRandomQuestion()).isNull();
    }

}
