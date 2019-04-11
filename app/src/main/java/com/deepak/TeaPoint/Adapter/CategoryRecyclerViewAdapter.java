package com.deepak.TeaPoint.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.deepak.TeaPoint.Activities.MainActivity;
import com.deepak.TeaPoint.Models.Hero;
import com.deepak.TeaPoint.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<Hero> heroList;

    public CategoryRecyclerViewAdapter(Context mContext, List<Hero> heroList) {
        this.mContext = mContext;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_card,viewGroup,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder,final int position) {
        holder.name.setText(heroList.get(position).getName());
        holder.createdby.setText(heroList.get(position).getFirstappearance());

        //load album cover using picasso
        Picasso.with(mContext)
                .load(heroList.get(position).getImageurl())
                .placeholder(R.drawable.load)
                .into(holder.thumbnail);

        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addtocart.setVisibility(View.GONE);
                holder.elgbtn.setNumber("1");
                holder.elgbtn.setVisibility(View.VISIBLE);
            }
        });

        holder.elgbtn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if(newValue==0){
                    holder.elgbtn.setVisibility(View.GONE);
                    holder.addtocart.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = position;
                if (pos != RecyclerView.NO_POSITION){
                    Hero clickedDataItem = heroList.get(pos);
                    Intent intent = new Intent(mContext, MainActivity.class);
                    //intent.putExtra("name", heroList.get(pos).getName());
                    //intent.putExtra("numOfSongs", albumList.get(pos).getNumOfSongs());
                    //intent.putExtra("thumbnail", albumList.get(pos).getThumbnail());
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView createdby,addtocart;
        ImageView thumbnail;
        ElegantNumberButton elgbtn;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hname);
            createdby = itemView.findViewById(R.id.createdby);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            addtocart = itemView.findViewById(R.id.addtocart);
            elgbtn = itemView.findViewById(R.id.elgbutton);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Hero clickedDataItem = heroList.get(pos);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("name", heroList.get(pos).getName());
                        //intent.putExtra("numOfSongs", albumList.get(pos).getNumOfSongs());
                        //intent.putExtra("thumbnail", albumList.get(pos).getThumbnail());
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }
}
