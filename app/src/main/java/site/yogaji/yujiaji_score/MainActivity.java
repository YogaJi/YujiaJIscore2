package site.yogaji.yujiaji_score;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private static int perAmount = 0;
    private int totalScore;
    private int count = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.get id
        Button teamABut = findViewById(R.id.teamAbut);
        Button teamBBut = findViewById(R.id.teamBbut);
        RadioGroup chooseTeam = findViewById(R.id.chooseTeam);

        TextView gameName = findViewById(R.id.gameName);
        Spinner gameSpinner = findViewById(R.id.gameSpin);
        Spinner  scoreAmountSpinner = findViewById(R.id.amountSpin);

        Button desButton = findViewById(R.id.desButton);
        Button insButton = findViewById(R.id.insButton);

        teamABut.setOnClickListener(this);
        teamBBut.setOnClickListener(this);

        desButton.setOnClickListener(this);
        insButton.setOnClickListener(this);

        //2. set Listener
        chooseTeam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                totalScore = 0;
                updateScreen();
            }
        });

        //add game list for game spinner
        List<String> games = new ArrayList<>();
        games.add("American Football");
        games.add("Basketball");
        games.add("Freestyle Wrestling");
        games.add("Cricket");
        //add Adapter for gameSpinner
        ArrayAdapter arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, games);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameSpinner.setAdapter(arrAdapter);
        //set spinner Listener
        gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You choose " + games.get(position), Toast.LENGTH_SHORT).show();
                gameName.setText(games.get(position));
                updateScreen();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        //add scoreAmount list for score spinner
        List<String> scoreAmountList = new ArrayList<>();
        scoreAmountList.add("1");
        scoreAmountList.add("2");
        scoreAmountList.add("4");
        scoreAmountList.add("6");

        //add Adapter for scoreAmountSpinner
        ArrayAdapter scoreAmountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, scoreAmountList);
        scoreAmountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scoreAmountSpinner.setAdapter(scoreAmountAdapter);
        //set spinner Listener
        scoreAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                perAmount =Integer.parseInt(scoreAmountList.get(position));
                totalScore = perAmount;
                //clear button counts
                count = 0;
                updateScreen();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }//end of onCreate

    //3.add onClick function
    @Override
    public void onClick(View view) {

        //set buttons to ins and des per score by the click (count) and link update() function
        switch(view.getId()){
            case R.id.desButton:
                if(count == 0){
                    totalScore = perAmount;
                }else{
                    count--;
                    totalScore = count * perAmount;
                }

                break;
            case R.id.insButton:
                if(totalScore >= 20){
                    totalScore = 20;
                }else{
                    count++;
                    totalScore = count * perAmount;
                }

                break;

            default:
                Log.e("click event","wrong button");

        }

        updateScreen();

    }//end of onClick

    //clear total score when changing team
    @Override
    public void onCheckedChanged(RadioGroup radioGroup,int checkedId){
        if(radioGroup.getId() == R.id.chooseTeam){
            totalScore = 0;
            count = 0;
            updateScreen();
        }
    }
    //4.set updateScreen to update text, score amount , total score & update view and buttons
    public void updateScreen(){
        //get Id
        Button teamABut = findViewById(R.id.teamAbut);
        Button teamBBut = findViewById(R.id.teamBbut);
        TextView scoreATextView = findViewById(R.id.scoreAText);
        TextView scoreBTextView = findViewById(R.id.scoreBText);

        Button desButton = findViewById(R.id.desButton);
        Button insButton = findViewById(R.id.insButton);

        RadioGroup chooseTeam = findViewById(R.id.chooseTeam);

        //set team buttons and update total score on the team's score text view
        char team = 'A';
        switch (chooseTeam.getCheckedRadioButtonId()){
            case R.id.chooseA:
                team ='A';
                teamBBut.setEnabled(false);
                teamABut.setEnabled(true);
                scoreATextView.setText(String.valueOf(totalScore));
                break;

            case R.id.chooseB:
                team ='B';
                teamABut.setEnabled(false);
                teamBBut.setEnabled(true);
                scoreBTextView.setText(String.valueOf(totalScore));
                break;

            default://else;
                teamBBut.setEnabled(true);
                teamABut.setEnabled(true);
        }


        //set max and min button value [0,20]

        if(totalScore == 0){
            desButton.setEnabled(false);
        }
        else{
           desButton.setEnabled(true);
        }

        if(totalScore >= 20){
            insButton.setEnabled(false);
        }
        else{
            insButton.setEnabled(true);
        }

        //set total score text view
        TextView scoreAmountTextView = findViewById(R.id.scoreChangeText);
        scoreAmountTextView.setText(String.valueOf(totalScore));

    }//end of update Screen


}//end of main

