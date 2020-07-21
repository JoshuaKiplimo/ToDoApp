package com.example.todoapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


//Takes data from  model  into row in view
//Needs to extend base RecyclerView ADAPTER which is parametized by a ViewHolder
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    //public interface for on click so that Main Methos can capture the clicked item
    public interface OnClickListener{
        void OnItemClick(int position);
    }

    public interface OnLongClickListener{ //a public interface for long click listener - sos that Main method can capture this information
        void OnItemLongClicked(int position);
    }
    //MAIN ACTIVITY.JAVA WILL BE CONSTRUCTING ItemsAdapter - We add a constructor for that.
    List<String> items; //Define a variable that will be used throughout
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener ) {
        this.items = items; //make declared variable equal to variable passed in from the constructor
         this.longClickListener = longClickListener;
         this.clickListener = clickListener;
    }

    @NonNull
    @Override
    //RESPONSIBLE FOR CRATING EACH VIEW
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create a new view and wrap it inside a view holder
        //use layout inflator to inflate a view
        //Wrap it inside a view holder and return it

        View todoView =  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    //TAKING DATA FROM A CERTAIN LOCATION AND PUTTING IT INTO A POSITION
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab item from a position
        String item = items.get(position);
        holder.bind(item);
        //bind item into specified viewholder

    }

    @Override
    //NUMBER OF ITEMS AVAILABLE IN THE DATA - tells recycler view how many items are in the list
    public int getItemCount() {
        return items.size();
    }

    //container for view items, it extends the base, recycler view

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        //updates the view holder with data
        public void bind(String item) {
            tvItem.setText(item);

            //Attach a click listener to our text view to know which item was clicked  --- This is to know which item was sort clicked for editing.
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //This will notify main activity which item was clicked
                    //once clicked, we are going to invoke the method clickListener
                    clickListener.OnItemClick(getAdapterPosition());
                }
            });

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //android notifies that an item was long presses  and which item was long presses
                    longClickListener.OnItemLongClicked(getAdapterPosition());

                    return true; //call back that a user long clicked an item
                }
            });

        }
    }


}
