package com.icdominguez.mynotes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icdominguez.mynotes.db.entity.NoteEntity;
import com.icdominguez.mynotes.R;
import com.icdominguez.mynotes.viewmodel.NewNoteDialogViewModel;

import java.util.List;

public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private List<NoteEntity> mValues;
    private final Context mListener;
    private NewNoteDialogViewModel viewModel;


    public MyNoteRecyclerViewAdapter(List<NoteEntity> items, Context ctx) {
        mValues = items;
        mListener = ctx;
        viewModel = ViewModelProviders.of((AppCompatActivity)ctx).get(NewNoteDialogViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    //  method invoked once for each item in the note list
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitulo.setText(mValues.get(position).getTitle());
        holder.tvContent.setText(mValues.get(position).getContent());

        if (holder.mItem.isFavorite()) {
            holder.ivFavorite.setImageResource(R.drawable.ic_baseline_star_24);
        }

        holder.ivFavorite.setOnClickListener(view -> {
            if(holder.mItem.isFavorite()) {
                holder.mItem.setFavorite(false);
                holder.ivFavorite.setImageResource(R.drawable.ic_baseline_star_border_24);
            } else {
                holder.mItem.setFavorite(true);
                holder.ivFavorite.setImageResource(R.drawable.ic_baseline_star_24);
            }
            viewModel.updateNote(holder.mItem);
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNewNotes(List<NoteEntity> newNotes) {
        this.mValues = newNotes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitulo;
        public final TextView tvContent;
        public final ImageView ivFavorite;
        public NoteEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = (TextView) view.findViewById(R.id.textViewTitle);
            tvContent = (TextView) view.findViewById(R.id.textViewContent);
            ivFavorite =  view.findViewById(R.id.imageViewFavorite);
        }
    }
}