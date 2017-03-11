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

public class NormalGame extends AppCompatActivity {

    int turns;
    int current_player;
    int current_player_color;
    int games_status[][];
    int magic_square[][] = new int[][]{{4,9,2},{3,5,7},{8,1,6}};
    LinearLayout main;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_game);
        setupGameData();
        main = (LinearLayout)findViewById(R.id.activity_normal_game);
        setupZ(main);
    }

    public void setupGameData(){
        Log.e("Setting up ","data ");
        turns = 0;
        games_status = new int[3][3];
        current_player = 1;
        current_player_color = Color.RED;
            for (int j = 0 ; j<3 ; j++)
                for ( int k=0 ; k<3 ; k++){
                    games_status[j][k] = 0;
                }
    }

    public void setupZ(LinearLayout zone){
        GridLayout now = new GridLayout(this);
        now.setColumnCount(3);
        now.setRowCount(3);
        now.setTag("zone");
        Button[] game_buttons = new Button[9];
        for(int i = 0; i<9 ; i++){
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
                    Log.e("played by "+current_player,view.getTag()+"");
                    checkZ(zone);
                }
            });
            now.addView(game_buttons[i]);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.setMargins((int)getResources().getDisplayMetrics().density*1,(int)getResources().getDisplayMetrics().density*1,(int)getResources().getDisplayMetrics().density*1,(int)getResources().getDisplayMetrics().density*1);
            lp.width = (int)getResources().getDisplayMetrics().density*50;
            lp.height = (int)getResources().getDisplayMetrics().density*50;
            game_buttons[i].setLayoutParams(lp);
        }
        zone.addView(now);
    }

    public void checkZ(LinearLayout zone){
        Log.e("Inside CheckZ","");
        int temp_status[][] = new int[3][3];
        int win = 0;
        int draw = 0;
        View v = zone.findViewWithTag("zone");
        int count = 0 ;
        for (int i =0 ; i<3 ; i++)
            for (int j = 0; j<3 ; j++)
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
        for(int i = 0; i<3 ; i++ ){
            if((temp_status[i][0]!=0)&&(temp_status[i][0]==temp_status[i][1])&&(temp_status[i][1]==temp_status[i][2]))
            {
                win = current_player;
            }
            else if((temp_status[0][i]!=0)&&(temp_status[0][i]==temp_status[1][i])&&(temp_status[1][i]==temp_status[2][i])){
                win = current_player;
            }
        }

        if((win == 0)&&(temp_status[1][1]!=0)&&(temp_status[0][0] == temp_status[1][1])&&( temp_status[1][1]== temp_status [2][2])){
            win = current_player;
        }

        if((win == 0)&&(temp_status[1][1]!=0)&&(temp_status[2][0] == temp_status[1][1])&&( temp_status[1][1]== temp_status [0][2])){
            win = current_player;
        }

        Log.e("CheckZ","Win Checks Over. Win = "+win);
        if(win == 0) {
            int count_of_boxes_left = 0;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
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
            for (int i =0; i<9; i++)
            {
                Button ab = (Button)(((GridLayout)v).findViewWithTag("z"+i));
                ab.setText(""+current_player);
                ab.setBackgroundColor(current_player_color);
                ab.setClickable(false);
            }
            Toast.makeText(this,"Game Up, Player "+current_player+" wins !",Toast.LENGTH_SHORT).show();
        }

        if (draw == 1){

            for (int i =0; i<9; i++)
            {
                Button ab = (Button)(((GridLayout)v).findViewWithTag("z"+i));
                ab.setText("D");
                ab.setBackgroundColor(Color.LTGRAY);
                ab.setClickable(false);
             }
            Toast.makeText(this,"Booooo !!!",Toast.LENGTH_SHORT).show();

        }
        games_status = temp_status;
        changeTurn();
    }

    public void changeTurn(){
        if(turns<82){
            Log.e("Changing turn",""+turns);
            turns++;
            if(current_player == 1)
            {
                current_player = 2;
                current_player_color = Color.GREEN;
                playTurn();
            }
            else
            {
                Toast.makeText(this,"Your turn now ! Play !",Toast.LENGTH_SHORT).show();
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

    public void playTurn() {
        int played = 0;
        int row[], col[], diag111, diag222, row1[], col1[], diag11, diag21;
        int row2[], col2[], diag12, diag22;
        int player1_status[][], player2_status[][];
        int game_status[][] = new int[3][3];
        row1 = new int[3];
        col1 = new int[3];
        row = new int[3];
        col = new int[3];
        row2 = new int[3];
        col2 = new int[3];
        int count = 0;
        player1_status = new int[3][3];
        player2_status = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                player1_status[i][j] = 0;
                player2_status[i][j] = 0;
                Log.e("count", "" + count);
                Button current = (Button) main.findViewWithTag("z" + count);
                if (((ColorDrawable) current.getBackground()).getColor() == Color.RED) {
                    player1_status[i][j] = 1;
                    game_status[i][j] = 1;
                } else if (((ColorDrawable) current.getBackground()).getColor() == Color.GREEN) {
                    player2_status[i][j] = 1;
                    game_status[i][j] = 1;
                }
                count++;
            }

        for (int i = 0; i < 3; i++) {
            row1[i] = player1_status[i][0] + player1_status[i][1] + player1_status[i][2];
            col1[i] = player1_status[0][i] + player1_status[1][i] + player1_status[2][i];
            row[i] = game_status[i][0] + game_status[i][1] + game_status[i][2];
            col[i] = game_status[0][i] + game_status[1][i] + game_status[2][i];
            row2[i] = player2_status[i][0] + player2_status[i][1] + player2_status[i][2];
            col2[i] = player2_status[0][i] + player2_status[1][i] + player2_status[2][i];
        }
        diag11 = player1_status[0][0] + player1_status[1][1] + player1_status[2][2];
        diag21 = player1_status[0][2] + player1_status[1][1] + player1_status[2][0];
        diag111 = game_status[0][0] + game_status[1][1] + game_status[2][2];
        diag222 = game_status[0][2] + game_status[1][1] + game_status[2][0];
        diag12 = player2_status[0][0] + player2_status[1][1] + player2_status[2][2];
        diag22 = player2_status[0][2] + player2_status[1][1] + player2_status[2][0];

        if (((row2[0] == 2)) && (row[0] != 3)) {
            if (game_status[0][0] == 0) {
                ((Button) main.findViewWithTag("z0")).performClick();
                played = 1;
            } else if (game_status[0][1] == 0) {
                ((Button) main.findViewWithTag("z1")).performClick();
                played = 1;
            } else if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z2")).performClick();
                played = 1;
            }
        } else if (( (row2[1] == 2)) && (row[1] != 3)) {
            if (game_status[1][0] == 0) {
                ((Button) main.findViewWithTag("z3")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[1][2] == 0) {
                ((Button) main.findViewWithTag("z5")).performClick();
                played = 1;
            }
        } else if (((row2[2] == 2)) && (row[2] != 3)) {
            if (game_status[2][0] == 0) {
                ((Button) main.findViewWithTag("z6")).performClick();
                played = 1;
            } else if (game_status[2][1] == 0) {
                ((Button) main.findViewWithTag("z7")).performClick();
                played = 1;
            } else if (game_status[2][2] == 0) {
                ((Button) main.findViewWithTag("z8")).performClick();
                played = 1;
            }
        } else if (((col2[0] == 2)) && (col[0] != 3)) {
            if (game_status[0][0] == 0) {
                ((Button) main.findViewWithTag("z0")).performClick();
                played = 1;
            } else if (game_status[1][0] == 0) {
                ((Button) main.findViewWithTag("z3")).performClick();
                played = 1;
            } else if (game_status[2][0] == 0) {
                ((Button) main.findViewWithTag("z6")).performClick();
                played = 1;
            }
        } else if (((col2[1] == 2)) && (col[1] != 3)) {
            if (game_status[0][1] == 0) {
                ((Button) main.findViewWithTag("z1")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[2][1] == 0) {
                ((Button) main.findViewWithTag("z7")).performClick();
                played = 1;
            }
        } else if (((col2[2] == 2)) && (col[2] != 3)) {
            if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z2")).performClick();
                played = 1;
            } else if (game_status[1][2] == 0) {
                ((Button) main.findViewWithTag("z5")).performClick();
                played = 1;
            } else if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z8")).performClick();
                played = 1;
            }
        } else if (((diag12 == 2)) && (diag111 != 3)) {
            if (game_status[0][0] == 0) {
                ((Button) main.findViewWithTag("z0")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[2][2] == 0) {
                ((Button) main.findViewWithTag("z8")).performClick();
                played = 1;
            }
        } else if (((diag22 == 2)) && (diag222 != 3)) {
            if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z2")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[2][0] == 0) {
                ((Button) main.findViewWithTag("z6")).performClick();
                played = 1;
            }
        } else if (((row1[0] == 2)) && (row[0] != 3)) {
            if (game_status[0][0] == 0) {
                ((Button) main.findViewWithTag("z0")).performClick();
                played = 1;
            } else if (game_status[0][1] == 0) {
                ((Button) main.findViewWithTag("z1")).performClick();
                played = 1;
            } else if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z2")).performClick();
                played = 1;
            }
        } else if (( (row1[1] == 2)) && (row[1] != 3)) {
            if (game_status[1][0] == 0) {
                ((Button) main.findViewWithTag("z3")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[1][2] == 0) {
                ((Button) main.findViewWithTag("z5")).performClick();
                played = 1;
            }
        } else if (((row1[2] == 2)) && (row[2] != 3)) {
            if (game_status[2][0] == 0) {
                ((Button) main.findViewWithTag("z6")).performClick();
                played = 1;
            } else if (game_status[2][1] == 0) {
                ((Button) main.findViewWithTag("z7")).performClick();
                played = 1;
            } else if (game_status[2][2] == 0) {
                ((Button) main.findViewWithTag("z8")).performClick();
                played = 1;
            }
        } else if (((col1[0] == 2)) && (col[0] != 3)) {
            if (game_status[0][0] == 0) {
                ((Button) main.findViewWithTag("z0")).performClick();
                played = 1;
            } else if (game_status[1][0] == 0) {
                ((Button) main.findViewWithTag("z3")).performClick();
                played = 1;
            } else if (game_status[2][0] == 0) {
                ((Button) main.findViewWithTag("z6")).performClick();
                played = 1;
            }
        } else if (((col1[1] == 2)) && (col[1] != 3)) {
            if (game_status[0][1] == 0) {
                ((Button) main.findViewWithTag("z1")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[2][1] == 0) {
                ((Button) main.findViewWithTag("z7")).performClick();
                played = 1;
            }
        } else if (((col1[2] == 2)) && (col[2] != 3)) {
            if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z2")).performClick();
                played = 1;
            } else if (game_status[1][2] == 0) {
                ((Button) main.findViewWithTag("z5")).performClick();
                played = 1;
            } else if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z8")).performClick();
                played = 1;
            }
        } else if (((diag11 == 2)) && (diag111 != 3)) {
            if (game_status[0][0] == 0) {
                ((Button) main.findViewWithTag("z0")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[2][2] == 0) {
                ((Button) main.findViewWithTag("z8")).performClick();
                played = 1;
            }
        } else if (((diag21 == 2)) && (diag222 != 3)) {
            if (game_status[0][2] == 0) {
                ((Button) main.findViewWithTag("z2")).performClick();
                played = 1;
            } else if (game_status[1][1] == 0) {
                ((Button) main.findViewWithTag("z4")).performClick();
                played = 1;
            } else if (game_status[2][0] == 0) {
                ((Button) main.findViewWithTag("z6")).performClick();
                played = 1;
            }
        }
        else{
        }

        if(played!=1){
            if (game_status[1][1]==0){
                ((Button)main.findViewWithTag("z4")).performClick();
                played=1;
            }
            else if(game_status[0][0]==0){
                ((Button)main.findViewWithTag("z0")).performClick();
                played=1;
            }
            else if(game_status[2][2]==0){
                ((Button)main.findViewWithTag("z8")).performClick();
                played=1;
            }
            else if(game_status[0][2]==0){
                ((Button)main.findViewWithTag("z2")).performClick();
                played=1;
            }
            else if(game_status[2][0]==0){
                ((Button)main.findViewWithTag("z6")).performClick();
                played=1;
            }
            else if(game_status[0][1]==0){
                ((Button)main.findViewWithTag("z1")).performClick();
                played=1;
            }
            else if(game_status[1][0]==0){
                ((Button)main.findViewWithTag("z3")).performClick();
                played=1;
            }
            else if(game_status[1][2]==0){
                ((Button)main.findViewWithTag("z5")).performClick();
                played=1;
            }
            else if(game_status[2][1]==0){
                ((Button)main.findViewWithTag("z7")).performClick();
                played=1;
            }
            else{
            }
        }
        if(played!=1){
            Toast.makeText(this,"Something went horribly wrong !",Toast.LENGTH_SHORT).show();
        }
    }

}
