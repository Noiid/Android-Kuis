package com.splash.firebasemahasiswa;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private RecyclerView recyclerView;
    private MahasiswaAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference reference;
    private ArrayList<Mahasiswa> mhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Data Mahasiswa");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MyRecylerView();
        getData();

    }
    private void getData(){
        Toast.makeText(getApplicationContext(),"Mohon tunggu sebentar", Toast.LENGTH_LONG).show();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mhs = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mahasiswa mahasiswa = snapshot.getValue(Mahasiswa.class);

                    mhs.add(mahasiswa);
                }
                adapter = new MahasiswaAdapter(mhs,MainActivity.this);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Data berhasil dimuat", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data gagal dimuat", Toast.LENGTH_LONG).show();
                Log.e("MainActivity ",databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }
    private void MyRecylerView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
