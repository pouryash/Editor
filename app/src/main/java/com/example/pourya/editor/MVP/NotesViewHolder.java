package com.example.pourya.editor.MVP;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pourya.editor.Note;
import com.example.pourya.editor.R;

/**
 * Created by poorya on 2/9/2019.
 */

public class NotesViewHolder extends RecyclerView.ViewHolder {

    public TextView subject;
    public TextView conetnt;
    public ImageView delete;
    public ImageView edit;

    public NotesViewHolder(View itemView) {
        super(itemView);
        subject = itemView.findViewById(R.id.subject_adp);
        conetnt = itemView.findViewById(R.id.content_adp);
        delete = itemView.findViewById(R.id.delete);
        edit = itemView.findViewById(R.id.edit);
    }


}
