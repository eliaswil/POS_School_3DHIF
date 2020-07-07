package at.htlkaindorf.exa_107_quiz.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionPool {
    private Map<Category, List<QuizQuestion>> questions;


    public QuestionPool() {
    }

    public QuestionPool(Map<Category, List<QuizQuestion>> questions) {
        this.questions = questions;
    }

    public void addQuestion(Category category, QuizQuestion quizQuestion){
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        if(this.questions.keySet().contains(category)){
            quizQuestions = questions.get(category);
        }
        quizQuestions.add(quizQuestion);
        this.questions.put(category, quizQuestions);
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

    public Map<Category, List<QuizQuestion>> getQuestions() {
        return questions;
    }

}