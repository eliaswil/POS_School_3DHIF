package at.htlkaindorf.exa_107_quiz.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GivenAnswers {
    private int categoryIndex;
    private List<Integer> answers = new ArrayList<>();

    public GivenAnswers(int categoryIndex, List<Integer> answers) {
        this.categoryIndex = categoryIndex;
        this.answers = answers;
    }

    public GivenAnswers() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GivenAnswers that = (GivenAnswers) o;
        return categoryIndex == that.categoryIndex &&
                Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryIndex, answers);
    }
    public List<Integer> getAnswers() {
        return answers;
    }
    public void addAnswer(int answer){
        this.answers.add(answer);
    }
}
