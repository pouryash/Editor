package com.example.pourya.editor.MVP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pourya.editor.MainActivity;
import com.example.pourya.editor.Note;
import com.example.pourya.editor.R;
import com.example.pourya.editor.adapter;
import com.example.pourya.editor.add;

import java.lang.ref.WeakReference;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by poorya on 2/10/2019.
 */

public class MainPresenter implements MVP_Main.ProvidedPresenterOps,
        MVP_Main.RequiredPresenterOps {

    WeakReference<MVP_Main.RequiredViewOps> mView;
    Context con;
    MVP_Main.ProvidedModelOps mModel ;
    android.support.v7.app.AlertDialog.Builder builderAlert;

    public MainPresenter(MVP_Main.RequiredViewOps view, Context con) {
        mView = new WeakReference<>(view);
        this.con = con;
        mModel = new MainModel(this , con);
    }
    public void setModel(MVP_Main.ProvidedModelOps model) {
        mModel = model;
        // start to load data
        loadData();
    }

    @Override
    public void setView(MVP_Main.RequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public NotesViewHolder createViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adp, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void bindViewHolder(final NotesViewHolder holder, final int position, final Activity activity) {
        final Note note =mModel.getNote(position);
        holder.subject.setText(note.getSubject());
        holder.conetnt.setText(note.getContent());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builderAlert = new AlertDialog.Builder(con);
                    builderAlert.setTitle("Warning!!!").
                            setMessage("Do You Want Delete This Note?").
                            setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    mModel.deleteNote(note);
                                    getView().notifyItemRemoved(position);
                                    Toasty.success(con, " Note Succssesfully Deleted.", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setIcon(R.drawable.ic_warning).show();

                }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = mModel.getNote(position);
                Intent intent2 = new Intent(activity, add.class);
                intent2.putExtra("tv_subject", note.getSubject());
                intent2.putExtra("tv_content", note.getContent());
//                intent2.putExtra("tv_content", ((adapterVH) holder).conetnt.getText().toString());
                intent2.putExtra("position", position + "");
                activity.startActivityForResult(intent2, 2);
            }
        });
    }

    private MVP_Main.RequiredViewOps getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        try {
            new AsyncTask<Void, Void, List<Note>>() {
                @Override
                protected List<Note> doInBackground(Void... params) {
                    // Load data from Model
                    return mModel.loadData();
                }

                @Override
                protected void onPostExecute(List<Note> result) {
                    try {
                        if (result.size() == 0) // Loading error
                            getView().showToast(Toast.makeText(getActivityContext(), "Error loading data.", Toast.LENGTH_SHORT));
                        else // success
                            getView().notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getNotesCount() {
        return mModel.getNotesCount();
    }

    @Override
    public void clickNewNote(Activity activity) {
        Intent intent = new Intent(activity, add.class);
        activity.startActivityForResult(intent, MainActivity.REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2 && data != null) {
            String sub = data.getStringExtra("et_subject");
            String con = data.getStringExtra("et_content");
            String position = data.getStringExtra("position");
            int pos = 0;
            pos = Integer.parseInt(position);
//            notes notes = new notes();
//            notes.setSubject(message);
//            notes.setContent(message2);
//            adapter.updateNote(notes, pos);

            Note note = mModel.getNote(pos);
            note.setSubject(sub);
            note.setContent(con);
            mModel.updateNote(note);
            getView().notifyItemChanged(pos);
        }

        if (requestCode == MainActivity.REQUESTCODE && data != null) {
            String sub = data.getStringExtra("et_subject");
            String con = data.getStringExtra("et_content");
            Note note = new Note();
            note.setSubject(sub);
            note.setContent(con);
            int i = 0;
            note.setId(i);
            int id = mModel.insertNote(note);
//            this.notes.add(helper.getNote(id));
            getView().notifyItemInserted(0);
            i = +1;

        }

    }


    @Override
    public void clickDeleteAll(final Context context) {
         final List<Note> notes;
        notes = mModel.loadData();
        if (notes.isEmpty()) {
            Toasty.normal(context, "یادداشتی برای حذف کردن وجود ندارد", Toast.LENGTH_SHORT).show();
        } else {

            builderAlert = new AlertDialog.Builder(context);
            builderAlert.setTitle("Warning!!!").
                    setMessage("Do You Want Delete All Notes?").
                    setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public  void onClick(DialogInterface dialogInterface, int i) {
                            new  AsyncTask<Void, Void, Boolean>() {
                                @Override
                                protected Boolean doInBackground(Void... params) {
                                    mModel.deleteAll();
                                    notes.removeAll(notes);
                                    return true;
                                }

                                @Override
                                protected void onPostExecute(Boolean result) {
                                    try {
                                        if (result) {
                                            // Remove item from RecyclerView
                                            getView().notifyDataSetChanged();
                                            getView().showToast(Toasty.success(context, "All Note Succssesfully Deleted.", Toast.LENGTH_SHORT));
                                        } else {
                                            // Inform about error
                                            getView().showToast(Toast.makeText(context, "Error loading data.", Toast.LENGTH_SHORT));
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.execute();

//                            adapter.notifyDataSetChanged();

                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setIcon(R.drawable.ic_warning).show();

        }
    }


    @Override
    public Context getAppContext() {
        return null;
    }

    @Override
    public Context getActivityContext() {
        return null;
    }
}
