package eizougraphic.sintret.finsrecipe;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eizougraphic.sintret.finsrecipe.sql.Order;
import eizougraphic.sintret.finsrecipe.sql.Person;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    List<Order> orders;

    public TaskAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_task, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(orders.get(position).customer);
        holder.address.setText(orders.get(position).address);
        String thePhone = orders.get(position).phone;
        String firstLetter = "";
        firstLetter = thePhone.substring(0, 1);

        String phone = "";
        if(firstLetter.equals("0")){
            phone=thePhone;
        } else {
            phone="+"+thePhone;
        }
        holder.phone.setText(phone);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemActivity.class);
                intent.putExtra("id", orders.get(position).id);
                intent.putExtra("customer", orders.get(position).customer);
                intent.putExtra("address", orders.get(position).address);
                intent.putExtra("address2", orders.get(position).address2);
                intent.putExtra("phone", orders.get(position).phone);
                intent.putExtra("phone2", orders.get(position).phone2);
                intent.putExtra("kodepos", orders.get(position).kodepos);
                intent.putExtra("provinsi", orders.get(position).provinsi);
                intent.putExtra("kabupaten", orders.get(position).kabupaten);
                intent.putExtra("kecamatan", orders.get(position).kecamatan);
                intent.putExtra("subTotal", orders.get(position).subTotal);
                intent.putExtra("discount", orders.get(position).discount);
                intent.putExtra("total", orders.get(position).total);
                intent.putExtra("shippingFee", orders.get(position).shippingFee);
                intent.putExtra("paymentMethod", orders.get(position).paymentMethod);
                intent.putExtra("deliveryDate", orders.get(position).deliveryDate);
                intent.putExtra("deliveryHour", orders.get(position).deliveryHour);
                intent.putExtra("deliveryTime", orders.get(position).deliveryTime);
                intent.putExtra("remark", orders.get(position).remark);
                intent.putExtra("items", orders.get(position).items);


                v.getContext().startActivity(intent);
                //startActivity(intent);
                //return false;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orders.size();
    }
}
