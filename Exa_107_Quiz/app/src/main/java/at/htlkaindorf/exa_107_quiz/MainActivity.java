package at.htlkaindorf.exa_107_quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.htlkaindorf.exa_107_quiz.bl.Category;
import at.htlkaindorf.exa_107_quiz.bl.GivenAnswers;
import at.htlkaindorf.exa_107_quiz.bl.QuestionPool;
import at.htlkaindorf.exa_107_quiz.bl.QuizQuestion;

/**
 * Extra-Feature:
 * -------------
 *
 * just click the navigation buttons to review your answers
 *
 */
public class MainActivity extends AppCompatActivity {
    private QuestionPool questionPool;
    private TextView tvQuestion;
    private Button btContinue;
    private TextView tvCategory;
    private List<Button> btQuestions = new ArrayList<>();
    private List<Button> btAnswers = new ArrayList<>();
    private Map<Integer, Category> categoryMap = new HashMap<>();
    private int currentCategoryIndex = -1;
    Category currentCategory;
    private int currentQuestion = -1;
    private GivenAnswers givenAnswers;
    private boolean isCurrentQuesion = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise Category map
        int i = 0;
        for(Category category : Category.values()){
            categoryMap.put(i++, category);
        }
        currentCategory = categoryMap.get(currentCategoryIndex);

        tvQuestion = findViewById(R.id.tvQuestion);
        btContinue = findViewById(R.id.btContinue);
        btAnswers.add((Button)findViewById(R.id.btAnswer1));
        btAnswers.add((Button)findViewById(R.id.btAnswer2));
        btAnswers.add((Button)findViewById(R.id.btAnswer3));
        btAnswers.add((Button)findViewById(R.id.btAnswer4));
        tvCategory = findViewById(R.id.tvCategory);
        btQuestions.add((Button)findViewById(R.id.btQuestion1));
        btQuestions.add((Button)findViewById(R.id.btQuestion2));
        btQuestions.add((Button)findViewById(R.id.btQuestion3));
        btQuestions.add((Button)findViewById(R.id.btQuestion4));
        btQuestions.add((Button)findViewById(R.id.btQuestion5));

        // onClickListener for Answer Buttons
        MyOnClickListener mcl = new MyOnClickListener();
        for(Button btAnswer : btAnswers){
            btAnswer.setOnClickListener(mcl);
        }

        // onClick Listener for Continue-Button ("Weiter-Button")
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCurrentQuesion = true;
                onDisplayQuestion();
            }
        });

        // read Questions from File --> QuestionPool
        try {
            initQuestionPool();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // init first question
        changeCategory();
        onDisplayQuestion();

        Toast.makeText(getApplicationContext(), getString(R.string.info_message), Toast.LENGTH_LONG).show();
    }

    private void onDisplayQuestion(){
        boolean currQuestionIsAnswered = (currentQuestion == givenAnswers.getAnswers().size() - 1);   // if currQuestion is answered
        if (currQuestionIsAnswered) {
            currentQuestion++;
            isCurrentQuesion = true;
        }

        // change Category if finished with
        if(currentQuestion >= questionPool.getQuestions().get(currentCategory).size()){
            currentQuestion = 0;
            changeCategory();
        }

        // indicate current Question
        btQuestions.get(currentQuestion).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.currentQuestionGrey));

        btContinue.setEnabled(false);
        btQuestions.get(currentQuestion).setEnabled(true);

        int noQuestions = questionPool.getQuestions().get(currentCategory).size();
        int questionIndex = currentQuestion % noQuestions;

        displayQuestionAndAnswers(currentCategory, questionIndex, true);
    }

    private void displayQuestionAndAnswers(Category currentCategory, int questionIndex, boolean enable){
        // get, set the question
        String question = questionPool.getQuestions().get(currentCategory).get(questionIndex).getQuestion();
        tvQuestion.setText(question);

        // get, set answers
        for(int i = 0; i < btAnswers.size(); i++){
            String answer = questionPool.getQuestions().get(currentCategory).get(questionIndex).getAnswers().get(i);
            Button btAnswer = btAnswers.get(i);
            btAnswer.setText(answer);
            btAnswer.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.dark_grey));
            btAnswer.setEnabled(enable);
        }
    }

    public void onDisplayAnyQuestion(View view){
        Button clickedQuestion = (Button) view;
        for(int i = 0; i < btQuestions.size(); i++){

            // determine clicked question
            if(btQuestions.get(i).equals(clickedQuestion)){

                // check if the shown question is the current one
                isCurrentQuesion = (i == currentQuestion);

                // ... AND not answered yet
                if(isCurrentQuesion && givenAnswers.getAnswers().size() <= i){
                    currentQuestion--;
                    onDisplayQuestion();
                    return;
                }

                // get, set answers and question, but disable answer buttons
                displayQuestionAndAnswers(currentCategory, i, false);

                // color Answer buttons
                int correctIndex = questionPool.getQuestions().get(currentCategory).get(i).getCorrectAnswer();
                int answeredIndex = givenAnswers.getAnswers().get(i);
                if(answeredIndex == correctIndex){ // if answered correctly
                    btAnswers.get(correctIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
                }
                else {
                    btAnswers.get(answeredIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
                    btAnswers.get(correctIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green2));
                }
                btContinue.setEnabled(true);
            }
        }
    }

    private void changeCategory(){
        // set Category
        currentCategoryIndex = (++currentCategoryIndex) % categoryMap.size();
        String tvCategoryText = getString(R.string.tvCategoryText) + " ";
        tvCategoryText += categoryMap.get(currentCategoryIndex).toString();
        tvCategory.setText(tvCategoryText);
        currentCategory = categoryMap.get(currentCategoryIndex);
        isCurrentQuesion = true;

        // reset buttons (state)
        for(Button button : btQuestions){
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.light_grey));
            button.setEnabled(false);
        }

        // init Given Answers
        givenAnswers = new GivenAnswers(currentCategoryIndex, new ArrayList<Integer>());
    }

    private void onAnswerClick(View view){

        // don't allow allow guesses on already answered questions
        if(!isCurrentQuesion){
            return;
        }

        int clickedIndex = -1;
        Button clickedButton = (Button) view;

        // get index of clicked button, disable all answer-buttons
        for(int i = 0; i < btAnswers.size(); i++){
            if(clickedButton.equals(btAnswers.get(i))){
                clickedIndex = i;
            }
            btAnswers.get(i).setEnabled(false);
        }
        int correctIndex = questionPool.getQuestions().get(currentCategory).get(currentQuestion).getCorrectAnswer();

        // save the answer
        givenAnswers.addAnswer(clickedIndex);

        // change color of buttons according to the answer
        if(clickedIndex == correctIndex){   // answer == true
            btAnswers.get(clickedIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
            btQuestions.get(currentQuestion).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
        }
        else{   // answer == false
            btAnswers.get(clickedIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
            btAnswers.get(correctIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green2));
            btQuestions.get(currentQuestion).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
        }
        btContinue.setEnabled(true);
    }

    private void initQuestionPool() throws IOException {
        InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.questions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.readLine();
        String line;
        questionPool = new QuestionPool(new HashMap<Category, List<QuizQuestion>>());

        // read questions from file --> QuestionPool
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\\|");
            if(tokens.length < 4){
                continue;
            }
            Category category;
            try{
                category = Category.valueOf(tokens[0].trim());
            }catch (RuntimeException e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                continue;
            }

            String question = tokens[1].trim();
            String[] answers = tokens[2].trim().split(";");
            List<String> answerList = new ArrayList<>(Arrays.asList(answers));
            int correctAnswer = Integer.parseInt(tokens[3].trim());

            QuizQuestion quizQuestion = new QuizQuestion(question, answerList, correctAnswer);
            this.questionPool.addQuestion(category, quizQuestion);
        }
    }

    public class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            onAnswerClick(view);
        }
    }
}
