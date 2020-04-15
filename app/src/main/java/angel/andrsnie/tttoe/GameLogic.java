/*
 * Created by andrSnie on 5.04.20 5:53
 * Copyright (c) 2020. All rights reserved.
 */

package angel.andrsnie.tttoe;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

class GameLogic{

    private Context mContext;
    private TextView mTextView;

    private int mValueOfCellO;

    private boolean mStartTheGame;
    private boolean mVictoryOfX;

    private ArrayList<Integer> mField1 = new ArrayList<>();
    private ArrayList<Integer> mField2 = new ArrayList<>();
    private ArrayList<Integer> mField3 = new ArrayList<>();
    private ArrayList<Integer> mField4 = new ArrayList<>();
    private ArrayList<Integer> mField5 = new ArrayList<>();
    private ArrayList<Integer> mField6 = new ArrayList<>();
    private ArrayList<Integer> mField7 = new ArrayList<>();
    private ArrayList<Integer> mField8 = new ArrayList<>();

    private int n = 8;
    private ArrayList<ArrayList<Integer>> mGrandList = new ArrayList<>(n);

    private Button[] mArrayOfButtons;

    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mPlayer) {
            releaseMediaPlayer();
        }
    };

    GameLogic(Context con, Button[] arr, TextView view)
    {
        mContext = con;
        inflateArrayList();
        mArrayOfButtons = arr;
        mTextView = view;
    }

    private void inflateArrayList()
    {
        mField1.add(1);
        mField1.add(2);
        mField1.add(3);
        mGrandList.add(mField1);

        mField2.add(4);
        mField2.add(5);
        mField2.add(6);
        mGrandList.add(mField2);

        mField3.add(7);
        mField3.add(8);
        mField3.add(9);
        mGrandList.add(mField3);

        mField4.add(1);
        mField4.add(4);
        mField4.add(7);
        mGrandList.add(mField4);

        mField5.add(2);
        mField5.add(5);
        mField5.add(8);
        mGrandList.add(mField5);

        mField6.add(3);
        mField6.add(6);
        mField6.add(9);
        mGrandList.add(mField6);

        mField7.add(1);
        mField7.add(5);
        mField7.add(9);
        mGrandList.add(mField7);

        mField8.add(3);
        mField8.add(5);
        mField8.add(7);
        mGrandList.add(mField8);
    }

    ArrayList<ArrayList<Integer>> getGrandList() {
        return mGrandList;
    }

    boolean isStartTheGame() {
        return mStartTheGame;
    }

    boolean isVictoryOfX() {
        return  mVictoryOfX;
    }

    int getValueOfCellO() {
        return mValueOfCellO;
    }

    void setGrandList(ArrayList<ArrayList<Integer>> grandList) {
        this.mGrandList = grandList;
    }

    void setStartTheGame(boolean startTheGame) {
        this.mStartTheGame = startTheGame;
    }

    void setVictoryOfX(boolean victoryOfX) {
        this.mVictoryOfX = victoryOfX;
    }

    void setValueOfCellO(int valueOfCellO) {
        this.mValueOfCellO = valueOfCellO;
    }

    void whatsGoesOnWhenClick(Button btn, int valueOfCell) {
        if (!mStartTheGame) {
            mStartTheGame = true;
            removeClickedElementX(btn, valueOfCell);
            int indexOfButton = Arrays.asList(mArrayOfButtons).indexOf(btn);
            int newIndexOfButton = randomIndexOfButton(indexOfButton);
            valueOfCell = newIndexOfButton + 1;
            handlingCellO(valueOfCell);
        } else {
            if (mValueOfCellO == valueOfCell)   // protection of the game logic against pressing several buttons at the same time
            {
                showResult(R.string.warning);
                btn.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.cross));
                allButtonsDisabled();
                releaseMediaPlayer();
            }
            else {
                removeClickedElementX(btn, valueOfCell);
                searchButtonO();
            }
        }
    }

    private int randomIndexOfButton(int index) {
        int indexList = (int) (Math.random() * 8);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            list.add(i);

        list.remove(index);

        return list.get(indexList);
    }

    //    Linear Search
    private void removeClickedElementX(Button bn, int value) {
        if (mVictoryOfX) {
            showResult(R.string.calm_down);
        }
        for (int i = 0, n = mGrandList.size(); i < n; i++) {
            for (int j = 0, k = mGrandList.get(i).size(); j < k; j++) {
                if (mGrandList.get(i).get(j) == value) {
                    bn.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.cross));
                    //bn.setBackgroundResource(R.drawable.cross);
                    bn.setEnabled(false);
                    mGrandList.get(i).remove(j);
                    break;
                }
            }
        }
    }

    private void markElementO(int value) {
        for (int i = 0, n = mGrandList.size(); i < n; i++) {
//            Binary Search taking into account the logic of this application :-)
            int lower = 0;
            int upper = mGrandList.get(i).size() - 1;
            while (lower <= upper) {
                int divider = (lower + upper) / 2;

                if (mGrandList.get(i).get(divider) == value) {
                    mGrandList.get(i).set(divider, 11);
                    break;
                } else if (mGrandList.get(i).get(divider) < value) {
                    lower = divider + 1;
                } else if (mGrandList.get(i).get(divider) == 11) {
                    if (mGrandList.get(i).get(mGrandList.get(i).size() - 1) != 11) {
                        if (mGrandList.get(i).get(divider + 1) == value) {
                            mGrandList.get(i).set(divider + 1, 11);
                            break;
                        } else {
                            upper = divider - 1;
                        }
                    } else {
                        upper = divider - 1;
                    }
                } else if (mGrandList.get(i).get(divider) > value) {
                    upper = divider - 1;
                }
            }
        }
    }

    private void searchButtonO() {
        boolean flagOfVictoryOfO = false;

        for (int v = 0, f = mGrandList.size(); v < f; v++) {
            int size = mGrandList.get(v).size();

            if (!mVictoryOfX) {
                if (size == 0) {
                    flagOfVictoryOfO = true;

                    String message = mTextView.getText().toString();

                    // protection of the game logic against pressing several buttons at the same time
                    if (!message.contains("You are a greedy hamster! According to the rules of the game, you can not press several cells at once. Now you have to start all over again :-(\nWhat are you waiting for? Reload the app!"))
                        showResult(R.string.good_shot);
                } else if (size == 3 && !flagOfVictoryOfO) {
                    int counterForMarkedO = 0;
                    int valueForWinnerO = 10;

                    for (int j = 0; j < size; j++) {
                        if (mGrandList.get(v).get(j) == 11) {
                            counterForMarkedO++;
                        } else {
                            valueForWinnerO = mGrandList.get(v).get(j);
                        }
                    }

                    if (counterForMarkedO == 2) {
                        handlingCellO(valueForWinnerO);
                        showResult(R.string.ooo_winner);
                        flagOfVictoryOfO = true;
                        screamOfVictory();
//                        allButtonsDisabled(); // Thus (on this) you can finish the game
                    } else if (counterForMarkedO == 3) {
                        flagOfVictoryOfO = true;

                        String message = mTextView.getText().toString();
                        // protection of the game logic against pressing several buttons at the same time
                        if (!message.contains("You are a greedy hamster! According to the rules of the game, you can not press several cells at once. Now you have to start all over again :-(\nWhat are you waiting for? Reload the app!"))
                            showResult(R.string.woodpecker);
                    }
                }
            }
        }

        if (!flagOfVictoryOfO) {
            int valueOfButtonIfSizeOne = 10;
            int valueOfButtonIfSizeNOtOne = 10;
            int valueOfButtonIfDoubletX = 10;
            boolean doubletCounterForX = false;

            for (int i = 0, n = mGrandList.size(); i < n; i++) {
                int listSize = mGrandList.get(i).size();

                if (listSize == 1) {
                    if (mGrandList.get(i).get(0) != 11) {
                        if (!doubletCounterForX) {
                            valueOfButtonIfSizeOne = mGrandList.get(i).get(0);
                        } else {
                            valueOfButtonIfDoubletX = mGrandList.get(i).get(0);
                        }
                        doubletCounterForX = true;
                    }
                } else {
                    for (int j = 0; j < listSize; j++) {
                        if (mGrandList.get(i).contains(11)) {
                            if (mGrandList.get(i).get(j) != 11) {
                                valueOfButtonIfSizeNOtOne = mGrandList.get(i).get(j);
                            }
                        }
                    }
                }
            }

            if (valueOfButtonIfSizeOne != 10) {
                if (valueOfButtonIfDoubletX != 10) {
                    removeClickedElementX(mArrayOfButtons[valueOfButtonIfDoubletX - 1], valueOfButtonIfDoubletX);
                    mVictoryOfX = true;
                    showResult(R.string.unbelievably);
                    screamOfVictory();
//                    allButtonsDisabled(); // Thus (on this) you can finish the game
                }
                handlingCellO(valueOfButtonIfSizeOne);
                checkDoubletO();
            } else if (valueOfButtonIfSizeNOtOne != 10) {
                handlingCellO(valueOfButtonIfSizeNOtOne);
                checkDoubletO();
            } else {
                if (!mVictoryOfX) {
                    showResult(R.string.draw);
                }
            }
        }
    }

    private void showResult(int resId) {
        mTextView.setText(resId);
        mTextView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_purple));
        mTextView.getBackground().setAlpha(225);
    }

    private void checkDoubletO() {
        boolean doubletCounterForO = false;
        for (int i = 0, n = mGrandList.size(); i < n; i++) {
            int size = mGrandList.get(i).size();

            if (size == 3) {
                int counterForMarkedO = 0;

                for (int j = 0; j < size; j++) {
                    if (mGrandList.get(i).get(j) == 11) {
                        counterForMarkedO++;
                    }
                }

                if (counterForMarkedO == 2) {
                    if (!doubletCounterForO) {
                        doubletCounterForO = true;
                    } else {
                        Toast.makeText(mContext, R.string.wow, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void setBack(int valueOfButton) {
        valueOfButton -= 1;
        mArrayOfButtons[valueOfButton].setBackground(AppCompatResources.getDrawable(mContext, R.drawable.o));
        //mArrayOfButtons[valueOfButton].setBackgroundResource(R.drawable.o);
        mArrayOfButtons[valueOfButton].setEnabled(false);
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void screamOfVictory() {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.victory);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mListener);
    }

    private void handlingCellO(int valueOfButton) {
        setBack(valueOfButton);
        markElementO(valueOfButton);
        mValueOfCellO = valueOfButton;
    }

    private void allButtonsDisabled() {
        for (Button arrayOfButton : mArrayOfButtons) {
            arrayOfButton.setEnabled(false);
        }
    }
}