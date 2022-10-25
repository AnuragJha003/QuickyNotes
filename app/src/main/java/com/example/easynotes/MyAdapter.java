package com.example.easynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    RealmResults<Note> notesList;

    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note=notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());
        String ft= DateFormat.getDateTimeInstance().format(note.createdtime);
        holder.timeOutput.setText(ft);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu=new PopupMenu(context,view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("DELETE")){
                            Realm realm=Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "NOTE DELETED", Toast.LENGTH_SHORT).show();
                        }//DELETION KE LIYE THIS ENTIRE FALSET HAS BEEN CREATED...
                        return  true;
                    }
                });
                menu.show();


                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput=itemView.findViewById(R.id.titleoutput);
            descriptionOutput=itemView.findViewById(R.id.descriptionoutput);
            timeOutput=itemView.findViewById(R.id.timeoutput);

        }
    }
}
