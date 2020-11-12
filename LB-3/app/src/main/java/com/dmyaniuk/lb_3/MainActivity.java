package com.dmyaniuk.lb_3;

import android.os.Environment;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private EditText noteEditText;
    private CalendarView calendarView;
    private ArrayList<Note> notes;
    private Calendar calendar;
    private RadioGroup radioGroup;
    private String selectedStorageType;
    private FileHandler fileHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeElements();
        this.setCalendarViewEventHandler();
    }

    public void saveNote(View v) {
        this.updateFileWithNotes(false);
    }

    public void removeNote(View v) {
        this.updateFileWithNotes(true);
    }

    public void loadNotes(View v) {
        this.getStorageType();
        this.notes = this.fileHandler.readFromFile(this.selectedStorageType);
    }

    private void initializeElements() {
        this.noteEditText = findViewById(R.id.note);
        this.calendarView = findViewById(R.id.datepicker);
        this.radioGroup = findViewById(R.id.storageType);
        RadioButton internalRadio = findViewById(R.id.internalRadio);
        internalRadio.setChecked(true);

        this.selectedStorageType = Constants.InternalStorage;
        this.notes = new ArrayList<Note>();
        this.fileHandler = new FileHandler(this, getExternalFilePath());
    }

    private void setCalendarViewEventHandler() {
        this.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                calendar = new GregorianCalendar(year, month, day);
                noteEditText.setText("");
                Note existingNote = findNoteByDate();

                if (notes != null && existingNote != null) {
                    noteEditText.setText(existingNote.getNote());
                }
            }
        });
    }

    private Note findNoteByDate() {
        for (Note note : notes) {
            if (note.getDate().compareTo(calendar.getTime()) == 0) {
                return note;
            }
        }

        return null;
    }

    private void getStorageType() {
        int selectedIndex = this.radioGroup.getCheckedRadioButtonId();
        if (selectedIndex == -1) {
            ToastHandler.showToast(this, "Select type of storage!");
            return;
        }

        switch (selectedIndex) {
            case R.id.internalRadio:
                selectedStorageType = Constants.InternalStorage;
                break;
            case R.id.externalRadio:
                selectedStorageType = Constants.ExternalStorage;
                break;
        }
    }

    private void updateFileWithNotes(boolean isDeleting) {
        if (isDeleting) {
            this.notes.remove(findNoteByDate());
            noteEditText.setText("");
        } else {
            this.notes.add(new Note(
                    noteEditText.getText().toString(),
                    calendar.getTime()
            ));
        }

        int selectedIndex = this.radioGroup.getCheckedRadioButtonId();
        if (selectedIndex == -1) {
            ToastHandler.showToast(this, "Select type of storage!");
            return;
        }

        this.getStorageType();

        this.fileHandler.saveToFile(this.notes, this.selectedStorageType);
        ToastHandler.showToast(this, isDeleting ? "Note has been deleted!" : "Note has been saved!");
    }

    private File getExternalFilePath() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), Constants.FileName);
    }
}