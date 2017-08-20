package vin.com.carracing;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity  implements View.OnClickListener{

    //declaring gameview
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout game = new FrameLayout(this);

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y,getIntent().getIntExtra("spd",20));

        LinearLayout gameWidgets = new LinearLayout(this);

        Button endGameButton = new Button(this);
        //TextView myText = new TextView(this);

        //endGameButton.setWidth(300);
        endGameButton.setText("Restart");
        //myText.setText("rIZ..i");

        endGameButton.setGravity(Gravity.CENTER);
        endGameButton.setBackgroundColor(Color.BLACK);
        endGameButton.setTextColor(Color.WHITE);
        //gameWidgets.addView(myText);
       // endGameButton.setPadding(20,5,0,0);
        gameWidgets.addView(endGameButton);

        game.addView(gameView);
        game.addView(gameWidgets);

        setContentView(game);
        endGameButton.setOnClickListener(this);

        //adding it to contentview
        //setContentView(gameView);
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // re-starts this activity from game-view. add this.finish(); to remove from stack
    }
}