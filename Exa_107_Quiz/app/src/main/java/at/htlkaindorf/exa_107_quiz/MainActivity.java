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
import at.htlkaindorf.exa_107_quiz.bl.QuestionPool;
import at.htlkaindorf.exa_107_quiz.bl.QuizQuestion;

public class MainActivity extends AppCompatActivity {
    private QuestionPool questionPool;
    private TextView tvQuestion;
    private Button btContinue;
    private TextView tvCategory;
    private List<Button> btQuestions = new ArrayList<>();
    private List<Button> btAnwers = new ArrayList<>();
    private Map<Integer, Category> categoryMap = new HashMap<>();
    private int currentCategoryIndex = -1;
    Category currentCategory;
    private int currentQuestion = -1;

    // ToDo: find some good questions
    // ToDo: wenn text zu lang --> layout funktioniert nicht !!!!!!!!!!!! layout_span ????


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
        btAnwers.add((Button)findViewById(R.id.btAnswer1));
        btAnwers.add((Button)findViewById(R.id.btAnswer2));
        btAnwers.add((Button)findViewById(R.id.btAnswer3));
        btAnwers.add((Button)findViewById(R.id.btAnswer4));
        tvCategory = findViewById(R.id.tvCategory);
        btQuestions.add((Button)findViewById(R.id.btQuestion1));
        btQuestions.add((Button)findViewById(R.id.btQuestion2));
        btQuestions.add((Button)findViewById(R.id.btQuestion3));
        btQuestions.add((Button)findViewById(R.id.btQuestion4));
        btQuestions.add((Button)findViewById(R.id.btQuestion5));


        // onClickListener for Answer Buttons
        MyOnClickListener mcl = new MyOnClickListener();
        for(Button btAnswer : btAnwers){
            btAnswer.setOnClickListener(mcl);
        }

        // onClick Listener for Continue-Button ("Weiter-Button")
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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



    }

    private void onDisplayQuestion(){
        currentQuestion++;
        btContinue.setEnabled(false);

        // change Category if finished with
        if(currentQuestion >= questionPool.getQuestions().get(currentCategory).size()){
            currentQuestion = 0;
            changeCategory();
        }
        // get, set the question
        int noQuestions = questionPool.getQuestions().get(currentCategory).size();
        int questionIndex = currentQuestion % noQuestions;
        String question = questionPool.getQuestions().get(currentCategory).get(questionIndex).getQuestion();
        tvQuestion.setText(question);

        // get, set answers
        for(int i=0; i < btAnwers.size(); i++){
            String answer = questionPool.getQuestions().get(currentCategory).get(questionIndex).getAnswers().get(i);
            Button btAnswer = btAnwers.get(i);
            btAnswer.setText(answer);
            btAnswer.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.dark_grey));
            btAnswer.setEnabled(true);

        }

    }

    private void changeCategory(){
        // set Category
        currentCategoryIndex = (++currentCategoryIndex) % categoryMap.size();
        String tvCategoryText = getString(R.string.tvCategoryText) + " ";
        tvCategoryText += categoryMap.get(currentCategoryIndex).toString();
        tvCategory.setText(tvCategoryText);
        currentCategory = categoryMap.get(currentCategoryIndex);

        // reset buttons (state)
        for(Button button : btQuestions){
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));
        }
    }

    private void onAnswerClick(View view){
        int clickedIndex = -1;
        Button clickedButton = (Button) view;

        // get index of clicked button, disable all answer-buttons
        for(int i = 0; i < btAnwers.size(); i++){
            if(clickedButton.equals(btAnwers.get(i))){
                clickedIndex = i;
            }
            btAnwers.get(i).setEnabled(false);
        }
        int correctIndex = questionPool.getQuestions().get(currentCategory).get(currentQuestion).getCorrectAnswer();

        // change color of buttons according to the answer
        if(clickedIndex == correctIndex){   // true
            btAnwers.get(clickedIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
            btQuestions.get(currentQuestion).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
        }
        else{   // false
            btAnwers.get(clickedIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
            btAnwers.get(correctIndex).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green2));
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

            System.out.println("finished with loading questions"); // ToDo: pls remove

        }
    }

    public class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            onAnswerClick(view);
        }
    }
}
