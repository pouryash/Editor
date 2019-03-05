package com.example.pourya.editor.MVP;

import android.content.Context;

import com.example.pourya.editor.Data.DAO;
import com.example.pourya.editor.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poorya on 2/10/2019.
 */

public class MainModel implements MVP_Main.ProvidedModelOps {

    private MVP_Main.RequiredPresenterOps mPresenter;
    private DAO mDAO;
    // Recycler data

    public List<Note> mNotes = new ArrayList<>();
    Context con;
//    Context c;

    public MainModel(MVP_Main.RequiredPresenterOps presenter , Context con) {
        this.mPresenter = presenter;
        mDAO = new DAO( con );
        this.con = con;
//         c = mPresenter.getAppContext();
    }

    public int getNotePosition(Note note) {
        for (int i=0; i<mNotes.size(); i++){
            if ( note.getId() == mNotes.get(i).getId())
                return i;
        }
        return -1;
    }

    @Override
    public int insertNote(Note note) {
        Note insertedNote = mDAO.insertNote(note);
        if ( insertedNote != null ) {
            loadData();
            return getNotePosition(insertedNote);
        }
        return 0;
    }

    @Override
    public List<Note> loadData() {
        mNotes = mDAO.SelectAllNotes();
        return mNotes;
    }

    @Override
    public Note getNote(int position) {
        return mNotes.get(position);
    }

    @Override
    public void deleteNote(Note note) {
         mDAO.deleteNote(note);
    }

    @Override
    public boolean deleteAll() {
        mDAO.deleteAll();
        return true;
    }

    @Override
    public int getNotesCount() {
        if ( loadData() != null )
            return loadData().size();
        return 0;
    }

    @Override
    public int updateNote(Note note) {
        return mDAO.updateNote(note);
    }
}
