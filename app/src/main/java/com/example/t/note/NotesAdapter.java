package com.example.t.note;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.ResultListener;

/**
 * Created by t on 11/29/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>{
    private Context mContext;
    private List<NotesObject> noteList;
    private String TAG="NotesAdapter";
    private Meteor meteor;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content,date;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView=view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            content = (TextView) view.findViewById(R.id.content);
        }
    }


    public NotesAdapter(Context mContext, List<NotesObject> noteList, Meteor meteor) {
        this.mContext = mContext;
        this.noteList = noteList;
        this.meteor=meteor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NotesObject note = noteList.get(position);
        final String id=note.getId();
        final String title=note.getTitle();
        holder.title.setText(title);
        final String content=note.getContent();
        holder.content.setText(content);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+ "working");

                Intent updateNote=new Intent(view.getContext(),UpdateNote.class);
                updateNote.putExtra("title",title);
                updateNote.putExtra("content",content);
                updateNote.putExtra("id",id);
                view.getContext().startActivity(updateNote);

                

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onLongClick: "+ "working");

                final Map<String, Object> values = new HashMap<String, Object>();
                values.put("id",id);

                final Object[] queryParams = {values};
                Snackbar.make(view, "Delete this note?", Snackbar.LENGTH_LONG)
                       .setAction("Delete", new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
//                               Log.d(TAG, "onClick: "+ meteor.isConnected()+meteor.get);
                               if(meteor.isConnected()) {
                                   meteor.call("notes.delete", queryParams, new ResultListener() {
                                       @Override
                                       public void onSuccess(String result) {
                                           Log.d(TAG, "onSuccess: " + "Note deleted");
                                       }

                                       @Override
                                       public void onError(String error, String reason, String details) {

                                       }
                                   });
                               }
                           }
                       }).show();
                return true;
            }
        });


    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
