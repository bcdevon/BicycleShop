package com.example.bicycleshop.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicycleshop.R;
import com.example.bicycleshop.entities.Part;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder> {
    private List<Part> mParts;
    private final Context context;
    private final LayoutInflater mInflater;

    class PartViewHolder extends RecyclerView.ViewHolder {

        private final TextView partItemView;
        private final TextView partItemView2;

        public PartViewHolder(View itemView) {
            super(itemView);
            partItemView = itemView.findViewById(R.id.textView3);
            partItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Part current = mParts.get(position);
                    Intent intent = new Intent(context, PartDetails.class);
                    intent.putExtra("id", current.getPartID());
                    intent.putExtra("name", current.getPartName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("prodID", current.getProductID());
                    context.startActivity(intent);
                }
            });
        }
    }
    public PartAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public PartAdapter.PartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.part_list_item,parent,false);
        return new PartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartViewHolder holder, int position) {
        if(mParts!=null){
            Part current=mParts.get(position);
            String name=current.getPartName();
            int prodID= current.getProductID();
            holder.partItemView.setText(name);
            Log.d("PartAdapter", "Part Name: " + name + ", Product ID: " + prodID);
            holder.partItemView2.setText(Integer.toString(prodID));
        }
        else {
            holder.partItemView.setText("No part name");
            holder.partItemView2.setText("No product id");
        }
    }
    public void setParts(List<Part> parts){
        mParts=parts;
        notifyDataSetChanged();
    }
    public int getItemCount() {
        if(mParts!=null) return mParts.size();
        else return 0;
    }

}