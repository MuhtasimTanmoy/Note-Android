package com.example.t.note;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;
import im.delight.android.ddp.MeteorSingleton;
import im.delight.android.ddp.SubscribeListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MeteorCallback {

    private DbHelper dbHelper ;
    private TextView headerName;
    private TextView headerContactNo;

//    private ContentActivity content;

    private String TAG="MainActivity";

    private Meteor meteor;

    public static ArrayList<NotesObject> list;

    public static HashMap<String,NotesObject> checker;

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper=new DbHelper(this);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newNote=new Intent(getApplicationContext(),NewNote.class);
                startActivity(newNote);


            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        headerName= (TextView) headerView.findViewById(R.id.headerName);
        headerContactNo= (TextView) headerView.findViewById(R.id.headerContact);


        updatenavHeader();


//        View contentView=findViewById(R.id.notesHolder);
//
//
//
//        content=new ContentActivity(this,contentView);

        list= new ArrayList<>();

        meteor= MeteorSingleton.getInstance();
        meteor.addCallback(this);
        meteor.connect();

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        notesAdapter=new NotesAdapter(getBaseContext(),list,meteor);
        checker=new HashMap<>();





        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, 2, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);


        Log.d(TAG, "ContentActivity: "+ "creating activity");


        final String[] params = {};

        String subscriptionId = meteor.subscribe("notes", params, new SubscribeListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Successfull subscription");

            }

            @Override
            public void onError(String error, String reason, String details) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        checker.clear();

        meteor.connect();
        meteor.addCallback(this);

        Log.d(TAG, "onResume: " + meteor.isConnected());


        final String[] params = {};

        String subscriptionId = meteor.subscribe("notes", params, new SubscribeListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Successfull subscription");

            }

            @Override
            public void onError(String error, String reason, String details) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });

        Log.d(TAG, "onResume: " + subscriptionId);
        Log.d(TAG, "onResume: ");

        notesAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        meteor.disconnect();
        meteor.removeCallback(this);
        meteor.unsubscribe("notes");
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


    //////////////////////////////////////////////////////////UpperOption//////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            dbHelper.updateStatus(1,"false");
            dbHelper.deleteUserDetails(1);
            MeteorSingleton.getInstance().logout();
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    ///////////////////////////////////////////////////////NavigationOption///////////////////////////////

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.note) {
            // Handle the camera action

        } else if (id == R.id.help) {


        } else if (id == R.id.logout) {
            dbHelper.updateStatus(1,"false");
            dbHelper.deleteUserDetails(1);
            MeteorSingleton.getInstance().logout();
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /////////////////////////headerUpdate/////////////////////////////////
    void updatenavHeader(){
        int row = dbHelper.numberOfRows("users");
        Log.d("updateHeader: ",row+"  rows are seen here ");

        if (row == 1) {
            Cursor res = dbHelper.getUserDetails(1);
            res.moveToFirst();
            String name = res.getString(res.getColumnIndex("name"));
            String contactNo = res.getString(res.getColumnIndex("contactNo"));

            Log.d("updateHeader: ",row+"  rows are seen here "+ name + contactNo);


            headerName.setText("Name: "+name);
            headerContactNo.setText("Contact No: "+contactNo);

        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }











    /////////////////////////////////



    @Override
    public void onConnect(boolean signedInAutomatically) {

//        list.clear();
//        checker.clear();

        Log.d(TAG, "onConnect: "+signedInAutomatically);

    }

    @Override
    public void onDisconnect() {
        Log.d(TAG, "onDisconnect: ");


    }

    @Override
    public void onException(Exception e) {

    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String newValuesJson) {
        Log.d(TAG, "onDataAdded: "+ collectionName+ "......"+documentID+"......."   + newValuesJson);

        if(!checker.containsKey(documentID)) {
            if (collectionName.equals("Notes")) {
                try {
                    JSONObject jsonObject = new JSONObject(newValuesJson);
                    String title = jsonObject.getString("title");
                    String content = jsonObject.getString("text");
                    String createdBy = jsonObject.getString("createdBy");
                    String id = documentID;


                    Log.d(TAG, title + "   " + content);
                    NotesObject note = new NotesObject(title, content, "", false);
                    note.setId(id);

                    checker.put(documentID,note);


                    list.add(note);
                    notesAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


        //Query query = meteor.getDatabase().getCollection(collectionName).whereEqual("createdBy", meteor.getUserId());
//        Document document = meteor.getDatabase().getCollection(collectionName).whereEqual("createdBy", meteor.getUserId()).findOne();

//        Log.d(TAG, "onDataAdded: query:::" +query);



    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        Log.d(TAG, "onDataChanged: "+ collectionName+" ......... "+ documentID+ " .......... "+ updatedValuesJson+" ... "+ removedValuesJson );
        list.remove(checker.get(documentID));
        NotesObject note=checker.get(documentID);

        try {
            JSONObject jsonObject = new JSONObject(updatedValuesJson);
            if(jsonObject.has("title")){
                note.setTitle(jsonObject.getString("title"));
            }
            if(jsonObject.has("text")){
                note.setContent(jsonObject.getString("text"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        checker.put(documentID,note);
        list.add(checker.get(documentID));

        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        Log.d(TAG, "onDataRemoved: "+ collectionName+" ..... "+ documentID);
        list.remove(checker.get(documentID));
        notesAdapter.notifyDataSetChanged();


    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
