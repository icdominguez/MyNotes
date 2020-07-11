package com.icdominguez.mynotes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class NoteFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private NotesInteractionListener mListener;

    private List<Note> noteList;
    private MyNoteRecyclerViewAdapter adapterNotas;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NoteFragment newInstance(int columnCount) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if(view.getId() == R.id.listPortrait) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                // Extract screen size
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                int numberColumns = (int) dpWidth / 180;
                
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberColumns, StaggeredGridLayoutManager.VERTICAL));
            }

            noteList = new ArrayList<>();
            noteList.add(new Note("Lista de la compra", "comprar pan tostado", true, android.R.color.holo_green_light));
            noteList.add(new Note("Tareas", "Terminar proyecto android y subir todo el código", false, android.R.color.holo_blue_light));
            noteList.add(new Note("Recordar", "Recordar siempre donde has dejado el coche una vez usado, para no tener que preguntar después", true, android.R.color.holo_green_light));
            noteList.add(new Note("Lista de la compra", "comprar pan tostado", true, android.R.color.holo_green_light));
            noteList.add(new Note("Tareas", "Terminar proyecto android y subir todo el código", false, android.R.color.holo_blue_light));

            adapterNotas = new MyNoteRecyclerViewAdapter(noteList, mListener);
            recyclerView.setAdapter(adapterNotas);
        }
        return view;
    }

    // first method to be launched when a fragment is instantiated
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof NotesInteractionListener) {
            mListener = (NotesInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NotesInteractionListener");
        }
    }
}