package eizougraphic.sintret.finsrecipe.task;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eizougraphic.sintret.finsrecipe.R;
import eizougraphic.sintret.finsrecipe.sql.Order;
import eizougraphic.sintret.finsrecipe.sql.Person;


public class NoviewAdapter extends RecyclerView.Adapter<NoviewAdapter.ViewHolder> {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private String[] mDataset;
        CardView cv;
        TextView name;
        TextView address;
        TextView phone;
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
        }
    }

    List<Person> persons;

    public NoviewAdapter(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_task, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(persons.get(position).name);
        holder.address.setText(persons.get(position).address);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return persons.size();
    }
}