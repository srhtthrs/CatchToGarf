package com.fisunkayaproject.catchtogarf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fisunkayaproject.catchtogarf.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    String nickName;
    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
             View view = activityMainBinding.getRoot();
             setContentView(view);

        }

        public void scoreTable(View view){

            Intent intentToScore=new Intent(MainActivity.this,ScoreActivity.class);
            startActivity(intentToScore);

        }

        public void enterGame(View view){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Enter Name");
            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (input.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Please Enter Name", Toast.LENGTH_LONG).show();
                    } else {
                        nickName = input.getText().toString();
                        Intent intentToGame=new Intent(MainActivity.this,GameActivity.class);
                        intentToGame.putExtra("userInput",nickName);
                        startActivity(intentToGame);


                    }
                }});
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });builder.show();




    }
}