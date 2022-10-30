import org.example.exception.IncorrectAmountOfQuestion;
import org.example.model.Question;
import org.example.service.QuestionService;
import org.example.service.impl.ExaminerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private static final List<Question> QUESTIONS = List.of(
            new Question("questionTest1", "answerTest1"),
            new Question("questionTest2", "answerTest2"),
            new Question("questionTest3", "answerTest3"),
            new Question("questionTest4", "answerTest4"),
            new Question("questionTest5", "answerTest5"));

//    @BeforeEach
//    public void beforeEach() {
//        when(questionService.getAll()).thenReturn(QUESTIONS);
//    }

    @ParameterizedTest
    @MethodSource("getQuestionsTest1Params")
    public void getQuestionsTest1(int incorrectAmount) {
        assertThatExceptionOfType(IncorrectAmountOfQuestion.class)
                .isThrownBy(() -> examinerService.getQuestions(incorrectAmount));
    }

    @Test
    public void getQuestionsTest2() {
        when(questionService.getAll()).thenReturn(QUESTIONS);
        when(questionService.getRandomQuestion()).thenReturn(
                QUESTIONS.get(1),
                QUESTIONS.get(2),
                QUESTIONS.get(3),
                QUESTIONS.get(1),
                QUESTIONS.get(2)
        );
        assertThat(examinerService.getQuestions(3)).containsExactly(
                QUESTIONS.get(1),
                QUESTIONS.get(2),
                QUESTIONS.get(3)
        );
    }

    public static Stream<Arguments> getQuestionsTest1Params() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(QUESTIONS.size() + 1)
        );
    }

}
