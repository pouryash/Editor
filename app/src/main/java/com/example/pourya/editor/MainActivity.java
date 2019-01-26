package com.example.pourya.editor;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pourya.editor.sql.SqlHelper;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements adapter.onNoteItemClick{

    FloatingActionButton fab_add;
    Button btn_remove_all;
    adapter adapter;
    RecyclerView recyclerView;
    public static final int REQUESTCODE = 1001;
    private AlertDialog.Builder builderAlert;
    private List<Note> notes = new ArrayList<>();
    SqlHelper helper = new SqlHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String subject_intent = intent.getStringExtra("et_subject");
        String content_intent = intent.getStringExtra("et_content");

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_main);
        if (helper.SelectAllNotes().size() !=0){
            notes = helper.SelectAllNotes();
        }
        adapter = new adapter(notes,MainActivity.this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add_main);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, add.class);
                startActivityForResult(intent, REQUESTCODE);

            }
        });

        btn_remove_all = (Button) findViewById(R.id.remove1_all);
        btn_remove_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                adapter.removeall();

                if (notes.isEmpty()){
                    Toasty.normal(getApplicationContext(), "یادداشتی برا حذف کردن وجود ندارد", Toast.LENGTH_SHORT).show();
                }else {
                    builderAlert = new AlertDialog.Builder(MainActivity.this);
                    builderAlert.setTitle("Warning!!!").
                            setMessage("Do You Want Delete All Notes?").
                            setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    helper.deleteAll();
                                    notes.removeAll(notes);
                                    adapter.notifyDataSetChanged();
                                    Toasty.success(MainActivity.this,"All Note Succssesfully Deleted.",Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setIcon(R.drawable.ic_warning).show();

                }
//                adapter.notifyItemRemoved();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2 && data != null) {
            String message = data.getStringExtra("et_subject");
            String message2 = data.getStringExtra("et_content");
            String position = data.getStringExtra("position");
            int pos = 0;
            pos = Integer.parseInt(position);
//            notes notes = new notes();
//            notes.setSubject(message);
//            notes.setContent(message2);
//            adapter.updateNote(notes, pos);
            Note note = this.notes.get(pos);
            note.setSubject(message);
            note.setContent(message2);
            helper.updateNote(note);
            adapter.notifyItemChanged(pos);
        }

        if (requestCode == REQUESTCODE && data != null) {
            String message = data.getStringExtra("et_subject");
            String message2 = data.getStringExtra("et_content");
            Note note = new Note();
            note.setSubject(message);
            note.setContent(message2);
            int i=0;
            note.setId(i);
            long id = helper.insertNote(note);
            this.notes.add(helper.getNote(id));
            adapter.notifyDataSetChanged();
            i=+ 1;

        }

    }

    @Override
    public void onDelete(Note note , int postion) {
        this.notes.remove(note);
        helper.deleteNote(note);
        adapter.notifyItemRemoved(postion);
        adapter.notifyItemRangeRemoved(postion,helper.SelectAllNotes().size());
    }

    @Override
    public void onEdite(Note note,int position) {

        Intent intent2 = new Intent(MainActivity.this, add.class);
        intent2.putExtra("tv_subject", note.getSubject());
        intent2.putExtra("tv_content", note.getContent());
//        intent2.putExtra("tv_content", ((adapterVH) holder).conetnt.getText().toString());
        intent2.putExtra("position", position + "");
        MainActivity.this.startActivityForResult(intent2, 2);

    }
}
