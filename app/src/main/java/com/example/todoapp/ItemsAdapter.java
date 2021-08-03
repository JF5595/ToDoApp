package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Container to provide easy access to view that represents each row of the list
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickListener{
        void onItemClicked(int position);
    }

    public interface OnLongClockListener{
        void onItemLongClicked(int position);
    }


    List<String> items;
    OnLongClockListener longClockListener;
    OnClickListener clickListener;

    public ItemsAdapter(List<String> items, OnLongClockListener longClockListener, OnClickListener clickListener) {
        this.items = items;
        this.longClockListener = longClockListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        //wrap it inside a View Holder and return it
        return new ViewHolder(todoView);
    }

    //binds data to view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //grab the item at position
        String item =  items.get(position);

        //bind item into view holder
        holder.bind(item); 
    }

    //tells RV # of items in list
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update view inside of holder with item data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //notify listener which position long pressed
                    longClockListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
