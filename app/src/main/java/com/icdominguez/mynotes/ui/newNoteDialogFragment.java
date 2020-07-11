package com.icdominguez.mynotes.ui;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.icdominguez.mynotes.viewmodel.NewNoteDialogViewModel;
import com.icdominguez.mynotes.R;
import com.icdominguez.mynotes.db.entity.NoteEntity;

public class newNoteDialogFragment extends DialogFragment {

    public static newNoteDialogFragment newInstance() {
        return new newNoteDialogFragment();
    }



    private View view;

    private EditText etTitle;
    private EditText etContent;
    private RadioGroup rgColor;
    private Switch swFavoriteNote;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(R.string.newNoteDialog_title);
            builder.setMessage(R.string.newNoteDialog_message)
                    .setPositiveButton(R.string.newNoteDialog_save, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String title = etTitle.getText().toString();
                            String content = etContent.getText().toString();
                            String color = "azul";
                            switch(rgColor.getCheckedRadioButtonId()) {
                                case R.id.radioButtonRed:
                                    color = getString(R.string.newNoteFragment_colorRed);
                                    break;
                            }
                            switch(rgColor.getCheckedRadioButtonId()) {
                                case R.id.radioButtonGreen:
                                    color = getString(R.string.newNoteFragment_colorGreen);
                                    break;
                            }
                            switch(rgColor.getCheckedRadioButtonId()) {
                                case R.id.radioButtonBlue:
                                    color = getString(R.string.newNoteFragment_colorBlue);
                                    break;
                            }

                            boolean isFavorite = swFavoriteNote.isChecked();

                            // Communicate the new data to the view model
                            NewNoteDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NewNoteDialogViewModel.class);
                            mViewModel.insertNote(new NoteEntity(title,content,isFavorite,color));
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton(R.string.newNoteDialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            // load fragment in the dialog
            LayoutInflater inflater = getActivity().getLayoutInflater();
            view = inflater.inflate(R.layout.new_note_dialog_fragment, null);

            etTitle = view.findViewById(R.id.editTextTitle);
            etContent = view.findViewById(R.id.editTextContent);
            rgColor = view.findViewById(R.id.radioGroupColors);
            swFavoriteNote = view.findViewById(R.id.switchFavoriteNote);

            builder.setView(view);

            // Create the AlertDialog object and return it
            return builder.create();
        }
}