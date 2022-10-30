package org.example.service;

import org.example.model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
