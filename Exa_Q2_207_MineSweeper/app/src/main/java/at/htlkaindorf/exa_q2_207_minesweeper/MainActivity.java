package at.htlkaindorf.exa_q2_207_minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import at.htlkaindorf.exa_q2_207_minesweeper.bl.GameLogic;
import at.htlkaindorf.exa_q2_207_minesweeper.bl.MineSweeperField;

public class MainActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private GameLogic gameLogic;
    private static final int FIELD_SIZE = 9;
    private Button[][] buttons;
    private boolean initialised = false;
    private TextView tvMineCount;
    private ImageButton ibReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[FIELD_SIZE][FIELD_SIZE];
        gridLayout = findViewById(R.id.glButtons);
        tvMineCount = findViewById(R.id.tvMineCount);
        ibReset = findViewById(R.id.ibReset);

//        FrameLayout.LayoutParams llLP = (FrameLayout.LayoutParams)gridLayout.getLayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                Button bt = new Button(this);
                buttons[i][j] = bt;
                bt.setId(i*10 + j);
                bt.setTextColor(getResources().getColor(R.color.white, null));
                bt.setBackgroundColor(getResources().getColor(R.color.button, null));
                gridLayout.addView(bt);
                ViewGroup.LayoutParams params = bt.getLayoutParams();
                GridLayout.LayoutParams lp = (GridLayout.LayoutParams) bt.getLayoutParams();
                int margin = 4;
                lp.setMargins(margin, margin, margin, margin);

                params.width = width / FIELD_SIZE - 2*margin;
                params.height = width / FIELD_SIZE;

                bt.setOnClickListener(this::onClickButton);
                bt.setOnLongClickListener(this::onLongClickButton);
            }
        }

        ibReset.setOnClickListener(this::onReset);

    }

    private void initialise(Integer[] position){
        gameLogic = new GameLogic(position, FIELD_SIZE);
        tvMineCount.setText(Integer.toString(GameLogic.NO_MINES - gameLogic.getNoTaggedMines()));
    }

    private boolean onLongClickButton(View view){
        int[] pos = getClickedButton(view);

        if (!initialised) {
            Integer[] position = {pos[0], pos[1]};
            initialise(position);
            initialised = true;
        }
        int result = gameLogic.makeMove(pos, true);
        if(result == -2){ // TODO: set flag instead of background
            buttons[pos[0]][pos[1]].setBackgroundColor(getResources().getColor(R.color.tagged, null));
        }else{
            buttons[pos[0]][pos[1]].setBackgroundColor(getResources().getColor(R.color.button, null));
        }
        tvMineCount.setText(Integer.toString(GameLogic.NO_MINES - gameLogic.getNoTaggedMines()));

        return true;
    }

    private void onClickButton(View view){
        int[] pos = getClickedButton(view);
        if (!initialised) {
            Integer[] position = {pos[0], pos[1]};
            initialise(position);
            initialised = true;
        }
        int result = gameLogic.makeMove(pos, false);
        if(result == -4){
            buttons[pos[0]][pos[1]].setText(Integer.toString(gameLogic.getNoMines(pos)));
            win();
        }
        else if(result != -1){
            gameLogic.getRevealedFields().forEach(revealedPos -> {
                buttons[revealedPos[0]][revealedPos[1]].setBackgroundColor(getResources().getColor(R.color.grey, null));
                buttons[revealedPos[0]][revealedPos[1]].setEnabled(false);
                int noMines = gameLogic.getNoMines(new int[]{revealedPos[0], revealedPos[1]});
                if(noMines > 0){
                    buttons[revealedPos[0]][revealedPos[1]].setText(Integer.toString(noMines));
                }
            });
            String text = "";
            if(gameLogic.getNoMines(pos) != 0){
                text = Integer.toString(gameLogic.getNoMines(pos));
            }
            buttons[pos[0]][pos[1]].setText(text); // set no neighbours

        }else{
            // if touched mine
            lose();
        }

    }

    private void onReset(View view){
        initialised = false;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                int[] pos = {i,j};
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(getResources().getColor(R.color.button, null));
                buttons[i][j].setEnabled(true);
                tvMineCount.setText(Integer.toString(GameLogic.NO_MINES));
                // TODO: 06.02.2020 reset timer
            }
        }
    }

    private int[] getClickedButton(View view){
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if(buttons[i][j].equals(view)){
                    Integer[] position = {i,j};
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    private void lose(){
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                MineSweeperField field = gameLogic.getMineSweeperField(new int[]{i,j});
                buttons[i][j].setEnabled(false);
                if(field.isMine()){
                    if(field.isTagged()){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.correctlyTagged, null));
                    }else{
                        if(field.isRevealed()){
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.clickedMine, null));
                        }else{
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.mine, null));
                        }

                    }
                }else {
                    if(field.isRevealed()){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.grey, null));
                    }else{
                        if(field.isTagged()){
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.wronglyTagged, null));
                        }else{
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.button, null));
                        }

                    }

                }
            }
        }
    }
    private void win(){
        // show mines
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                MineSweeperField field = gameLogic.getMineSweeperField(new int[]{i,j});
                buttons[i][j].setEnabled(false);
                if(field.isMine()){
                    buttons[i][j].setBackgroundColor(getResources().getColor(R.color.mine, null));
                }else {
                    buttons[i][j].setBackgroundColor(getResources().getColor(R.color.grey, null));
                }
            }
        }
    }
}
