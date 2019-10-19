package at.htlkaindorf.exa_107_quiz.bl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionPool {
    private Map<QuestionPool, List<QuizQuestion>> questions;

    public QuestionPool(Map<QuestionPool, List<QuizQuestion>> questions) {
        this.questions = questions;
    }

    public QuestionPool() {
    }

    public Map<QuestionPool, List<QuizQuestion>> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<QuestionPool, List<QuizQuestion>> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionPool that = (QuestionPool) o;
        return Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions);
    }
}
