package com.fortuna.keepnotes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fortuna.keepnotes.databinding.RecyclerViewBinding;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    ArrayList<Note> noteArrayList;

    public NoteAdapter(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewBinding recyclerViewBinding=RecyclerViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new NoteHolder(recyclerViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  NoteAdapter.NoteHolder holder, int position) {
        holder.binding.recyclerViewTextView.setText(noteArrayList.get(position).title);
        holder.binding.recyclerViewText.setText(noteArrayList.get(position).note);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),NoteActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("titleId",noteArrayList.get(position).id);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder{

        private RecyclerViewBinding binding;


        public NoteHolder(RecyclerViewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }

}
