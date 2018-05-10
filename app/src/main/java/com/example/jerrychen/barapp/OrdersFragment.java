package com.example.jerrychen.barapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView listViewOrders;
    private ArrayList<Order> CURRENT_ORDERS;
    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_orders, container, false);
        CURRENT_ORDERS=new ArrayList<>();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference();

//        HashMap<String,Integer> temp=new HashMap<>();
//        temp.put("JMMOIMVNHX",2);
//        Date date=new Date(System.currentTimeMillis());
//        Order order=new Order(date,temp,21);
//        databaseReference.child("orders").child(order.getId()).setValue(order);
//        databaseReference.child("orders").child(order.getId()).child("status").setValue(Status.paid);


        databaseReference.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                CURRENT_ORDERS=new ArrayList<>();
                for(DataSnapshot child:children){
                    Order temp=child.getValue(Order.class);
                    if(temp.getStatus()==Status.paid||temp.getStatus()==Status.started) {
                        CURRENT_ORDERS.add(temp);
                    }
                }
                Collections.sort(CURRENT_ORDERS, new Comparator<Order>() {
                    @Override
                    public int compare(Order order, Order t1) {
                        return -order.getDate().compareTo(t1.getDate());
                    }
                });
                if(listViewOrders!=null) {
                    updateListView(listViewOrders, CURRENT_ORDERS);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Set up a listView
        listViewOrders=(ListView)view.findViewById(R.id.listViewOrders);
        OrdersStaffAdapter productAdapter=new OrdersStaffAdapter(getContext(),CURRENT_ORDERS);
        listViewOrders.setAdapter(productAdapter);

        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(getContext(), OrderDetailActivity.class);
                myIntent.putExtra("Order",CURRENT_ORDERS.get(i));
                getContext().startActivity(myIntent);
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference();
                for (int i = 0; i < CURRENT_ORDERS.size(); i++) {
                    Order order=CURRENT_ORDERS.get(i);
                    long timeElapsed = System.currentTimeMillis()- order.getDate().getTime();
                    if (timeElapsed > 300000) {
                        if(!order.getColor().equals("#FF0000")){
                            databaseReference.child("orders").child(order.getId()).child("color").setValue("#FF0000");
                        }
                    }else if (timeElapsed > 180000) {
                        if(!order.getColor().equals("#FFFF00")){
                            databaseReference.child("orders").child(order.getId()).child("color").setValue("#FFFF00");
                        }
                    }else{
                        if(!order.getColor().equals("#7ED41B")){
                            databaseReference.child("orders").child(order.getId()).child("color").setValue("#7ED41B");
                        }
                    }

                }
            }
        }, 0, 5000);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void updateListView(ListView listView,ArrayList<Order> CURRENT_ORDERS){
            if(getContext()!=null) {
                OrdersStaffAdapter productAdapter = new OrdersStaffAdapter(getContext(), CURRENT_ORDERS);
                listView.setAdapter(productAdapter);
            }
    }
}
