package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class AddLessonDialog extends AppCompatDialogFragment {

    private EditText aula;
    private EditText inizioLezione;
    private EditText fineLezione;
    private RadioButton tipoLezione;
    private ExampleDialogListener listener;

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.add_lesson_dialog_layout, null);

        TextView title = new TextView(getContext());
        // You Can Customise your Title here
        title.setText("Lezione");
        title.setPadding(10, 20, 10, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextSize(30);

        builder.setView(view)
                .setCustomTitle(title)
                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String aulaString = "";
                        String inizioLezioneString = "";
                        String fineLezioneString = "";
                        String tipoLezioneString = "";
                        listener.applyTexts(aulaString, inizioLezioneString, fineLezioneString, tipoLezioneString);
                    }
                })
                .setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String aulaString = aula.getText().toString();
                        String inizioLezioneString = inizioLezione.getText().toString();
                        String fineLezioneString = fineLezione.getText().toString();
                        String tipoLezioneString = tipoLezione.getText().toString();
                        listener.applyTexts(aulaString, inizioLezioneString, fineLezioneString, tipoLezioneString);
                    }
                });

        aula = view.findViewById(R.id.aula_AddLessonDialogLayout);
        inizioLezione = view.findViewById(R.id.inizioLezione_AddLessonDialogLayout);
        fineLezione = view.findViewById(R.id.fineLezione_AddLessonDialogLayout);
        tipoLezione = view.findViewById(R.id.radioButton_AddLessonDialogLayout);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String aula, String inizioLezione, String fineLezione, String tipoLezione);
    }

}
