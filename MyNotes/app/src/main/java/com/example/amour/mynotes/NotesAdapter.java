package com.example.amour.mynotes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Amour on 2016/4/7.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> implements OnMoveOrSwipeListener{


    private List<Note> notes;
    private Context context;
    private LayoutInflater inflater;


    public NotesAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_note,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, final int position) {

        TextView tvNoteTime= (TextView) holder.v.findViewById(R.id.noteTime);
        TextView tvNoteText= (TextView) holder.v.findViewById(R.id.noteText);
        TextView tvNoteTitle= (TextView) holder.v.findViewById(R.id.noteTitle);
        tvNoteTitle.setText(notes.get(position).getmTitle());
        tvNoteTime.setText(notes.get(position).getmTime());
        tvNoteText.setText(notes.get(position).getmText());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NoteActivity.class);
                intent.putExtra("NotePosition",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public boolean onItemMove(int fromPosition,int toPosition) {
//        if(notes==MainActivity.mNotes){
            Collections.swap(notes,fromPosition,toPosition);


        notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
            if(notes==MainActivity.mNotes){
                MainActivity.deleteNoyes.add(MainActivity.mNotes.get(position));
                MainActivity.mNotes.remove(position);}
//            }else if(notes==MainActivity.deleteNoyes){
//                MainActivity.deleteNoyes.remove(position);
//            }

        notifyItemRemoved(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        private View v;
        public NoteViewHolder(View itemView) {
            super(itemView);
            this.v=itemView;
        }
    }
}
