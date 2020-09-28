package com.example.android.esiweather.ui.notification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.esiweather.ListItem;
import com.example.android.esiweather.R;
import com.example.android.esiweather.listitemfornotif;
import com.example.android.esiweather.ui.actions.ActionsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NotificationFragment extends Fragment {
    final ArrayList<listitemfornotif.ListItemForNotif> item = new ArrayList<listitemfornotif.ListItemForNotif>();
    final NotificationFragment.MyCustomAdapter myadpter = new NotificationFragment.MyCustomAdapter(item);

    private NotificationViewModel notificationViewModel;
    private Context mContext;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference notifRef = database.getReference().child("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() +"/notif/notif_stack");
    int i;

    //////////////////////////////////////////////////////////////////////////////////////////////////////:
    // Write a message to the database

    //myRef.setValue("Hello, World!");
    // Read from the database

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notification, container, false);

        final CardView cardViewdellet =(CardView) root.findViewById(R.id.cardclearall);

        final ListView ls = (ListView) root.findViewById(R.id.actions_listview_notification);

        notifRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.d("TAG", " befor: ");
                // Map<String, String> mape =dataSnapshot.getValue(Map.class);
                // Log.d("TAG", " after: ");

                // globalClass.setItems("name", false);




                String name ;
                String key ;
                String nember = dataSnapshot.getChildrenCount() + "";
                i = (int) Integer. parseInt(nember);
                int j = 0 ;
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren() ) {
                    Log.d("i=", String.valueOf(i));
                    name = (String) uniqueKeySnapshot.getValue();
                    key = uniqueKeySnapshot.getKey();
                    Log.d("Tag", name);

                    Log.d("Tag", String.valueOf(j+1));

                    if (item.size() > j)  {
                        item.set(j,new listitemfornotif.ListItemForNotif(name,key));
                        ls.setAdapter(myadpter);
                    }else{
                        item.add(new listitemfornotif.ListItemForNotif(name,key));
                        ls.setAdapter(myadpter);
                    }
                    j++;



                }
                //item.add(new listitemfornotif.ListItemForNotif(name));


                //String temp = dataSnapshot.child("1").child("name").getValue().toString();
                //Log.d("Tag",temp);
                // String rain = dataSnapshot.child("rain").getValue().toString();
                //String wind = dataSnapshot.child("wind").getValue().toString();
                //String humidty = dataSnapshot.child("humidty").getValue().toString();

                if (item.size()> i) {
                    item.remove(item.size()-1);

                }


            }





            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

////////////////////////////////////end firebase/////////////////////////////////////////////////////////////////:

       // item.add(new listitemfornotif.ListItemForNotif("hello"));
       // item.add(new listitemfornotif.ListItemForNotif("it is a test"));
       // item.add(new listitemfornotif.ListItemForNotif("for the notif fragment"));




       ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

               final int item_position = i;

               new AlertDialog.Builder(mContext)
                       .setIcon(android.R.drawable.ic_delete)
                       .setTitle("are you sure?")
                       .setMessage("do you want to delete this notification")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                              // myRef.order;
                               Log.d("name of ",ls.getItemAtPosition(item_position).toString() );
                               Log.d("key of ",item.get(item_position).key );
                               notifRef.child(item.get(item_position).key).removeValue();
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

        ls.setAdapter(myadpter);
        //myadpter.notifyDataSetChanged();
    /*    ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView txtname = (TextView) view.findViewById(R.id.text_action);
                //Switch state = (Switch) view.findViewById(R.id.action_switch_state);

                item.add(new listitemfornotif.ListItemForNotif("rana"));
                // globalClass.setItems("sama", true);
                myadpter.notifyDataSetChanged();

            }
        });*/


        cardViewdellet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifRef.removeValue();
                item.clear();
                myadpter.notifyDataSetChanged();
            }
        });


        return root;
    }

    class MyCustomAdapter extends BaseAdapter {
        ArrayList<listitemfornotif.ListItemForNotif> Items = new ArrayList<listitemfornotif.ListItemForNotif>();

        MyCustomAdapter(ArrayList<listitemfornotif.ListItemForNotif> Items) {
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
            return Items.get(position).notif;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.notif_examle, null);

            TextView txtname = (TextView) view1.findViewById(R.id.text_notif);
            String key ;
            txtname.setText(Items.get(i).notif);
            txtname.setTypeface(Typeface.SERIF);
            txtname.setTypeface(Typeface.DEFAULT_BOLD);
            key = item.get(i).key;
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





 /*   public void showpopup() {

        final AlertDialog.Builder dialogbuilder;
        final AlertDialog dialog;
        dialogbuilder = new AlertDialog.Builder(mContext);
        final View contactpopupview = getLayoutInflater().inflate(R.layout.add_action_popup,null);
        dialogbuilder.setView(contactpopupview);

        dialog = dialogbuilder.create();
        dialog.show();
        dialog.getWindow().setLayout(1000,1800);


        final EditText editText =  contactpopupview.findViewById(R.id.edit_text_action_name);
        // final String name = editText.getText().toString().trim();
        //final String name = "text";
        final Button buttonadd = contactpopupview.findViewById(R.id.add_action) ;
        final TextView exit_popup_action = contactpopupview.findViewById(R.id.exit_popup_action) ;


        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //globalClass.setItems(editText.getText().toString(),false);
                // item.add(new ListItem(editText.getText().toString(), false));

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("name", editText.getText().toString());
                childUpdates.put("state", "false");

                //mDatabase.updateChildren(childUpdates);


                myRef.push().updateChildren(childUpdates);
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
*/



}