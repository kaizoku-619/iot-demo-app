package com.example.iotdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tempTv = findViewById(R.id.temp_tv);
        final TextView humTv = findViewById(R.id.hum_tv);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference humRef = database.getReference("dht11").child("humidity");
        DatabaseReference tempRef = database.getReference("dht11").child("temperature");

        humRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String, Object> humMap = (Map<String, Object>) dataSnapshot.getValue();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i(TAG, "onDataChange: " + ds.getValue());
                    humTv.setText(ds.getValue().toString());
                    humTv.append(" %");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: " + databaseError.toException());
            }
        });

        tempRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String, Object> tempMap = (Map<String, Object>) dataSnapshot.getValue();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i(TAG, "onDataChange: " + ds.getValue());
                    tempTv.setText(ds.getValue().toString());
                    tempTv.append(" °C");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: " + databaseError.toException());
            }
        });
    }
}
