package com.generally2.magicslidepuzzle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Random;

public class PuzzleOne extends AppCompatActivity {

    ;

    private static final int COLUMNS = 3;
    private static final int DIMENTIONS = COLUMNS * COLUMNS;

    private static String[] tileList;

    private static GestureDetectGridView mGridView;

    private static int mColumWidth, mColumHight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    private static SoundPlayer sound;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_one);

        sound = new SoundPlayer(this);


        init();

        scramble();

        setDimentions();


    }

    private void setDimentions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHight = mGridView.getMeasuredHeight();

                int statusBarHigth = getStatusBarHigth(getApplicationContext());
                int requiredHigth = displayHight - statusBarHigth;

                mColumWidth = displayWidth/COLUMNS;
                mColumHight = requiredHigth/COLUMNS;

                display(getApplicationContext());


            }
        });
    }

    private int getStatusBarHigth(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("sataus_bar_hight", "dimon", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;

    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++){
            button = new Button(context);

            if (tileList[i].equals("0"))
                button.setBackgroundResource(R.drawable.mush_house_001);
            else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.mush_house_002);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.mush_house_003);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.mush_house_004);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.mush_house_005);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.mush_house_006);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.mush_house_007);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.mush_house_008);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.mush_house_009);

            buttons.add(button);

        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumWidth, mColumHight));

    }

    //scrambles the pieces
    private void scramble() {
        int index;
        String temp;
        Random random = new Random();
        for (int i = tileList.length -1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;


        }

    }

    //initiates the array
    private void init() {
        mGridView= (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENTIONS];
        for (int i = 0; i < DIMENTIONS; i++ ){
            tileList[i] = String.valueOf(i);

        }

    }

    private static void swap(Context context, int position, int swap){
        String newPosition = tileList[position + swap];
        tileList[position + swap] = tileList[position];
        tileList[position] = newPosition;
        display(context);

        if (isSolved()){
            sound.playWinSound();
            Toast.makeText(context, "WINNER!", Toast.LENGTH_SHORT).show();

        }



    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++){
            if (tileList[i].equals(String.valueOf(i))){
                solved = true;
            }
            else {
                solved = false;
                break;
            }
        }
        return solved;

    }

    public static void moveTiles(Context context, String direction, int position) {
        //upper left corner tile
        if (position == 0) {
            if (direction.equals(RIGHT)) swap(context, position, 1);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();


            //upper center tile
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(LEFT)) swap(context, position, -1);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else if (direction.equals(RIGHT)) swap(context, position, 1);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();


            //upper right tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(LEFT)) swap(context, position, -1);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();


            //middle left tile
        } else if (position == COLUMNS){
            if (direction.equals(UP)) swap(context, position, -COLUMNS);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else if (direction.equals(RIGHT)) swap(context, position, 1);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();


            // middle right tile
        } else if (position == COLUMNS + 2) {

            if (direction.equals(LEFT)) swap(context, position, -1);

            else if (direction.equals(UP)) swap(context, position, -COLUMNS);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();

        }
        //bottom left tile
        else if (position == DIMENTIONS - COLUMNS) {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else if (direction.equals(RIGHT)) swap(context, position, 1);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();


        }
        //bottom center tile
        else if (position < DIMENTIONS - 1 && position > DIMENTIONS - COLUMNS) {
            if (direction.equals(LEFT)) swap(context, position, - 1);

            else if (direction.equals(UP)) swap(context, position, - COLUMNS);

            else if (direction.equals(RIGHT)) swap(context, position, 1);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();

        }
        //bottom right
        else if (position == DIMENTIONS - 1) {
            if (direction.equals(LEFT)) swap(context, position, -1);

            else if (direction.equals(UP)) swap(context, position, -COLUMNS);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();
        }
        //middle center tile
        else {
            if (direction.equals(UP)) swap(context, position, - COLUMNS);

            else if (direction.equals(LEFT)) swap(context, position, - 1);

            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);

            else if (direction.equals(RIGHT)) swap(context, position, 1);

            else Toast.makeText(context, "invalid move", Toast.LENGTH_SHORT).show();
        }





    }






}
