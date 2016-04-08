package com.example.amour.mynotes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static List<Note> mNotes;
    public static List<Note> deleteNoyes;
    private MyDatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private RecyclerView mRecycleView;
    private NotesAdapter adapter;
    private NotesAdapter deleteApapter;
    private ItemTouchHelper itemTouchHelper;
    private FloatingActionButton fab;
    private int fabstate=0;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();


    }

    /*
    加载数据
     */
    public void initData(){
        mNotes=new ArrayList<Note>();
        deleteNoyes=new ArrayList<Note>();
        mDatabaseHelper=new MyDatabaseHelper(this,"NoteStore.db",null,1);
        mDatabase=mDatabaseHelper.getWritableDatabase();
        Cursor cursor=mDatabase.rawQuery("select * from Note",null);
        if(cursor.moveToFirst()){

            do{
                Note note=new Note();
                note.setmId(cursor.getInt(cursor.getColumnIndex("NoteId")));
                note.setmTitle(cursor.getString(cursor.getColumnIndex("NoteTitle")));
                note.setmText(cursor.getString(cursor.getColumnIndex("NoteText")));
                note.setmTime(cursor.getString(cursor.getColumnIndex("NoteTime")));
                mNotes.add(note);

            }while (cursor.moveToNext());
        }
        cursor=mDatabase.rawQuery("select * from DeleteNote",null);
        if(cursor.moveToFirst()){

            do{
                Note note=new Note();
                note.setmId(cursor.getInt(cursor.getColumnIndex("NoteId")));
                note.setmTitle(cursor.getString(cursor.getColumnIndex("NoteTitle")));
                note.setmText(cursor.getString(cursor.getColumnIndex("NoteText")));
                note.setmTime(cursor.getString(cursor.getColumnIndex("NoteTime")));
                deleteNoyes.add(note);

            }while (cursor.moveToNext());
        }
        cursor.close();

    }

    /*
    加载视图
     */
    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                switch (fabstate){
                    case 0:
                    MainActivity.this.startActivity(new Intent(MainActivity.this, NoteActivity.class));
                        break;
                    case 1:
                        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("清空回收站？");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteNoyes.clear();
                                dialog.dismiss();
                                Snackbar.make(fab, "清理完毕", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                        builder.create().show();
                        deleteApapter.notifyDataSetChanged();

                        break;
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecycleView= (RecyclerView) findViewById(R.id.recycleview);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(manager);
        adapter=new NotesAdapter(mNotes,this);
        deleteApapter=new NotesAdapter(deleteNoyes,this);
        ItemTouchHelper.Callback callback=new MyItemTouchHelperCallback(adapter);
        itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecycleView);
        initAdapter(adapter);
    }
    public void initAdapter(NotesAdapter initAdapter){

        mRecycleView.setAdapter(initAdapter);


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recycle_bins) {
            // Handle the camera action
            initAdapter(deleteApapter);
            toolbar.setTitle("回收站");
            fabstate=1;
            fab.setImageResource(R.drawable.ic_close_white_48dp);
            itemTouchHelper.attachToRecyclerView(null);
        }else if(id==R.id.nav_all_notes){
            initAdapter(adapter);
            itemTouchHelper.attachToRecyclerView(mRecycleView);
            toolbar.setTitle("简记");
            fabstate=0;
            fab.setImageResource(R.drawable.ic_fab_add);
        }else if (id == R.id.nav_share) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                mDatabase.execSQL("delete from Note");
                mDatabase.execSQL("delete from DeleteNote");
                ContentValues values=new ContentValues();
                for(Note note:mNotes){

                    values.put("NoteTitle",note.getmTitle());
                    values.put("NoteText",note.getmText());
                    values.put("NoteTime",note.getmTime());
                    mDatabase.insertOrThrow("Note", null, values);
                    values.clear();

                }
                for(Note note:deleteNoyes){
                    values.put("NoteTitle",note.getmTitle());
                    values.put("NoteText",note.getmText());
                    values.put("NoteTime",note.getmTime());
                    mDatabase.insertOrThrow("DeleteNote", null, values);
                    values.clear();
                }
                return null;
            }
        }.execute();

    }
}
