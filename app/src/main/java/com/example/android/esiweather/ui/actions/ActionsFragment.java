package com.example.android.esiweather.ui.actions;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.android.esiweather.ListItem;
import com.example.android.esiweather.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ActionsFragment extends Fragment {
    ArrayList<ListItem> item = new ArrayList<ListItem>();
    final MyCustomAdapter myadpter = new MyCustomAdapter(item);
    private ActionsViewModel actionsViewModel;
    private Context mContext;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference actionRef = database.getReference().child("actions/");
    int i;
    int keyforupdatethedatabase;
    ProgressBar progressBar;


    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        actionsViewModel = ViewModelProviders.of(this).get(ActionsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_actions, container, false);
        //final TextView textView = root.findViewById(R.id.text_actions);





//////////////////////////////////////////////////////////////////////////////////////////////////////:
        // Write a message to the database
        //myRef.setValue("Hello, World!");
        // Read from the database
        final ListView ls = (ListView) root.findViewById(R.id.actions_listview);
        final ProgressBar progressBar = root.findViewById(R.id.progressBaraction);
        progressBar.setVisibility(View.VISIBLE);
        actionRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String name;
                String state;
                String key;
                String nember = dataSnapshot.getChildrenCount() + "";
                i = (int) Integer.parseInt(nember);
                int j = 0;
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    Log.d("i=", String.valueOf(i));
                    name = (String) uniqueKeySnapshot.child("name").getValue().toString();
                    state = (String) uniqueKeySnapshot.child("state").getValue().toString();
                    key = (String) uniqueKeySnapshot.getKey();
                    Log.d("Tag", name);
                    Log.d("Tag", state);

                    Log.d("Tag", String.valueOf(j + 1));
                    if (state.equals("OFF")) {
                        if (item.size() > j) {
                            item.set(j, new ListItem(name, "OFF", key));
                            ls.setAdapter(myadpter);
                        } else {
                            item.add(new ListItem(name, "OFF", key));
                            ls.setAdapter(myadpter);
                        }


                    } else {

                        if (item.size() > j) {
                            item.set(j, new ListItem(name, "ON", key));
                            ls.setAdapter(myadpter);
                        } else {
                            item.add(new ListItem(name, "ON", key));
                            myadpter.notifyDataSetChanged();
                        }


                    }

                    j++;

                }


                if (item.size() > i) {
                    item.remove(item.size() - 1);
                }

                progressBar.setVisibility(View.INVISIBLE);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
                Toast.makeText(getActivity(), "Failed to read value!", Toast.LENGTH_SHORT).show();
            }
        });

////////////////////////////////////end firebase/////////////////////////////////////////////////////////////////:


// suppression des actions code

/*
         ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                 final int item_position = i;

                 new AlertDialog.Builder(mContext)
                         .setIcon(android.R.drawable.ic_delete)
                         .setTitle("are you sure?")
                         .setMessage("do you want to delete this action")
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {
                                         // myRef.order;
                                         Log.d("name of ",ls.getItemAtPosition(item_position).toString() );
                                         Log.d("key of ",item.get(item_position).key );
                                         myRef.child(item.get(item_position).key).removeValue();
                                         item.remove(item_position);
                                         myadpter.notifyDataSetChanged();

                                     }


                                 }
                         )
                         .setNegativeButton("No",null)
                         .show();



                 return true;
             }
         });

*/


// change the state of the action
        ls.setAdapter(myadpter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (item.get(position).state.equals("OFF")) {
                    //item.set(i,new ListItem(item.get(i).action,"ON",item.get(i).key)) ;
                    actionRef.child(item.get(position).key).child("state").setValue("ON");
                } else {
                    //item.set(i,new ListItem(item.get(i).action,"OFF",item.get(i).key)) ;
                    actionRef.child(item.get(position).key).child("state").setValue("OFF");
                }


            }
        });

        // code that manage the add button using showpopup() function

            /*  Button button = root.findViewById(R.id.button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                showpopup();

                            }
                        });

            */


        return root;


    }


    class MyCustomAdapter extends BaseAdapter {
        ArrayList<ListItem> Items = new ArrayList<ListItem>();

        MyCustomAdapter(ArrayList<ListItem> Items) {
            this.Items = Items;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).action;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.layout_example, null);

            TextView txtname = (TextView) view1.findViewById(R.id.text_action);
            final TextView state = (TextView) view1.findViewById(R.id.action_switch_state);
            txtname.setText(Items.get(i).action);
            txtname.setTypeface(Typeface.DEFAULT_BOLD);
            txtname.setTypeface(Typeface.SERIF);
            txtname.setTextSize(20);
            state.setText(Items.get(i).state);
            if (item.get(i).state.equals("OFF")) {
                state.setTextColor(Color.RED);
            } else {
                state.setTextColor(Color.GREEN);
            }
            state.setTextSize(20);

            return view1;

        }


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public void showpopup() {

        final AlertDialog.Builder dialogbuilder;
        final AlertDialog dialog;
        dialogbuilder = new AlertDialog.Builder(mContext);
        final View contactpopupview = getLayoutInflater().inflate(R.layout.add_action_popup, null);
        dialogbuilder.setView(contactpopupview);

        dialog = dialogbuilder.create();
        dialog.show();
        dialog.getWindow().setLayout(1000, 1800);


        final EditText editText = contactpopupview.findViewById(R.id.edit_text_action_name);
        // final String name = editText.getText().toString().trim();
        //final String name = "text";
        final Button buttonadd = contactpopupview.findViewById(R.id.add_action);
        final TextView exit_popup_action = contactpopupview.findViewById(R.id.exit_popup_action);


        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //globalClass.setItems(editText.getText().toString(),false);
                // item.add(new ListItem(editText.getText().toString(), false));

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("name", editText.getText().toString());
                childUpdates.put("state", "0FF");

                //mDatabase.updateChildren(childUpdates);


                actionRef.push().updateChildren(childUpdates);
                // child("name").setValue(editText.getText().toString());
                //myRef.child(String.valueOf(i+1)).child("state").setValue("false");
                // myadpter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });


        exit_popup_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });


    }
}










                                        /*
                                        *
                                        *
                                        *
                                        *
                                        *
                                        *       // Write a message to the database
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference().child("last-val");
                                                //myRef.setValue("Hello, World!");
                                                // Read from the database
                                                myRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        // This method is called once with the initial value and again
                                                        // whenever data at this location is updated.
                                                        Log.d(TAG, " befor: ");
                                                        // Map<String, String> mape =dataSnapshot.getValue(Map.class);
                                                        Log.d(TAG, " after: ");
                                                        String temp = dataSnapshot.child("temp").getValue().toString();
                                                        String rain = dataSnapshot.child("rain").getValue().toString();
                                                        String wind = dataSnapshot.child("wind").getValue().toString();
                                                        String humidty = dataSnapshot.child("humidty").getValue().toString();

                                                        texttemp.setText(temp + "°");
                                                        textrain.setText(rain );
                                                        textwind.setText(wind);
                                                        texthumd.setText(humidty);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError error) {
                                                        // Failed to read value
                                                        Log.w(TAG, "Failed to read value.", error.toException());
                                                    }
                                                });

                                        *
                                        *
                                        *
                                        *
                                        *
                                        *
                                        *
                                        *
                                        * */

