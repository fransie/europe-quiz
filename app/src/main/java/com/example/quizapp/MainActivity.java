package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view = findViewById(R.id.credit);
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * This method checks whether all questions in the quiz except for the checkbox question (since
     * it is possible that none of the answers in checkbox questions are true) have been
     * answered and sends a toast message to the user if at least one question is unanswered.
     *
     * @return boolean: true if all questions have been answered, else false.
     */
    public boolean areAllQuestionsAnswered() {
        RadioGroup radioGroupQuestion1 = findViewById(R.id.question1_radio_group);
        RadioGroup radioGroupQuestion4 = findViewById(R.id.question4_radio_group);
        EditText editTextAnswerQuestion3 = findViewById(R.id.question3_edit_text);
        Toast unansweredQuestionsToast = Toast.makeText(this, R.string.answerQuestions, Toast.LENGTH_SHORT);

        if (radioGroupQuestion1.getCheckedRadioButtonId() == -1) {
            unansweredQuestionsToast.show();
            return false;
        }
        if (radioGroupQuestion4.getCheckedRadioButtonId() == -1) {
            unansweredQuestionsToast.show();
            return false;
        }
        if (TextUtils.isEmpty(editTextAnswerQuestion3.getText())) {
            unansweredQuestionsToast.show();
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the user gave the right answer (Germany) for question 1 and
     * returns 1 if the answer is correct, else 0.
     *
     * @return int number of points achieved in question 1
     */
    public int evaluateFirstQuestion() {
        int points = 0;
        RadioButton radioButtonGermany = findViewById(R.id.question1_germany_correct);
        if (radioButtonGermany.isChecked()) {
            points++;
        }
        return points;
    }

    /**
     * This method evaluates the number of points achieved in question 2. It give +1 point if
     * correct answers (France, Ukraine) have been checked and +1 point if wrong answers
     * (Hungary, Latvia) have not been checked.
     *
     * @return int number of points achieved in question 2
     */
    public int evaluateSecondQuestion() {
        int points = 0;
        CheckBox checkBoxLatvia = findViewById(R.id.question2_latvia_wrong);
        CheckBox checkBoxFrance = findViewById(R.id.question2_france_correct);
        CheckBox checkBoxHungary = findViewById(R.id.question2_hungary_wrong);
        CheckBox checkBoxUkraine = findViewById(R.id.question2_ukraine_correct);

        if (!checkBoxLatvia.isChecked()) {
            points++;
        }
        if (!checkBoxHungary.isChecked()) {
            points++;
        }
        if (checkBoxFrance.isChecked()) {
            points++;
        }
        if (checkBoxUkraine.isChecked()) {
            points++;
        }
        return points;
    }

    /**
     * This method checks whether the user entered the correct answer (Madrid) into question 3
     * text field. The answer is not case sensitive and spaces are not considered. Examples of
     * correct Strings: "MADRID", "madRId" or "Madrid ". It returns one point if the answer is
     * correct, else zero points.
     *
     * @return int number of points from question 3
     */
    public int evaluateThirdQuestion() {
        int points = 0;
        EditText editTextCapitalOfSpain = findViewById(R.id.question3_edit_text);
        String answerCapitalOfSpain = editTextCapitalOfSpain.getText().toString();
        answerCapitalOfSpain = answerCapitalOfSpain.toLowerCase().replace(" ", "");
        if (answerCapitalOfSpain.equals("madrid")) {
            points++;
        }
        return points;
    }


    /**
     * This method checks whether the user gave the right answer (Latvia) for question 4 and
     * returns 1 if the answer is correct, else 0.
     *
     * @return int number of points achieved in question 4
     */
    public int evaluateFourthQuestion() {
        int points = 0;
        RadioButton radioButtonLatvia = findViewById(R.id.question4_latvia_correct);
        if (radioButtonLatvia.isChecked()) {
            points++;
        }
        return points;
    }

    /**
     * This method adds up all points from questions 1 to 4 and displays a toast to tell the user
     * their score. The toast message depends on whether the user got 0 or more points.
     */
    public void addUpAndDisplayScore(View view) {
        if (!areAllQuestionsAnswered()) {
            return;
        }
        int score = evaluateFirstQuestion();
        score += evaluateSecondQuestion();
        score += evaluateThirdQuestion();
        score += evaluateFourthQuestion();
        String scoreMessage;
        if (score == 0) {
            scoreMessage = getResources().getString(R.string.scoreMessageZeroPointsString);
        } else {
            scoreMessage = getResources().getString(R.string.scoreMessageString, score);
        }
        Toast scoreToast = Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG);
        scoreToast.show();
    }
}