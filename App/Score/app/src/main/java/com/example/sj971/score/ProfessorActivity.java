package com.example.sj971.score;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ProfessorActivity extends AppCompatActivity {

    ListView listView;
    SubjectAdapter adapter;

    int line = 4; //디비 속 row 개수
    String[] number = new String[line];
    String[] subject = new String[line];

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    TextView textYear;
    TextView textSemester;

    Button selectButton;
    Spinner year_spinner;
    Spinner semester_spinner;

    String[] year_value = {"2015", "2016", "2017", "2018"};
    String[] semester_value = {"1학기", "여름학기", "2학기", "겨울학기"};

    String year;
    String semester;

    private final int DYNAMIC_VIEW_ID = 0x8000;
    private LinearLayout dynamicLayout;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("ID");

        textYear=(TextView)findViewById(R.id.year);
        textSemester=(TextView)findViewById(R.id.semester);

        selectButton = (Button) findViewById(R.id.select);
        year_spinner = (Spinner) findViewById(R.id.select_year);
        semester_spinner = (Spinner) findViewById(R.id.select_semester);

        listView = (ListView) findViewById(R.id.listView);

        number[0] = "0939209";
        number[1] = "0939248";
        number[2] = "0939262";
        number[3] = "0939202";

        subject[0] = "소프트웨어 공학";
        subject[1] = "컴퓨터 그래픽스";
        subject[2] = "모바일 프로그래밍";
        subject[3] = "경영학원론";

        ArrayAdapter<String> adapter_year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year_value);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(adapter_year);

        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long id) {
                year = year_value[i];
                textYear.setText(year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter_semester = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semester_value);
        adapter_semester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester_spinner.setAdapter(adapter_semester);

        semester_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long id) {
                semester = semester_value[i];
                textSemester.setText(semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapter = new SubjectAdapter();

        selectButton=(Button)findViewById(R.id.select);

        selectButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                adapter = new SubjectAdapter();

                if(semester.equals("1학기") && year.equals("2018")){
                    for(int i=0; i<line; i++){
                        adapter.addItem(new SubjectItem(number[i], subject[i]));
                    }
                }

                /*
                //사용자가 선택한 연도와 학기에 따른 디비 값을 읽어서 출력
                databaseReference = database.getReference("Mobile/users/professor/" + id + "/subject/" + year + "/" + semester);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> userList = dataSnapshot.getChildren().iterator();
                        while (userList.hasNext()) {
                            DataSnapshot data = userList.next();

                            String subjectID = data.getKey(); //과목 ID 값

                            String subjectName = (String)data.child(subjectID).child("subjectName").getValue(); //과목 이름 가져옴

                            adapter.addItem(new SubjectItem(subjectID, subjectName));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                */
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                SubjectItem item = (SubjectItem) adapter.getItem(position);


                Intent intent = new Intent(getApplicationContext(), InputGradeActivity.class);

                Bundle select_number = new Bundle();

                select_number.putString("Number", item.getNumber());
                select_number.putString("Subject", item.getSubject());
                select_number.putString("Year", year);
                select_number.putString("Semester", semester);

                intent.putExtras(select_number);

                startActivity(intent);
            }
        });

    }

    class SubjectAdapter extends BaseAdapter {
        ArrayList<SubjectItem> items = new ArrayList<SubjectItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SubjectItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            SubjectItemView view = new SubjectItemView(getApplicationContext());

            SubjectItem item = items.get(position);

            view.setScore(item.getNumber());
            view.setSubject(item.getSubject());

            return view;
        }

    }
}