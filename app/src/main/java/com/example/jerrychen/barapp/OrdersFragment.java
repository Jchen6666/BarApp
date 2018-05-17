package com.example.jerrychen.barapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private Order myOrder;
    Map<String,ArrayList<String>> orderMap;
    private OnFragmentInteractionListener mListener;
    private ListView listViewOrders;
    private ArrayList<Order> CURRENT_ORDERS;
    ArrayList<String> CURRENT_ORDERSID;
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
        myOrder=new Order();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        final DatabaseReference dbRef=firebaseDatabase.getReference();

        if (LoginActivity.isStaff=="false") {
            view=inflater.inflate(R.layout.fragment_orders_customer, container, false);
            final Button orderButton=view.findViewById(R.id.buttonOrder);
            final Button currentOrderButton=view.findViewById(R.id.navigation);
            listViewOrders=view.findViewById(R.id.listViewOrders);
            dbRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                ArrayList<String> currentOrders=new ArrayList<>();
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myOrder=new Order();

                    if (dataSnapshot.child("cart").exists()) {
                        myOrder = dataSnapshot.child("cart").getValue(Order.class);
                        Log.d("Tag","TAG"+myOrder);
                        OrdersCustomerAdapter ordersCustomerAdapter=new OrdersCustomerAdapter(myOrder.getOrderMap(),myOrder);
                        listViewOrders.setAdapter(ordersCustomerAdapter);
                    }
                    if (listViewOrders!=null&&myOrder.getOrderMap()!=null){
                        updateListViewCustomer(listViewOrders, myOrder.getOrderMap(), myOrder);
                    }
                    if (myOrder==null){
                        Toast.makeText(getContext(),"no current order",Toast.LENGTH_LONG);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            CURRENT_ORDERSID=new ArrayList<>();
            dbRef.child("users").child(user.getUid()).child("currentOrder").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot>children=dataSnapshot.getChildren();
                    for (DataSnapshot child:children){
                        String id=child.getValue(String.class);
                        CURRENT_ORDERSID.add(id);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myOrder!=null) {
                        dbRef.child("users").child(user.getUid()).child("cart").removeValue();
                        Order finalOrder = new Order(new Date(), myOrder.getOrderMap(),myOrder.getPrice());
                        finalOrder.setStatus(Status.paid);
                       if (CURRENT_ORDERS!=null) {
                           CURRENT_ORDERSID.add(myOrder.getId());
                           dbRef.child("users").child(user.getUid()).child("currentOrder").setValue(CURRENT_ORDERSID);
                       }
                        dbRef.child("orders").child(myOrder.getId()).setValue(finalOrder);

                    }
                }

            });

            currentOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent=new Intent(getContext(),OrdersCustomerDetailActivity.class);
                    getContext().startActivity(myIntent);
                }
            });

        }


       else if (LoginActivity.isStaff=="true") {
             view=inflater.inflate(R.layout.fragment_orders, container, false);
            dbRef.child("orders").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    CURRENT_ORDERS = new ArrayList<>();
                    for (DataSnapshot child : children) {
                        Order temp = child.getValue(Order.class);
                        if (temp.getStatus() == Status.paid || temp.getStatus() == Status.started) {
                            CURRENT_ORDERS.add(temp);
                        }
                    }
                    Collections.sort(CURRENT_ORDERS, new Comparator<Order>() {
                        @Override
                        public int compare(Order order, Order t1) {
                            return -order.getDate().compareTo(t1.getDate());
                        }
                    });
                    if (listViewOrders != null) {
                        updateListView(listViewOrders, CURRENT_ORDERS);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //Set up a listView
            listViewOrders = (ListView) view.findViewById(R.id.listViewOrders);
            OrdersStaffAdapter productAdapter = new OrdersStaffAdapter(getContext(), CURRENT_ORDERS);
            listViewOrders.setAdapter(productAdapter);

            listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent myIntent = new Intent(getContext(), OrderDetailActivity.class);
                    myIntent.putExtra("Order", CURRENT_ORDERS.get(i));
                    Log.d("CurrentOrder","CurrentOrderID: "+CURRENT_ORDERS.get(i).getId());
                    getContext().startActivity(myIntent);
                }
            });

//            new Timer().scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference databaseReference = database.getReference();
//                    for (int i = 0; i < CURRENT_ORDERS.size(); i++) {
//                        Order order = CURRENT_ORDERS.get(i);
//                        long timeElapsed = System.currentTimeMillis() - order.getDate().getTime();
//                        if (timeElapsed > 300000) {
//                            if (!order.getColor().equals("#ff6666")) {
//                                databaseReference.child("orders").child(order.getId()).child("color").setValue("#ff6666");
//                            }
//                        } else if (timeElapsed > 180000) {
//                            if (!order.getColor().equals("#ffff66")) {
//                                databaseReference.child("orders").child(order.getId()).child("color").setValue("#ffff66");
//                            }
//                        } else {
//                            if (!order.getColor().equals("#99ff99")) {
//                                databaseReference.child("orders").child(order.getId()).child("color").setValue("#99ff99");
//                            }
//                        }
//
//                    }
//                }
//            }, 0, 5000);
        }
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
    public void updateListViewCustomer(ListView listView,Map<String,ArrayList<String>> CURRENT_ORDER,Order ORDER){
        if(getContext()!=null) {
            OrdersCustomerAdapter productAdapter = new OrdersCustomerAdapter( CURRENT_ORDER,ORDER);
            listView.setAdapter(productAdapter);
        }
    }
    public void updateListView(ListView listView,ArrayList<Order> CURRENT_ORDERS){
        if(getContext()!=null) {
            OrdersStaffAdapter productAdapter = new OrdersStaffAdapter(getContext(), CURRENT_ORDERS);
            listView.setAdapter(productAdapter);
        }
    }
}
