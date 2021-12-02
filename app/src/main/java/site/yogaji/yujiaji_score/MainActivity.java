package site.yogaji.yujiaji_score;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
    implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private static int perAmount = 0;
    private int totalScore;
    private int count = 0;
    //set sharedPreference:
    private SharedPreferences prefs;

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

        //set up preference manager to xml preference
        PreferenceManager.setDefaultValues(this,R.xml.preference,false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //2. set Listener
        chooseTeam.setOnCheckedChangeListener((group, checkedId) -> {
            totalScore = 0;
            updateScreen();
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

    //make menu:
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(
                        getApplicationContext(),
                        SettingActivity.class
                ));
                break;
            case R.id.menu_about:
                Toast.makeText(this, "Yujia Ji JAV-1001 - 91349", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //3.add onClick function
    @SuppressLint("NonConstantResourceId")
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
    @SuppressLint("NonConstantResourceId")
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

        desButton.setEnabled(totalScore != 0);

        insButton.setEnabled(totalScore < 20);

        //set total score text view
        TextView scoreAmountTextView = findViewById(R.id.scoreChangeText);
        scoreAmountTextView.setText(String.valueOf(totalScore));

    }//end of update Screen

    //save for data storage
    @Override
    protected void onResume(){
        super.onResume();
        //set ,save data and update
        String scoreATest = prefs.getString("scoreA_pref","0");
        String scoreBTest = prefs.getString("scoreB_pref","0");
        String gameName = prefs.getString("gameName_pref","");
        String scoreChangeTest = prefs.getString("scoreChangeTest_pref","0");

        ((TextView)findViewById(R.id.scoreAText)).setText(String.valueOf(totalScore));
        ((TextView)findViewById(R.id.scoreBText)).setText(String.valueOf(totalScore));
//        ((Spinner)findViewById(R.id.gameSpin)).setOnItemSelectedListener();
        RadioGroup chooseTeam = findViewById(R.id.chooseTeam);
        char team = 'A';
        //else;
        if (team == (char) R.id.chooseB) {
            team = 'B';
            chooseTeam.check(R.id.chooseB);
        } else {
            chooseTeam.check(R.id.chooseA);
        }

        updateScreen();
    }
    //life cycle
    @Override
    protected void onPause(){
//        holdData();
        super.onPause();
    }
    protected void onStop(){
//        holdData();
        super.onStop();
    }
    protected void onDestroy(){
        saveData();
        super.onDestroy();
    }
    //save data
    private void saveData(){
        SharedPreferences.Editor editor = prefs.edit();
        boolean saveValues = prefs.getBoolean("save_values_pref",false);
        Log.i("PREFS",Boolean.toString(saveValues));
        if(saveValues){
//         holdData();
            //collect any change
            TextView scoreAText = findViewById(R.id.scoreAText);
            TextView scoreBText = findViewById(R.id.scoreBText);
            TextView gameName = findViewById(R.id.gameName);
            TextView scoreChangeTest = findViewById(R.id.scoreChangeText);

            RadioGroup chooseTeam = findViewById(R.id.chooseTeam);
            int checkID = chooseTeam.getCheckedRadioButtonId();
            int team = 'A';

            if(checkID == R.id.chooseA)
                team = 'A';
            else if(checkID == R.id.chooseB){
               team = 'B';
            }
            //we can add KEYS and VALUES to the SharedPreferences
            editor.putString("scoreA_pref",scoreAText.getText().toString());
            editor.putString("scoreB_pref",scoreBText.getText().toString());
            editor.putString("gameName_pref",gameName.getText().toString());
            editor.putString("scoreChangeTest_pref",scoreChangeTest.getText().toString());
//            editor.putString("scoreChangeTest_pref",scoreChangeTest.getText().toString());
//            editor.putInt("tip_percent_pref",tipPercent);
        }else{
            editor.clear();
            editor.putBoolean("save_values_pref",false);
        }
        //editor.commit();
        editor.apply();
    }
//    private void holdData(){
//        SharedPreferences.Editor editor = prefs.edit();
//
//        EditText billAmountET = findViewById(R.id.billAmount);
//        editor.putString("bill_amount_pref", billAmountET.getText().toString());
//        //different from save data, there is no else to clean data
//
//        //editor.commit();
//        editor.apply();
//    }

}//end of main

