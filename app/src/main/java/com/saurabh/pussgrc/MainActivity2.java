package com.saurabh.pussgrc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private TextView[] questionTextViews = new TextView[4];
    private RadioButton[] yesRadioButtons = new RadioButton[4];
    private RadioButton[] noRadioButtons = new RadioButton[4];
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int[] scores = new int[8];

    private Button backButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        QuestionBank questionBank = new QuestionBank();
        questions = questionBank.getList();

        questionTextViews[0] = findViewById(R.id.question_text_view1);
        questionTextViews[1] = findViewById(R.id.question_text_view2);
        questionTextViews[2] = findViewById(R.id.question_text_view3);
        questionTextViews[3] = findViewById(R.id.question_text_view4);

        yesRadioButtons[0] = findViewById(R.id.yes_radio_button1);
        yesRadioButtons[1] = findViewById(R.id.yes_radio_button2);
        yesRadioButtons[2] = findViewById(R.id.yes_radio_button3);
        yesRadioButtons[3] = findViewById(R.id.yes_radio_button4);

        noRadioButtons[0] = findViewById(R.id.no_radio_button1);
        noRadioButtons[1] = findViewById(R.id.no_radio_button2);
        noRadioButtons[2] = findViewById(R.id.no_radio_button3);
        noRadioButtons[3] = findViewById(R.id.no_radio_button4);

        backButton = findViewById(R.id.back_button);
        nextButton = findViewById(R.id.next_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestions();
                resetRadioButtons();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestions();
                resetRadioButtons();
            }
        });

        showCurrentQuestions();

        new AlertDialog.Builder(this)
                .setTitle("Rules")
                .setMessage("Here are the rules of the questionnaire: \n\n1. Each question must be answered honestly. \n\n2. Do not skip any question. \n\n3. Your scores will be calculated based on your answers. \n\n4. Enjoy the questionnaire!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with your test
                        showCurrentQuestions();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showCurrentQuestions() {
        backButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.size());
        for (int i = 0; i < 4; i++) {
            if (currentQuestionIndex + i < questions.size()) {
                questionTextViews[i].setText(questions.get(currentQuestionIndex + i).getQuestion());
            } else {
                // Quiz is over, show score
                questionTextViews[i].setVisibility(View.INVISIBLE);
                yesRadioButtons[i].setVisibility(View.GONE);
                noRadioButtons[i].setVisibility(View.GONE);
            }
        }
        if(currentQuestionIndex >= questions.size())
        {
            String mbtiProfile=getMBTIProfile(scores);
            new AlertDialog.Builder(this)
                    .setTitle("Rezultat")
                    .setMessage(String.format("Tipul tau comportamental este %s",mbtiProfile))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            Map<String, List<String>> academicFields = getAcademicFieldsForMBTIProfile(mbtiProfile);

            // Print the academic fields for the personality type
            List<String> fields = academicFields.get(mbtiProfile);
            questionTextViews[2].setVisibility(View.VISIBLE);
            String result = "Personality Type: " + mbtiProfile + " => " +
                    "Academic Fields: " + fields.get(0) + ", " + fields.get(1);
            questionTextViews[2].setText(result);
        }
    }

    private void showNextQuestions() {
        currentQuestionIndex += 4;
        if(currentQuestionIndex<=16)
        calculateScores();
        showCurrentQuestions();
    }

    private void showPreviousQuestions() {
        currentQuestionIndex -= 4;
        showCurrentQuestions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void calculateScores() {
        for (int i = currentQuestionIndex-4; i < currentQuestionIndex; i++) {
            for (int j = 0; j < 4; j++) {
                if(yesRadioButtons[j].isChecked())
                scores[questions.get(i).getScores()*2]+=1;
                else scores[ questions.get(i).getScores()*2+1]+=1;
            }
        }
    }
    private void resetRadioButtons() {
        for (int i = 0; i < 4; i++) {
            yesRadioButtons[i].setChecked(false);
            noRadioButtons[i].setChecked(false);
        }
    }
    public String getMBTIProfile(int[] scores) {
        // Define the MBTI profile categories
        String[] categories = {"E", "I", "S", "N", "T", "F", "J", "P"};

        // Initialize the MBTI profile as an empty string
        StringBuilder mbtiProfile = new StringBuilder();

        // Iterate over the scores array in pairs (opposite attributes)
        for (int i = 0; i < scores.length; i += 2) {
            // Determine the category (E/I/S/N/T/F/J/P) based on the maximum score between opposite attributes
            String category = scores[i] > scores[i + 1] ? categories[i] : categories[i + 1];
            // Append the category letter to the MBTI profile string
            mbtiProfile.append(category);
        }

        // Return the generated MBTI profile
        return mbtiProfile.toString();
    }
    public Map<String, List<String>> getAcademicFieldsForMBTIProfile(String mbtiProfile) {
        Map<String, List<String>> academicFieldsMap = new HashMap<>();

        // Define the academic fields for each personality type
        Map<String, List<String>> fieldsByProfile = new HashMap<>();
        fieldsByProfile.put("ISTJ", Arrays.asList("Accounting", "Computer Science"));
        fieldsByProfile.put("ISFJ", Arrays.asList("Nursing", "Education"));
        fieldsByProfile.put("INFJ", Arrays.asList("Psychology", "Social Work"));
        fieldsByProfile.put("INTJ", Arrays.asList("Engineering", "Research"));
        fieldsByProfile.put("ISTP", Arrays.asList("Mechanical Engineering", "Carpentry"));
        fieldsByProfile.put("ISFP", Arrays.asList("Fine Arts", "Interior Design"));
        fieldsByProfile.put("INFP", Arrays.asList("Writing", "Counseling"));
        fieldsByProfile.put("INTP", Arrays.asList("Physics", "Computer Programming"));
        fieldsByProfile.put("ESTP", Arrays.asList("Entrepreneurship", "Sports Management"));
        fieldsByProfile.put("ESFP", Arrays.asList("Performing Arts", "Hospitality Management"));
        fieldsByProfile.put("ENFP", Arrays.asList("Journalism", "Marketing"));
        fieldsByProfile.put("ENTP", Arrays.asList("Business", "Innovation and Design"));
        fieldsByProfile.put("ESTJ", Arrays.asList("Finance", "Management"));
        fieldsByProfile.put("ESFJ", Arrays.asList("Human Resources", "Event Planning"));
        fieldsByProfile.put("ENFJ", Arrays.asList("Counseling", "Education"));
        fieldsByProfile.put("ENTJ", Arrays.asList("Law", "Business Leadership"));

        // Get the academic fields for the given personality type
        List<String> academicFields = fieldsByProfile.get(mbtiProfile);

        // Add the academic fields to the map
        if (academicFields != null) {
            academicFieldsMap.put(mbtiProfile, academicFields);
        }

        return academicFieldsMap;
    }

}
