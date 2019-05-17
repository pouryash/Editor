package com.example.pourya.editor;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pourya.editor.Data.DAO;
import com.example.pourya.editor.MVP.MVP_Main;
import com.example.pourya.editor.MVP.MainModel;
import com.example.pourya.editor.MVP.MainPresenter;
import com.example.pourya.editor.MVP.NotesViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends RuntimePermissionsActivity implements View.OnClickListener,
        MVP_Main.RequiredViewOps {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQ = 10;

    FloatingActionButton fab_add;
    Button btn_remove_all;
    ListNotes adapter;
    RecyclerView recyclerView;
    public static final int REQUESTCODE = 1001;
    private AlertDialog.Builder builderAlert;

    @Inject
    MVP_Main.ProvidedPresenterOps mPresenter = new MainPresenter(this, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQ);
//        Intent intent = getIntent();
//        String subject_intent = intent.getStringExtra("et_subject");
//        String content_intent = intent.getStringExtra("et_content");
        setupViews();

///////////////////////////////////////////////////////////////
//        recyclerView = (RecyclerView) findViewById(R.id.recycleview_main);
//        if (helper.SelectAllNotes().size() !=0){
//            notes = helper.SelectAllNotes();
//        }
//        adapter = new adapter(notes,MainActivity.this,this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//////////////////////////////////////////////////////////////
//        fab_add = (FloatingActionButton) findViewById(R.id.fab_add_main);
//        fab_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(MainActivity.this, add.class);
//                startActivityForResult(intent, REQUESTCODE);
//
//            }
//        });
///////////////////////////////////////////////////////////////////////
//        btn_remove_all = (Button) findViewById(R.id.remove1_all);
//        btn_remove_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                adapter.removeall();
//
//                if (notes.isEmpty()) {
//                    Toasty.normal(getApplicationContext(), "یادداشتی برا حذف کردن وجود ندارد", Toast.LENGTH_SHORT).show();
//                } else {
//                    builderAlert = new AlertDialog.Builder(MainActivity.this);
//                    builderAlert.setTitle("Warning!!!").
//                            setMessage("Do You Want Delete All Notes?").
//                            setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    helper.deleteAll();
//                                    notes.removeAll(notes);
//                                    adapter.notifyDataSetChanged();
//                                    Toasty.success(MainActivity.this, "All Note Succssesfully Deleted.", Toast.LENGTH_SHORT).show();
//                                }
//                            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    }).setIcon(R.drawable.ic_warning).show();
//
//                }
////                adapter.notifyItemRemoved();
//
//            }
//        });


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onPermissionsDeny(int requestCode) {

    }


    private void setupViews() {

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_main);
//        if (helper.SelectAllNotes().size() !=0){
//            notes = helper.SelectAllNotes();
//        }
        adapter = new ListNotes();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add_main);
        fab_add.setOnClickListener(this);


        btn_remove_all = (Button) findViewById(R.id.remove1_all);
        btn_remove_all.setOnClickListener(this);
    }


    public int getNum(int num) {

        return num + 10;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mPresenter.onActivityResult(requestCode, resultCode, data);

    }
/////////////////////////////////////////////////////
//    @Override
//    public void onDelete(Note note , int postion) {
//        this.notes.remove(note);
//        helper.deleteNote(note);
//        adapter.notifyItemRemoved(postion);
//        adapter.notifyItemRangeRemoved(postion,helper.SelectAllNotes().size());
//    }
//
//    @Override
//    public void onEdite(Note note,int position) {
//
//        Intent intent2 = new Intent(MainActivity.this, add.class);
//        intent2.putExtra("tv_subject", note.getSubject());
//        intent2.putExtra("tv_content", note.getContent());
////        intent2.putExtra("tv_content", ((adapterVH) holder).conetnt.getText().toString());
//        intent2.putExtra("position", position + "");
//        MainActivity.this.startActivityForResult(intent2, 2);
//
//    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab_add_main: {

                mPresenter.clickNewNote(MainActivity.this);
                break;
            }
            case R.id.remove1_all: {

                mPresenter.clickDeleteAll(MainActivity.this);
                break;
            }
        }
    }

    @Override
    public Context getAppContext1() {
        return MainActivity.this.getApplicationContext();
    }

    @Override
    public Context getActivityContext1() {
        return MainActivity.this;
    }

    @Override
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }


    private class ListNotes extends RecyclerView.Adapter<NotesViewHolder> {


        @Override
        public int getItemCount() {
            return mPresenter.getNotesCount();
        }

        @Override
        public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(NotesViewHolder holder, int position) {
            mPresenter.bindViewHolder(holder, position, MainActivity.this);
        }

    }
}
