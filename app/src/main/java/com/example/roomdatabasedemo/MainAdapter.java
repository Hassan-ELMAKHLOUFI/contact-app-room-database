package com.example.roomdatabasedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // Initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;
    int i;
    //Create constructor
    public MainAdapter(MainActivity context, List<MainData> dataList)
    {
        i=1;
       this.context= (Activity) context;
       this.dataList=dataList;
       notifyDataSetChanged();
    }




    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Initialize view
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, int position) {

        // Initialize main data
        MainData data=dataList.get(position);

        // Initialize database
        database=RoomDB.getInstance(context);

        i++;
        int photos[]={R.drawable.programmer,R.drawable.man,R.drawable.man1};
        // Set text on text view
        holder.nomText.setText(data.getNom());
        holder.emailText.setText(data.getEmail());
        holder.numText.setText(data.getPhone());
        holder.jobText.setText(data.getJob());
        Random rand = null;
        int rnd = (int)(Math.random()*photos.length);

        Picasso.get().load(photos[rnd]).into(holder.img);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initialize main data
                MainData d=dataList.get(holder.getAdapterPosition());

                // Get id
                final int sID=d.getID();

                // Get text
                String sText=d.getNom();
                String sphone=d.getPhone();
                String semail=d.getEmail();
                String sjob=d.getJob();

                // create dialog
                final Dialog dialog=new Dialog(context);

                // set content view
                dialog.setContentView(R.layout.dialog_update);

                //Initialize width
                int width= WindowManager.LayoutParams.MATCH_PARENT;

                //Initialize height
                int height=WindowManager.LayoutParams.WRAP_CONTENT;

                //Set layout
                dialog.getWindow().setLayout(width,height);

                //show dialog
                dialog.show();

                //Initialize and assign variable
                final EditText nom = dialog.findViewById(R.id.nomAdd);
                final EditText phone = dialog.findViewById(R.id.numero);
                final EditText email = dialog.findViewById(R.id.email);
                final EditText job = dialog.findViewById(R.id.job);
                Button btUpdate=dialog.findViewById(R.id.bt_update);

                // Set text on edit text
                nom.setText(sText);
                phone.setText(sphone);
                email.setText(semail);
                job.setText(sjob);


                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss Dialog
                        dialog.dismiss();

                        //Get Updated text from edit text
                        String Snom=nom.getText().toString().trim();
                        String Sphone=phone.getText().toString().trim();
                        String Semail=email.getText().toString().trim();
                        String Sjob=job.getText().toString().trim();

                        // Update text in database
                        database.mainDao().update(sID,Snom,Sjob,Sphone,Semail);

                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();

                    }
                });



            }
        });

        holder.itemView.setOnLongClickListener((new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                MainData d =dataList.get(holder.getAdapterPosition());
                database.mainDao().delete(d);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
                return true;
            }
        }));
        holder.call.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+holder.numText.getText().toString()));
            System.out.println("888888888"+holder.numText.getText().toString());
            context.startActivity(callIntent);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize variable
        TextView nomText,jobText,emailText,numText;
        ImageButton call ;
        CircleImageView  img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable

            nomText=itemView.findViewById(R.id.nomText);
            emailText=itemView.findViewById(R.id.emailText);
            jobText=itemView.findViewById(R.id.jobText);
            numText=itemView.findViewById(R.id.numeroText);
            img=itemView.findViewById(R.id.photo);
            call=itemView.findViewById(R.id.call);


        }
    }
}
