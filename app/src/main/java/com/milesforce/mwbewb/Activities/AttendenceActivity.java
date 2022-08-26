package com.milesforce.mwbewb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class AttendenceActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<DelaysModel>delaysModelArrayList;
    TextView title_text;
    AppCompatImageButton bt_menu_back_tomain;
    RecyclerView attendence_recycler;
    AttendanceAdapter attendanceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        title_text=findViewById(R.id.title_text);
        bt_menu_back_tomain=findViewById(R.id.bt_menu_back_tomain);
        bt_menu_back_tomain.setOnClickListener(this);
        delaysModelArrayList= (ArrayList<DelaysModel>) getIntent().getSerializableExtra("student_list");
        title_text.setText("Total : "+String.valueOf(delaysModelArrayList.size()));
        attendence_recycler=findViewById(R.id.attendence_recycler);
        attendence_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        attendanceAdapter=new AttendanceAdapter(getApplicationContext(),delaysModelArrayList);
        attendence_recycler.setAdapter(attendanceAdapter);
        attendanceAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_menu_back_tomain:
            finish();
            break;
        }

    }
}
