//package com.example.t.note;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//
//import im.delight.android.ddp.Meteor;
//import im.delight.android.ddp.MeteorCallback;
//import im.delight.android.ddp.MeteorSingleton;
//import im.delight.android.ddp.ResultListener;
//import im.delight.android.ddp.SubscribeListener;
//import im.delight.android.ddp.db.Document;
//import im.delight.android.ddp.db.Query;
//
///**
// * Created by t on 11/21/17.
// */
//
//public class ContentActivity extends AppCompatActivity implements MeteorCallback {
//
//    private Context context;
//    private TextView yo;
//    private View view;
//    private Meteor meteor;
//    private static String TAG = "NotesTag";
//    public static ArrayList<NotesObject> list;
//
//    public static HashMap<String,String> checker;
//
//    private RecyclerView recyclerView;
//    private NotesAdapter notesAdapter;
//
//
//
//    public ContentActivity(Context context,View view){
//
//
//        this.view=view;
//        this.context=context;
//
//
//        list= new ArrayList<>();
//
//
//
//        recyclerView=view.findViewById(R.id.recycler_view);
//        notesAdapter=new NotesAdapter(getBaseContext(),list);
//        checker=new HashMap<>();
//
//        meteor= MeteorSingleton.getInstance();
//        meteor.addCallback(this);
//        meteor.connect();
//
//
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 2, true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(notesAdapter);
//
//
//        Log.d(TAG, "ContentActivity: "+ "creating activity");
//
//
//        final String[] params = {};
//
//        String subscriptionId = meteor.subscribe("notes", params, new SubscribeListener() {
//
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "Successfull subscription");
//
//            }
//
//            @Override
//            public void onError(String error, String reason, String details) {
//                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onDestroy() {
    //        meteor.disconnect();
    //        meteor.removeCallback(this);
//        // or
//        // mMeteor.removeCallbacks();
//
//        // ...
//
//        super.onDestroy();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        Log.d(TAG, "onCreate: ");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume: ");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(TAG, "onRestart: ");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart:");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause: ");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onConnect(boolean signedInAutomatically) {
//
//        list.clear();
//        checker.clear();
//
//        Log.d(TAG, "onConnect: "+signedInAutomatically);
//
//    }
//
//    @Override
//    public void onDisconnect() {
//        Log.d(TAG, "onDisconnect: ");
//
//
//    }
//
//    @Override
//    public void onException(Exception e) {
//
//    }
//
//    @Override
//    public void onDataAdded(String collectionName, String documentID, String newValuesJson) {
//        Log.d(TAG, "onDataAdded: "+ collectionName+ "......"+documentID+"......."   + newValuesJson);
//
//        if(!checker.containsKey(documentID)) {
//            checker.put(documentID,newValuesJson);
//            if (collectionName.equals("Notes")) {
//                try {
//                    JSONObject jsonObject = new JSONObject(newValuesJson);
//                    String title = jsonObject.getString("title");
//                    String content = jsonObject.getString("text");
//                    String createdBy = jsonObject.getString("createdBy");
//                    String id = documentID;
//
//
//                    Log.d(TAG, title + "   " + content);
//                    NotesObject note = new NotesObject(title, content, "", false);
//                    note.setId(id);
//
//                    list.add(note);
//                    notesAdapter.notifyDataSetChanged();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//
//            //Query query = meteor.getDatabase().getCollection(collectionName).whereEqual("createdBy", meteor.getUserId());
////        Document document = meteor.getDatabase().getCollection(collectionName).whereEqual("createdBy", meteor.getUserId()).findOne();
//
////        Log.d(TAG, "onDataAdded: query:::" +query);
//
//
//
//    }
//
//    @Override
//    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
//        Log.d(TAG, "onDataChanged: "+ collectionName+" ......... "+ documentID+ " .......... "+ updatedValuesJson+" ... "+ removedValuesJson );
//
//    }
//
//    @Override
//    public void onDataRemoved(String collectionName, String documentID) {
//        Log.d(TAG, "onDataRemoved: "+ collectionName+" ..... "+ documentID);
//
//
//    }
//
//
//    /**
//     * RecyclerView item decoration - give equal margin around grid item
//     */
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }
//
//}
