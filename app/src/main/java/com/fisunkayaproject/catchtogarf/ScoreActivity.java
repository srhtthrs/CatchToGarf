package com.fisunkayaproject.catchtogarf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fisunkayaproject.catchtogarf.databinding.ActivityScoreBinding;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

public class ScoreActivity extends AppCompatActivity {

    private ActivityScoreBinding binding;
    int sira=0;

    private FirebaseFirestore firebaseFirestore;
    ArrayList<DataModel> modelScoreArrayList;
    ScoreListAdapter adapterStok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        modelScoreArrayList = new ArrayList<>();



        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterStok = new ScoreListAdapter (modelScoreArrayList);
        binding.RecyclerView.setAdapter(adapterStok);
         getDataScore();





    }

    public void getDataScore()
    {
        firebaseFirestore.collection("catchtogarf2").orderBy("score", Query.Direction.DESCENDING).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ScoreActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if (value != null) {
                    modelScoreArrayList.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();



                        sira++;

                        String siraString=String.valueOf(sira);

                        String scoreString=String.valueOf(data.get("score"));
                        String name = (String) data.get("user");
                        String id= snapshot.getId();
                        Timestamp date = (Timestamp) data.get("date"); //burada bir dönüşüm yapıldı
                        String dateee=null;
                        try{
                            Date datee=date.toDate();
                            dateee= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(datee).toString();

                        }catch(Exception e){e.getLocalizedMessage();}




                        DataModel pst = new DataModel("","Score: "+scoreString,"User: "+name,""+dateee,"---- "+sira+" ----");
                        modelScoreArrayList.add(pst);
                    }
                    adapterStok.notifyDataSetChanged();  // değişikleri göstermek.
                }
            }
        });


    }
}