package com.example.hangman;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageView;

import android.widget.GridView;

import java.util.Random;

public class GameActivity extends Activity  {


    private ImageView[] ar;

    private int num =6;

    private int curr;


    private int numCorr;

    private GridView letters;
    private LetterAdapter ltrAdapt;
    private String[] words;
    private Random rand;
    private String currWord;
    private LinearLayout wordLayout;
    private TextView[] charV;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Resources res = getResources();
        words = res.getStringArray(R.array.words);
        rand = new Random();
        currWord = "";
        wordLayout = findViewById(R.id.word);
        letters = findViewById(R.id.letters);
        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);
        ar = new ImageView[num];
        ar[0] = findViewById(R.id.v);
        ar[1] = findViewById(R.id.num2);
        ar[2] = findViewById(R.id.num3);
        ar[3] = findViewById(R.id.num4);
        ar[4] = findViewById(R.id.num5);
        ar[5] = findViewById(R.id.num6);
        playGame();


    }
    private void playGame() {

        curr =0;numCorr=0;
        for(int p = 0; p < num; p++) {
            ar[p].setVisibility(View.INVISIBLE);
        }
        String newWord = words[rand.nextInt(words.length)];
        while(newWord.equals(currWord)) newWord = words[rand.nextInt(words.length)];
        currWord = newWord;
        charV = new TextView[currWord.length()];
        wordLayout.removeAllViews();
        for (int c = 0; c < currWord.length(); c++) {
            charV[c] = new TextView(this);
            charV[c].setText(""+currWord.charAt(c));
        }
        for (int c = 0; c < currWord.length(); c++) {
            charV[c] = new TextView(this);
            charV[c].setText(""+currWord.charAt(c));
            charV[c].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            charV[c].setGravity(Gravity.CENTER);
            charV[c].setTextColor(Color.WHITE);
            charV[c].setBackgroundResource(R.drawable.letter_bg);
            wordLayout.addView(charV[c]);
        }
    }

    public void letterPressed(View view) {
        String ltr = ((Button) view).getText().toString();
        char letterChar = ltr.charAt(0);
        view.setEnabled(false);
        boolean correct = false;
        for(int k = 0; k < currWord.length(); k++) {
            if(currWord.charAt(k)==letterChar){
                correct = true;
                numCorr++;
                charV[k].setTextColor(Color.BLACK);
            }
        }
        if (correct) {
            if (numCorr == currWord.length()) {
                disableBtns();
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                winBuild.setTitle("Ура");
                winBuild.setMessage("Ты Выиграл!\n\n правильный ответ:\n\n"+currWord);
                winBuild.setNegativeButton("Выйти",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameActivity.this.finish();
                            }});
                winBuild.show();

            }
        }else if (curr < num) {
            ar[curr].setVisibility(View.VISIBLE);
            curr++;
        }else{
            disableBtns();
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("Упс");
            loseBuild.setMessage("Ты проиграл!\n\nправильный ответ:\n\n"+currWord);
            loseBuild.setNegativeButton("Выйти",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.finish();
                        }});
            loseBuild.show();
        }

    }
    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }



}