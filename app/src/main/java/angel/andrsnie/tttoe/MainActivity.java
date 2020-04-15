/*
 * Created by andrSnie on 5.04.20 5:53
 * Copyright (c) 2020. All rights reserved.
 */

package angel.andrsnie.tttoe;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;

    private GameLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.butto1);
        button2 = (Button) findViewById(R.id.butto2);
        button3 = (Button) findViewById(R.id.butto3);
        button4 = (Button) findViewById(R.id.butto4);
        button5 = (Button) findViewById(R.id.butto5);
        button6 = (Button) findViewById(R.id.butto6);
        button7 = (Button) findViewById(R.id.butto7);
        button8 = (Button) findViewById(R.id.butto8);
        button9 = (Button) findViewById(R.id.butto9);

        Button[] arrayOfButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9};

        TextView tv = (TextView) findViewById(R.id.vic);

        gameLogic = new GameLogic(this, arrayOfButtons, tv);

        if (getLastCustomNonConfigurationInstance() != null)
        {
            Button[] changedScreenButtons = (Button[]) getLastCustomNonConfigurationInstance();
            for (int i = 0, k = changedScreenButtons.length; i < k; i++) {
                if (changedScreenButtons[i].getBackground() instanceof BitmapDrawable) {
                    if (((BitmapDrawable) changedScreenButtons[i].getBackground()).getBitmap().sameAs(((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.o)).getBitmap()))
                    {
                        arrayOfButtons[i].setBackgroundResource(R.drawable.o);
                    }
                    else if (((BitmapDrawable) changedScreenButtons[i].getBackground()).getBitmap().sameAs(((BitmapDrawable) AppCompatResources.getDrawable(this, R.drawable.cross)).getBitmap()))
                    {
                        arrayOfButtons[i].setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.cross));
                    }

                    arrayOfButtons[i].setEnabled(changedScreenButtons[i].isEnabled());
                }
            }
        }
        else
        {
            Toast.makeText(MainActivity.this, R.string.hi, Toast.LENGTH_SHORT).show();
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 1;
                gameLogic.whatsGoesOnWhenClick(button1, numberOfCell);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 2;
                gameLogic.whatsGoesOnWhenClick(button2, numberOfCell);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 3;
                gameLogic.whatsGoesOnWhenClick(button3, numberOfCell);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 4;
                gameLogic.whatsGoesOnWhenClick(button4, numberOfCell);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 5;
                gameLogic.whatsGoesOnWhenClick(button5, numberOfCell);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 6;
                gameLogic.whatsGoesOnWhenClick(button6, numberOfCell);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 7;
                gameLogic.whatsGoesOnWhenClick(button7, numberOfCell);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 8;
                gameLogic.whatsGoesOnWhenClick(button8, numberOfCell);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfCell = 9;
                gameLogic.whatsGoesOnWhenClick(button9, numberOfCell);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("grandList", gameLogic.getGrandList());
        outState.putBoolean("startTheGame", gameLogic.isStartTheGame());
        outState.putBoolean("victoryOfX", gameLogic.isVictoryOfX());
        outState.putInt("valueOfCellO", gameLogic.getValueOfCellO());

        TextView tv = (TextView) findViewById(R.id.vic);
        String message = tv.getText().toString();
        outState.putString("message", message);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        gameLogic.setGrandList((ArrayList<ArrayList<Integer>>) savedInstanceState.getSerializable("grandList"));
        gameLogic.setStartTheGame(savedInstanceState.getBoolean("startTheGame"));
        gameLogic.setVictoryOfX(savedInstanceState.getBoolean("victoryOfX"));
        gameLogic.setValueOfCellO(savedInstanceState.getInt("valueOfCellO"));

        if (savedInstanceState.getString("message") != null) {
            TextView tv = (TextView) findViewById(R.id.vic);
            String message = savedInstanceState.getString("message");
            tv.setText(message);
            tv.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple));
            tv.getBackground().setAlpha(225);

            if (message != null) {
                if (message.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.lets_continue, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.twist, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public Button[] onRetainCustomNonConfigurationInstance() {
        super.onRetainCustomNonConfigurationInstance();

        Button[] changedScreenButtons = new Button[9];
        changedScreenButtons[0] = button1;
        changedScreenButtons[1] = button2;
        changedScreenButtons[2] = button3;
        changedScreenButtons[3] = button4;
        changedScreenButtons[4] = button5;
        changedScreenButtons[5] = button6;
        changedScreenButtons[6] = button7;
        changedScreenButtons[7] = button8;
        changedScreenButtons[8] = button9;

        return changedScreenButtons;
    }
}