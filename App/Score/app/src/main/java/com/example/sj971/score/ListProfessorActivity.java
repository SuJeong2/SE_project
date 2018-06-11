package com.example.sj971.score;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ListProfessorActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

   // ProfessorAdapter adapter;
    ListView listView4;

    ArrayList<String> items;
    ArrayAdapter<String> adapter;

    String[] value = new String[1000];

    Button delete;

    int num=0;

    String[] grade = new String[]{"확인"};

    String professorID, professorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_professor);

        listView4 = (ListView) findViewById(R.id.listView4);
        delete = (Button) findViewById(R.id.delete);

        //adapter = new ProfessorAdapter();

        items = new ArrayList<String>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Mobile/users/professor/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> userList = dataSnapshot.getChildren().iterator();
                while (userList.hasNext()) {
                    DataSnapshot data = userList.next();

                    //교수 아이디 읽고 그 안에 name을 가져와서 출력
                    professorID = data.getKey();

                    professorName = (String)dataSnapshot.child(professorID).child("NAME").getValue();

                   // adapter.addItem(new ListItem(professorID, professorName));
                    value[num] = " " + professorID + " " + professorName;
                    items.add("" + value[num]);
                    num++;
                }

                adapter = new ArrayAdapter<String>(ListProfessorActivity.this,
                        android.R.layout.simple_list_item_single_choice, items);

                // 어댑터 설정
                listView4.setAdapter(adapter);
                listView4.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int pos = listView4.getCheckedItemPosition(); // 현재 선택된 항목의 첨자(위치값) 얻기
                if (pos != ListView.INVALID_POSITION) {      // 선택된 항목이 있으면

                    //디비 값도 삭제해 줘야함


                    items.remove(pos);                       // items 리스트에서 해당 위치의 요소 제거
                    listView4.clearChoices();                 // 선택 해제
                    adapter.notifyDataSetChanged();
                    // 어답터와 연결된 원본데이터의 값이 변경된을 알려 리스트뷰 목록 갱신
                }
            }
        });
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new AlertDialog.Builder(this).setTitle("삭제하시겠습니까?")
                .setItems(grade, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int num) {


                    }
                }).setNegativeButton("", null).show();
    }

    private class ProfessorAdapter extends BaseAdapter{
        ArrayList<ListItem> items = new ArrayList<ListItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ListItem item) {
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

            ListItemView view = new ListItemView(getApplicationContext());

            ListItem item = items.get(position);

            view.setNumber(item.getNumber());
            view.setName(item.getName());

            return view;
        }
    }
    */
}