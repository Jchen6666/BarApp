package com.example.jerrychen.barapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;


/**
 * A simple subclass of {@link ArrayAdapter<>} class that interprets the data passed for a listView
 */
public class OrdersStaffAdapter extends ArrayAdapter<Order> {
    public OrdersStaffAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final Order order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.current_orders_layout_staff, parent, false);
        }
        // Lookup view for data population
        TextView tvOrderNumber=(TextView)convertView.findViewById(R.id.textViewNumber);
        TextView tvOrderTime=(TextView)convertView.findViewById(R.id.textViewTime);
        TextView tvItemsQuantity=(TextView)convertView.findViewById(R.id.textViewItemsQuantity);
        int itemsQuantity=0;
        for (Map.Entry<String, ArrayList<String>> pair : order.getOrderMap().entrySet()) {
            itemsQuantity+=Integer.valueOf(pair.getValue().get(1));
        }
        String itemsQuantityText;
        if(itemsQuantity==1) {
            itemsQuantityText = itemsQuantity + " item";
        }else{
            itemsQuantityText=itemsQuantity+" items";
        }
        tvItemsQuantity.setText(itemsQuantityText);
        RelativeLayout relativeLayout=(RelativeLayout)convertView.findViewById(R.id.relativeLayoutOrders);
        String id="#"+order.getCode();
        tvOrderNumber.setText(id);
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm dd/MM");
        tvOrderTime.setText(dateFormat.format(order.getDate().getTime()));
        relativeLayout.setBackgroundColor(Color.parseColor(order.getColor()));

        // Return the completed view to render on screen
        return convertView;
    }
}
