package com.fisunkayaproject.catchtogarf;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.text.InputType;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.fisunkayaproject.catchtogarf.databinding.ActivityGameBinding;
import com.fisunkayaproject.catchtogarf.databinding.ActivityScoreBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    private ActivityGameBinding activityGameBinding;


    private FirebaseFirestore firebaseFirestore;
    TextView timeText;
    TextView scoreText;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    TextView TextViewNickName;

    int score;
    String nickName;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userInput");
        nickName=userName;

        firebaseFirestore = FirebaseFirestore.getInstance();




        super.onCreate(savedInstanceState);
        activityGameBinding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = activityGameBinding.getRoot();
        setContentView(view);







        timeText=findViewById(R.id.timeText);


        scoreText = findViewById(R.id.scoreText);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);




        score = 0;
        imageArray = new ImageView[]{imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        hideImages();





            new CountDownTimer(10000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timeText.setText("Time: " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    kayitEkle();

                    timeText.setText("Time Off");
                    handler.removeCallbacks(runnable);
                    for (ImageView image : imageArray) {
                        image.setVisibility(View.INVISIBLE);
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);
                    alert.setTitle("Restart?");
                    alert.setMessage("Score: " + score +"... Do you want to play again?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //restart

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(GameActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                            Intent intentToHome = new Intent(GameActivity.this, ScoreActivity.class);
                            startActivity(intentToHome);


                        }
                    });

                    alert.show();

                }
            }.start();
        }




    public void increaseScore(View view){

        score++;
        //score = score + 1;

        scoreText.setText(nickName.toUpperCase()+"'S SCORE: " + score);

    }

    public void hideImages(){
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {





                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,500);

            }
        };


        handler.post(runnable);


    }

    public void kayitEkle(){

        HashMap<String, Object> scoreData = new HashMap<>();
        scoreData.put("user", nickName);
        scoreData.put("score", score);
        scoreData.put("date", FieldValue.serverTimestamp());

        firebaseFirestore.collection("catchtogarf2").add(scoreData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String id=documentReference.getId();
                Toast.makeText(GameActivity.this, "YOUR SCORE ADDED. CHECK THE TABLE", Toast.LENGTH_LONG).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GameActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }



}