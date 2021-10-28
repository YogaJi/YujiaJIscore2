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
//    private Spinner spinner;
//    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private static int perAmount = 0;

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
        Spinner spinner = findViewById(R.id.gameSpin);
        SeekBar seekbar = findViewById(R.id.seekbar);

        Button desButton = findViewById(R.id.desButton);
        Button insButton = findViewById(R.id.insButton);

        teamABut.setOnClickListener(this);
        teamBBut.setOnClickListener(this);

        desButton.setOnClickListener(this);
        insButton.setOnClickListener(this);

        //add spinner list
        List<String> list = new ArrayList<>();
        list.add("American Football");
        list.add("Basketball");
        list.add("Freestyle Wrestling");
        list.add("Cricket");
        //2. set Listener
        chooseTeam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                perAmount = 0;
                updateScreen();
            }
        });
        //add Adapter for spinner
        ArrayAdapter arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        //set spinner Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You choose " + list.get(position), Toast.LENGTH_SHORT).show();
                gameName.setText(list.get(position));
                updateScreen();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        //seekbar Listener
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    perAmount = progress;
                    updateScreen();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }//end of onCreate

    //3.add onClick function
    @Override
    public void onClick(View view) {
        //set buttons to ins and des per score and link update() function
        switch(view.getId()){
            case R.id.desButton:
                perAmount -= 1;
                if(perAmount <= 0){
                    perAmount= 0;
                }
                break;
            case R.id.insButton:
                perAmount += 1;
                if(perAmount >= 20){
                    perAmount= 20;
                }
                break;

            default:
                Log.e("click event","wrong button");

        }
        //set seekbar progress to link the per score
        SeekBar seekbar = findViewById(R.id.seekbar);
        seekbar.setProgress(perAmount,true);

        updateScreen();

    }//end of onClick

    @Override
    public void onCheckedChanged(RadioGroup radioGroup,int checkedId){
        if(radioGroup.getId() == R.id.chooseTeam){
            updateScreen();
        }
    }
    //4.set updateScreen to update text, per score, score & update view and buttons
    public void updateScreen(){
        //get Id
        Button teamABut = findViewById(R.id.teamAbut);
        Button teamBBut = findViewById(R.id.teamBbut);
        TextView scoreATextView = findViewById(R.id.scoreAText);
        TextView scoreBTextView = findViewById(R.id.scoreBText);

        RadioGroup chooseTeam = findViewById(R.id.chooseTeam);
        int sumA = 0;
        int sumB = 0;
        //set team buttons and calculate scores
        char team = 'A';
        switch (chooseTeam.getCheckedRadioButtonId()){
            case R.id.chooseA:
                team ='A';
                teamBBut.setEnabled(false);
                teamABut.setEnabled(true);
                sumA += perAmount;
                scoreATextView.setText(String.valueOf(sumA));
                break;

            case R.id.chooseB:
                team ='B';
                teamABut.setEnabled(false);
                teamBBut.setEnabled(true);
                sumB += perAmount;
                scoreBTextView.setText(String.valueOf(sumB));
                break;

            default://else;
                teamBBut.setEnabled(true);
                teamABut.setEnabled(true);
        }

        Button desButton = findViewById(R.id.desButton);
        Button insButton = findViewById(R.id.insButton);

        //set max and min button value
        desButton.setEnabled(perAmount != 0);

        insButton.setEnabled(perAmount != 20);

        //set per score text view
        TextView scoreAmountTextView = findViewById(R.id.scoreChangeText);
        scoreAmountTextView.setText(String.valueOf(perAmount));

    }//end of update Screen



}//end of main

