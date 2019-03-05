package com.example.pourya.editor.MVP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pourya.editor.Note;

import java.util.List;

/**
 * Created by poorya on 2/9/2019.
 */

public interface MVP_Main {


    interface RequiredViewOps{

        Context getAppContext1();
        Context getActivityContext1();
        void showToast(Toast toast);
        void notifyItemChanged(int position);
        void notifyDataSetChanged();
        void notifyItemInserted(int position);
        void notifyItemRemoved(int position);
    }

    interface ProvidedPresenterOps{

        void setView(RequiredViewOps view);
        NotesViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(NotesViewHolder holder, int position, Activity activity);
        int getNotesCount();
        void clickNewNote(Activity activity);
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void clickDeleteAll(Context context);
    }

    interface RequiredPresenterOps {
        Context getAppContext();
        Context getActivityContext();
    }

    interface ProvidedModelOps {
        int insertNote(Note note);
        List<Note> loadData();
        Note getNote(int position);
        void deleteNote(Note note);
        boolean deleteAll();
        int getNotesCount();
        int updateNote(Note note);
    }


}
