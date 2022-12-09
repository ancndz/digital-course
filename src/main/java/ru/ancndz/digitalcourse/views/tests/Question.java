package ru.ancndz.digitalcourse.views.tests;

import java.util.HashMap;
import java.util.Map;

public class Question {

    private final String question;

    private final Map<String, Boolean> answers = new HashMap<>();

    public Question(String question) {
        this.question = question;
    }

    public void addAnswer(String answer, Boolean isRight) {
        this.answers.put(answer, isRight);
    }

    public Boolean isAnswerRight(String answer) {
        return this.answers.getOrDefault(answer, Boolean.FALSE);
    }

    public String getQuestion() {
        return question;
    }

    public Map<String, Boolean> getAnswers() {
        return answers;
    }
}
