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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LoginActivity loginActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Product> PRODUCTS;
    private ListView listViewProducts;
    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        //Get category of fragment
        this.getArguments();
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_category, container, false);
        //Retrieving data from database
        PRODUCTS=new ArrayList<>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference();
        databaseReference.child("Products").child(this.getArguments().getString("category"))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                PRODUCTS=new ArrayList<>();
                for(DataSnapshot child:children){
                    PRODUCTS.add(child.getValue(Product.class));
                }
                if(listViewProducts!=null) {
                    updateListView(listViewProducts, PRODUCTS);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Setting listview
        if (loginActivity.isStaff == "true") {
            listViewProducts=(ListView)v.findViewById(R.id.listViewProducts);
            ProductStaffAdapter productAdapter=new ProductStaffAdapter(getContext(),PRODUCTS);
            listViewProducts.setAdapter(productAdapter);
            return v;
        }
        else  {
            listViewProducts=(ListView)v.findViewById(R.id.listViewProducts);
            ProductAdapter productAdapter=new ProductAdapter(getContext(),PRODUCTS);
            listViewProducts.setAdapter(productAdapter);
//            listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Intent intent=new Intent(getContext(),ItemDetailsActivity.class);
//                    intent.putExtra("Product",PRODUCTS.get(i));
//                    startActivity(intent);
//                }
//            });
            return v;
        }

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
    public void updateListView(ListView listView,ArrayList<Product> PRODUCTS){
        if(getContext()!=null) {
            if (loginActivity.isStaff == "true") {
                ProductStaffAdapter productAdapter = new ProductStaffAdapter(getContext(), PRODUCTS);
                listView.setAdapter(productAdapter);
            } else {
                ProductAdapter productAdapter = new ProductAdapter(getContext(), PRODUCTS);
                listView.setAdapter(productAdapter);
            }
        }
    }
}
