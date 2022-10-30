package org.example.service.impl;

import org.example.exception.QuestionAlreadyExistException;
import org.example.exception.QuestionNotFoundException;
import org.example.model.Question;
import org.example.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionAlreadyExistException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        return questions.stream().skip(random.nextInt(questions.size())).findFirst().orElse(null);
    }
}
