package com.example.amour.mynotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;

/**
 * Created by Amour on 2016/4/7.
 */
public class NoteActivity extends AppCompatActivity {
    private int mPosition;
    private Note mNote=null;
    private TextInputLayout mTextInput;
    private EditText metTitle;
    private EditText metText;
    private TextView mtvTime;
    private Toolbar mToolbar;
    private Time localTime=new Time(System.currentTimeMillis());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mPosition=getIntent().getIntExtra("NotePosition",-1);
        initView();
        if(mPosition!=-1){
            initData();
        }

    }
    public void initView(){


        mToolbar= (Toolbar) findViewById(R.id.notetoolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("新的笔记");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTextInput= (TextInputLayout) findViewById(R.id.textinput);
        metTitle= (EditText) findViewById(R.id.etTitle);
        metText= (EditText) findViewById(R.id.etText);
        mtvTime= (TextView) findViewById(R.id.tvTime);
        mTextInput.setHint("标题");
    }
    public void initData(){

        mNote=MainActivity.mNotes.get(mPosition);
        mToolbar.setTitle(mNote.getmTitle());
        metTitle.setText(mNote.getmTitle());
        metText.setText(mNote.getmText());
        mtvTime.setText(mNote.getmTime());
    }

    @Override
    public void onBackPressed() {

        if(mPosition==-1){
            if(!metText.getText().toString().equals("")||!metTitle.getText().toString().equals("")){
                mNote = new Note();
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setMessage("是否保存更改");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNote.setmTitle(metTitle.getText().toString());
                        mNote.setmText(metText.getText().toString());
                        mNote.setmTime(mtvTime.getText().toString());
                        mNote.setmTime(localTime.toString());
                        dialog.dismiss();
                        MainActivity.mNotes.add(0,mNote);
                        NoteActivity.this.finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        NoteActivity.this.finish();
                    }
                });
//                dialog.setCancelable(false);
                dialog.create().show();
            }else this.finish();
        }else if(mNote.getmText().equals(metText.getText().toString())&&mNote.getmTitle().equals(metTitle.getText().toString())){
//            super.onBackPressed();
                this.finish();
        }else {
            remain();
        }

    }


    public void remain(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("是否保存更改");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNote.setmTitle(metTitle.getText().toString());
                mNote.setmText(metText.getText().toString());
                mNote.setmTime(mtvTime.getText().toString());
                mNote.setmTime(localTime.toString());
                dialog.dismiss();
                NoteActivity.this.finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                NoteActivity.this.finish();
            }
        });
//        dialog.setCancelable(false);
        dialog.create().show();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnSave:
                if(metTitle.getText().length()==0&& metText.getText().length()==0){
                    Snackbar.make(mtvTime, "你没有留下文字", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                if(mNote==null) {
                    mNote = new Note();
                    mNote.setmTitle(metTitle.getText().toString());
                    mNote.setmText(metText.getText().toString());
                    mNote.setmTime(mtvTime.getText().toString());
                    mNote.setmTime(localTime.toString());
                    MainActivity.mNotes.add(mNote);
                }else {
                    mNote.setmTitle(metTitle.getText().toString());
                    mNote.setmText(metText.getText().toString());
                    mNote.setmTime(mtvTime.getText().toString());
                    mNote.setmTime(localTime.toString());
                }
                this.finish();
                break;
            case R.id.btnDelete:
                MainActivity.mNotes.remove(mPosition);
                this.finish();
                break;
        }
        return true;
    }
}
