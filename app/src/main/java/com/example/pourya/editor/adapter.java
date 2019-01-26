package com.example.pourya.editor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private Context context;
    public  onNoteItemClick onNoteItemClick;

    public adapter(List<Note> model, Context context, adapter.onNoteItemClick onNoteItemClick) {
        notes = model;
        this.context = context;
        this.onNoteItemClick = onNoteItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adp, parent, false);
        return new adapterVH(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((adapterVH) holder).bindModel(notes.get(position));
        ((adapterVH) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Note.remove(new Note().getId());
//                notifyItemRemoved(position);
                Note m = notes.get(position);
               onNoteItemClick.onDelete(m,position);

            }
        });
        ((adapterVH) holder).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onNoteItemClick.onEdite(notes.get(position),position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class adapterVH extends RecyclerView.ViewHolder {

        private TextView subject;
        private TextView conetnt;
        private ImageView delete;
        private ImageView edit;

        public adapterVH(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject_adp);
            conetnt = itemView.findViewById(R.id.content_adp);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }

        public void bindModel(Note model) {
            subject.setText(model.getSubject());
            conetnt.setText(model.getContent());
        }
    }

//    public void addNote(Note model) {
//        notes.add(model);
//        notifyItemInserted(notes.size() - 1);
//    }
//
//    public void updateNote(Note model, int position) {
//        notes.set(position, model);
//        notifyItemChanged(position);
//    }
//
//    public void removeall() {
//        if (notes.isEmpty()) {
//            Toast.makeText(context, "یادداشتی برا حذف کردن وجود ندارد", Toast.LENGTH_SHORT).show();
//
////            Snackbar.make(((Activity)context).findViewById(R.id.Main), "یادداشتی برا حذف کردن وجود ندارد ", Snackbar.LENGTH_LONG).show();
//
//        } else {
//            notes.removeAll(Note);
//            notifyDataSetChanged();
//        }
//
//    }
//
    interface onNoteItemClick{
        void onDelete(Note note, int position);
        void onEdite(Note note , int position);
    }


}
