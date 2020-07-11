package com.icdominguez.mynotes;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private final NotesInteractionListener mListener;


    public MyNoteRecyclerViewAdapter(List<Note> items, NotesInteractionListener listener) {
        mValues = items;
        mListener = listener;
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

        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    mListener.addFavorite(holder.mItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitulo;
        public final TextView tvContent;
        public final ImageView ivFavorite;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = (TextView) view.findViewById(R.id.textViewTitle);
            tvContent = (TextView) view.findViewById(R.id.textViewContent);
            ivFavorite =  view.findViewById(R.id.imageViewFavorite);
        }
    }
}