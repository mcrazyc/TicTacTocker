package com.iclub.tictactocker;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NormalGame4 extends AppCompatActivity {

    LinearLayout[] z;
    int turns;
    int current_player;
    int current_player_color;
    int main_game_win;
    int main_game_status[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGameData();
        setContentView(R.layout.activity_normal_game4);
        z = new LinearLayout[16];
        z[0] = (LinearLayout)findViewById(R.id.z1);
        z[1] = (LinearLayout)findViewById(R.id.z2);
        z[2] = (LinearLayout)findViewById(R.id.z3);
        z[3] = (LinearLayout)findViewById(R.id.z4);
        z[4] = (LinearLayout)findViewById(R.id.z5);
        z[5] = (LinearLayout)findViewById(R.id.z6);
        z[6] = (LinearLayout)findViewById(R.id.z7);
        z[7] = (LinearLayout)findViewById(R.id.z8);
        z[8] = (LinearLayout)findViewById(R.id.z9);
        z[9] = (LinearLayout)findViewById(R.id.z10);
        z[10] = (LinearLayout)findViewById(R.id.z11);
        z[11] = (LinearLayout)findViewById(R.id.z12);
        z[12] = (LinearLayout)findViewById(R.id.z13);
        z[13] = (LinearLayout)findViewById(R.id.z14);
        z[14] = (LinearLayout)findViewById(R.id.z15);
        z[15] = (LinearLayout)findViewById(R.id.z16);


        for (int i= 0 ; i<16; i++){
            setupZ(z[i]);
        }
    }

    public void setupGameData(){
        Log.e("Setting up ","data ");
        turns = 0;
        main_game_win = 0;
        main_game_status = new int[4][4];
        current_player = 1;
        current_player_color = Color.RED;
            for (int j = 0 ; j<4 ; j++)
                for ( int k=0 ; k<4 ; k++){
                    main_game_status[j][k] = 0;
                }
    }

    public void changeTurn(){
        if(turns<256){
            Log.e("Changing turn",""+turns);
            turns++;
            if(current_player == 1)
            {
                current_player = 2;
                current_player_color = Color.GREEN;
            }
            else
            {
                current_player = 1;
                current_player_color = Color.RED;
            }
        }
        else{
            Log.e("Not changing turn","");
            Toast.makeText(this,"Game Over !",Toast.LENGTH_SHORT).show();
            this.recreate();
        }
    }



    public void checkZ(LinearLayout zone){
        Log.e("Inside CheckZ","");
        int temp_status[][] = new int[4][4];
        int win = 0;
        int draw = 0;
        View v = zone.findViewWithTag("zone");
        int count = 0 ;
        for (int i =0 ; i<4 ; i++)
            for (int j = 0; j<4 ; j++)
            {
                temp_status[i][j] = 0;
                Log.e("count",""+count);
                Button current = (Button)v.findViewWithTag("z"+count);
                if(((ColorDrawable)current.getBackground()).getColor() == Color.RED){
                    temp_status[i][j] = 1;
                }
                else if(((ColorDrawable)current.getBackground()).getColor() == Color.GREEN){
                    temp_status[i][j] = 2;
                }
                count++;
            }

        //checking rows and columns
        for(int i = 0; i<4 ; i++ ){
            if((temp_status[i][0]!=0)&&(temp_status[i][0]==temp_status[i][1])&&(temp_status[i][1]==temp_status[i][2])&&(temp_status[i][2]==temp_status[i][3]))
            {
                win = current_player;
            }
            else if((temp_status[0][i]!=0)&&(temp_status[0][i]==temp_status[1][i])&&(temp_status[1][i]==temp_status[2][i])&&(temp_status[2][i]==temp_status[3][i])){
                win = current_player;
            }
        }

        if((win == 0)&&(temp_status[1][1]!=0)&&(temp_status[0][0] == temp_status[1][1])&&( temp_status[1][1]== temp_status [2][2])&&( temp_status[2][2]== temp_status [3][3])){
            win = current_player;
        }

        if((win == 0)&&(temp_status[0][3]!=0)&&(temp_status[0][3] == temp_status[1][2])&&( temp_status[1][2]== temp_status [2][1])&&( temp_status[2][1]== temp_status [3][0])){
            win = current_player;
        }

        Log.e("CheckZ","Win Checks Over. Win = "+win);
        if(win == 0) {
            int count_of_boxes_left = 0;
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    if (temp_status[i][j]==0){
                        count_of_boxes_left++;
                    }
                }
            Log.e("Checked Draw ","turns = "+count_of_boxes_left);
            if (count_of_boxes_left == 0){
                draw = 1;
            }
        }

        if(win!=0){
            for (int i =0; i<16; i++)
            {
                Button ab = (Button)(((GridLayout)v).findViewWithTag("z"+i));
                ab.setText(""+current_player);
                ab.setBackgroundColor(current_player_color);
                ab.setClickable(false);
            }
        }

        if (draw == 1){

            for (int i =0; i<16; i++)
            {
                Button ab = (Button)(((GridLayout)v).findViewWithTag("z"+i));
                ab.setText("D");
                ab.setBackgroundColor(Color.LTGRAY);
                ab.setClickable(false);
            }
        }

        switch(zone.getId()){
            case R.id.z1 :
                if( win!=0){
                    main_game_status[0][0] = current_player;
                }
                if (draw == 1){
                    main_game_status[0][0] = 5;
                }
                break;
            case R.id.z2 :
                if( win!=0){
                    main_game_status[0][1] = current_player;
                }
                if (draw == 1){
                    main_game_status[0][1] = 5;
                }
                break;
            case R.id.z3 :
                if( win!=0){
                    main_game_status[0][2] = current_player;
                }
                if (draw == 1){
                    main_game_status[0][2] = 5;
                }
                break;
            case R.id.z4 :
                if( win!=0){
                    main_game_status[0][3] = current_player;
                }
                if (draw == 1){
                    main_game_status[0][3] = 5;
                }
                break;
            case R.id.z5 :
                if( win!=0){
                    main_game_status[1][0] = current_player;
                }
                if (draw == 1){
                    main_game_status[1][0] = 5;
                }
                break;
            case R.id.z6 :
                if( win!=0){
                    main_game_status[1][1] = current_player;
                }
                if (draw == 1){
                    main_game_status[1][1] = 5;
                }
                break;
            case R.id.z7 :
                if( win!=0){
                    main_game_status[1][2] = current_player;
                }
                if (draw == 1){
                    main_game_status[1][2] = 5;
                }
                break;
            case R.id.z8 :
                if( win!=0){
                    main_game_status[1][3] = current_player;
                }
                if (draw == 1){
                    main_game_status[1][3] = 5;
                }
                break;
            case R.id.z9 :
                if( win!=0){
                    main_game_status[2][0] = current_player;
                }
                if (draw == 1){
                    main_game_status[2][0] = 5;
                }
                break;
            case R.id.z10 :
                if( win!=0){
                    main_game_status[2][1] = current_player;
                }
                if (draw == 1){
                    main_game_status[2][1] = 5;
                }
                break;
            case R.id.z11 :
                if( win!=0){
                    main_game_status[2][2] = current_player;
                }
                if (draw == 1){
                    main_game_status[2][2] = 5;
                }
                break;
            case R.id.z12 :
                if( win!=0){
                    main_game_status[2][3] = current_player;
                }
                if (draw == 1){
                    main_game_status[2][3] = 5;
                }
                break;
            case R.id.z13 :
                if( win!=0){
                    main_game_status[3][0] = current_player;
                }
                if (draw == 1){
                    main_game_status[3][0] = 5;
                }
                break;
            case R.id.z14 :
                if( win!=0){
                    main_game_status[3][1] = current_player;
                }
                if (draw == 1){
                    main_game_status[3][1] = 5;
                }
                break;
            case R.id.z15 :
                if( win!=0){
                    main_game_status[3][2] = current_player;
                }
                if (draw == 1){
                    main_game_status[3][2] = 5;
                }
                break;
            case R.id.z16 :
                if( win!=0){
                    main_game_status[3][3] = current_player;
                }
                if (draw == 1){
                    main_game_status[3][3] = 5;
                }
                break;

        }
        checkMain();
        changeTurn();
    }

    public void checkMain(){
        int win = 0;
        int draw = 0;

        //checking rows and columns
        for(int i = 0; i<4 ; i++ ){
            if((main_game_status[i][0]!=0)&&(main_game_status[0][i]!=5)&&(main_game_status[i][0]==main_game_status[i][1])&&(main_game_status[i][1]==main_game_status[i][2])&&(main_game_status[i][2]==main_game_status[i][3]))
            {
                win = current_player;
            }
            else if((main_game_status[0][i]!=0)&&(main_game_status[0][i]!=5)&&(main_game_status[0][i]==main_game_status[1][i])&&(main_game_status[1][i]==main_game_status[2][i])&&(main_game_status[2][i]==main_game_status[3][i])){
                win = current_player;
            }
        }

        if((win == 0)&&(main_game_status[1][1]!=0)&&(main_game_status[1][1]!=5)&&(main_game_status[0][0] == main_game_status[1][1])&&( main_game_status[1][1]== main_game_status [2][2])&&( main_game_status[2][2]== main_game_status [3][3])){
            win = current_player;
        }

        if((win == 0)&&(main_game_status[0][3]!=0)&&(main_game_status[0][3]!=5)&&(main_game_status[0][3] == main_game_status[1][2])&&( main_game_status[1][2]== main_game_status [2][1])&&( main_game_status[2][1]== main_game_status [3][0])){
            win = current_player;
        }

        if(win == 0) {
            int count_of_boxes_left = 0;
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    if (main_game_status[i][j]==0){
                        count_of_boxes_left++;
                    }
                }
            if (count_of_boxes_left == 0){
                draw = 1;
            }
        }

        if(win!=0){
            Toast.makeText(this,"Game Up ! Player "+current_player+" wins !",Toast.LENGTH_SHORT).show();
            this.recreate();
        }

        if (draw == 1){
            Toast.makeText(this,"Game Up ! Nobody wins ! Boooooooo !",Toast.LENGTH_SHORT).show();
            this.recreate();        }
    }

    public void setupZ(LinearLayout zone){
        GridLayout now = new GridLayout(this);
        now.setColumnCount(4);
        now.setRowCount(4);
        now.setTag("zone");
        Button[] game_buttons = new Button[16];
        for(int i = 0; i<16 ; i++){
            game_buttons[i] = new Button(this);
            game_buttons[i].setText(" ");
            game_buttons[i].setTag("z"+i);
            Log.e("","z"+i);
            game_buttons[i].setBackgroundColor(Color.DKGRAY);
            game_buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackgroundColor(current_player_color);
                    view.setClickable(false);
                    View grid = (View)view.getParent();
                    LinearLayout zone = (LinearLayout)grid.getParent();
                    checkZ(zone);
                }
            });
            now.addView(game_buttons[i]);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.setMargins((int)getResources().getDisplayMetrics().density*1,(int)getResources().getDisplayMetrics().density*1,(int)getResources().getDisplayMetrics().density*1,(int)getResources().getDisplayMetrics().density*1);
            lp.width = (int)getResources().getDisplayMetrics().density*15;
            lp.height = (int)getResources().getDisplayMetrics().density*15;
            game_buttons[i].setLayoutParams(lp);
        }
        zone.addView(now);
    }
}
