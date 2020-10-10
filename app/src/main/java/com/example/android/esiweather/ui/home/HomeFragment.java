package com.example.android.esiweather.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.esiweather.R;
import com.example.android.esiweather.ui.actions.ActionsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView date;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_date);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //View view = getView();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("last-val");
        final DatabaseReference sunRef = database.getReference().child("sun-time");
        final DatabaseReference iconsRef = database.getReference().child("icons");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        final String convertedTime = simpleDateFormat.format(Calendar.getInstance().getTime());

       // final SimpleDateFormat DateFormat = new SimpleDateFormat("hh:mm");

        final TextView time = (TextView) root.findViewById(R.id.text_time);
        time.setText(convertedTime);
        final TextView time_sun_rise = (TextView) root.findViewById(R.id.time_sun_rise);
        final TextView time_sun_set = (TextView) root.findViewById(R.id.time_sun_set);
        final TextView weather = (TextView) root.findViewById(R.id.textView2);
        final TextView texttemp = (TextView) root.findViewById(R.id.text_temp);
        final TextView textwind = (TextView) root.findViewById(R.id.text_wind);
        final TextView textrain = (TextView) root.findViewById(R.id.text_rain);
        final TextView texthumd = (TextView) root.findViewById(R.id.text_humd);
        final ImageView imageweather = (ImageView) root.findViewById(R.id.imageView2);
        // Write a message to the database

        final DatabaseReference notifRef = database.getReference().child("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() +"/notif");
        //myRef.setValue("Hello, World!");
        // Read from the database

        iconsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //final String convertedTime2 = DateFormat.format(snapshot.child("sun-rise").getValue().toString());

                String x =snapshot2.child("weather").getValue().toString();
                weather.setText(x);

                if (x.equals("cloud") ){
                    imageweather.setImageResource(R.drawable.cloud);
                }
                else if(x.equals("sun")){
                    imageweather.setImageResource(R.drawable.sun);
                }
                else if(x.equals("rain")){
                    imageweather.setImageResource(R.drawable.rain);
                }
                else {
                    imageweather.setImageResource(R.drawable.snow);
                };


                sunRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //final String convertedTime2 = DateFormat.format(snapshot.child("sun-rise").getValue().toString());
                        time_sun_rise.setText(snapshot.child("sun-rise").getValue().toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                sunRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //final String convertedTime2 = DateFormat.format(snapshot.child("sun-rise").getValue().toString());
                        time_sun_set.setText(snapshot.child("sun-set").getValue().toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               // Log.d(TAG, " befor: ");
                // Map<String, String> mape =dataSnapshot.getValue(Map.class);
              //  Log.d(TAG, " after: ");
                final String temp = dataSnapshot.child("temp").getValue().toString();
                String rain = dataSnapshot.child("rain").getValue().toString();
                String wind = dataSnapshot.child("wind").getValue().toString();
                final String pressure = dataSnapshot.child("pressure").getValue().toString();
                final String humidty = dataSnapshot.child("humidty").getValue().toString();



                texttemp.setText(temp + "°");
                textrain.setText(rain );
                textwind.setText(wind);
                texthumd.setText(humidty);


                // test for notification
                notifRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        String tempstate = snapshot.child("temp_checkbox").getValue().toString();
                        String tempmax = snapshot.child("temperature_valeur_max").getValue().toString();
                        String tempmin = snapshot.child("temperature_valeur_min").getValue().toString();

                        String pressurestate = snapshot.child("press_checkbox").getValue().toString();
                        String pressuremax = snapshot.child("pressure_valeur_max").getValue().toString();
                        String pressuremin = snapshot.child("pressure_valeur_min").getValue().toString();

                        String humidtystate = snapshot.child("hum_checkbox").getValue().toString();
                        String humidtymax = snapshot.child("humidity_valeur_max").getValue().toString();
                        String humidtymin = snapshot.child("humidity_valeur_min").getValue().toString();
                        //Log.d(TAG,   Integer.parseInt(tempmax) + " <= "+Integer.parseInt(temp) +" true ");
                        //Log.d(TAG,   tempstate + " == 1");


                        if (Float.parseFloat(tempstate) == 1 ){
                            if ( Float.parseFloat(tempmax)  <= Float.parseFloat(temp) ){
                             //   Log.d(TAG, "  Integer.parseInt(tempmax)  <= Integer.parseInt(temp)  true ");
                                notifRef.child("notif_stack").push().setValue("la température est plus que " + tempmax + " !!  temp = " + temp  );
                            }
                            else if(Float.parseFloat(tempmin)  >= Float.parseFloat(temp) ){
                                notifRef.child("notif_stack").push().setValue("la température est moin que " + tempmin + " !!  temp = " + temp );
                            };
                        };
                        if (Float.parseFloat(pressurestate) == 1 ){
                            if ( Float.parseFloat(pressuremax)  <= Float.parseFloat(pressure) ){
                                notifRef.child("notif_stack").push().setValue("la Pression est plus que " + pressuremax + " !!  Pression = " + pressure  );
                            }
                            else if( Float.parseFloat(pressuremin)  >= Float.parseFloat(pressure) ){
                                notifRef.child("notif_stack").push().setValue("la Pression est moin que " + pressuremin + " !!  Pression = " + pressure );
                            };
                        };
                        if (Float.parseFloat(humidtystate) == 1 ){
                            if ( Integer.parseInt(humidtymax)  <= Float.parseFloat(humidty) ){
                                notifRef.child("notif_stack").push().setValue("l'humidty est plus que " + humidtymax + " !!  humidty = " + humidty );
                            }
                            else if( Float.parseFloat(humidtymin)  >= Float.parseFloat(humidty) ){
                                notifRef.child("notif_stack").push().setValue("l'humidty est moin que " + humidtymin + " !!  humidty = " + humidty );
                            };
                        };






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






                        // test for notification










            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        return root;



        //return inflater.inflate(R.layout.fragment_home, container, false);




    }

    @Override
    public void onStart() {
        super.onStart();


    }
}