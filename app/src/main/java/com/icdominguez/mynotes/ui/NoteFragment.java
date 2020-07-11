package com.icdominguez.mynotes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.icdominguez.mynotes.viewmodel.NewNoteDialogViewModel;
import com.icdominguez.mynotes.R;
import com.icdominguez.mynotes.db.entity.NoteEntity;

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

    private List<NoteEntity> noteEntityList;
    private MyNoteRecyclerViewAdapter adapterNotas;

    private NewNoteDialogViewModel noteViewModel;



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

        // We indicate that the fragment has its own options menu
        setHasOptionsMenu(true);
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

            noteEntityList = new ArrayList<>();

            adapterNotas = new MyNoteRecyclerViewAdapter(noteEntityList, getActivity());
            recyclerView.setAdapter(adapterNotas);

            launchViewModel();
        }
        return view;
    }

    private void launchViewModel() {
        noteViewModel = ViewModelProviders.of(getActivity())
                .get(NewNoteDialogViewModel.class);

        noteViewModel.getAllNotes().observe(getActivity(), new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapterNotas.setNewNotes(noteEntities);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_note_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_note:
                showDialogNewNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogNewNote() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        newNoteDialogFragment newNoteDialog = new newNoteDialogFragment();
        newNoteDialog.show(fm, "NewNowDialogFragment");
    }
}