package com.example.pourya.editor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class add extends AppCompatActivity {

    ImageView btn_bank;
    Button btn_submit;
    Button btn_edit;
    EditText et_subject;
    EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_submit = (Button) findViewById(R.id.btn_submit_add);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_bank = (ImageView) findViewById(R.id.ic_back);
        et_subject = (EditText) findViewById(R.id.et_subject_add);
        et_content = (EditText) findViewById(R.id.et_content_add);


//set data for edit
        Intent intent = getIntent();
        String subject_intent = intent.getStringExtra("tv_subject");
        String content_intent = intent.getStringExtra("tv_content");
        final String position = intent.getStringExtra("position");
        et_subject.setText(subject_intent);
        et_content.setText(content_intent);

//back btn
        btn_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add.super.onBackPressed();

            }
        });

//condition to show btn_edit or submit
        if (!et_subject.getText().toString().equals("")) {
            if (btn_submit.getVisibility() == View.VISIBLE) {
                btn_submit.setVisibility(View.GONE);
                btn_edit.setVisibility(View.VISIBLE);
            }
        }

//send edited data to recycler view
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_subject.getText().toString().equals("")) {
                    Toasty.warning(getApplicationContext(), "لطفا موضوع را پر کنید!", Toast.LENGTH_LONG).show();
                } else {
                Intent intent2 = new Intent(add.this, MainActivity.class);
                intent2.putExtra("et_subject", et_subject.getText().toString());
                intent2.putExtra("et_content", et_content.getText().toString());
                intent2.putExtra("position", position);
                setResult(2, intent2);
                finish();
                }
            }
        });

//send new item to recyclerview
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_subject.getText().toString().equals("")) {
                    Toasty.warning(getApplicationContext(), "لطفا موضوع را پر کنید!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(add.this, MainActivity.class);
                    intent.putExtra("et_subject", et_subject.getText().toString());
                    intent.putExtra("et_content", et_content.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
        });

    }
}
