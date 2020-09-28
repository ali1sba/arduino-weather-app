package com.example.android.esiweather.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.esiweather.R;
import com.example.android.esiweather.Signup;
import com.example.android.esiweather.ui.settings.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final CardView cardViewfacebook = root.findViewById(R.id.cardfacebook);
        final CardView cardViewgmail = root.findViewById(R.id.cardgmail);
        final CardView cardViewwebsite = root.findViewById(R.id.cardwebsite);
        final CardView cardViewlogout = root.findViewById(R.id.cardlogout);
        final TextView username = root.findViewById(R.id.username);
        final TextView email = root.findViewById(R.id.email);
        final TextView phonenember = root.findViewById(R.id.phone_nember);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users/" + FirebaseAuth.getInstance().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email.setText(  snapshot.child("email").getValue().toString());
                phonenember.setText( snapshot.child("phone_number").getValue().toString());
                username.setText(  snapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
username.setAllCaps(true);
username.setTextSize(25);

        //Button Logout = (Button) root.findViewById(R.id.logout);

        cardViewfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://web.facebook.com/Esi-Weather-Station-103928668045571/" ) );
                startActivity( browse );
            }
        });

        cardViewwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://esisbameteo.ddns.net"  ));
                startActivity( browse );
            }
        });
        cardViewgmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setType("plain/text")
                        .setData(Uri.parse("esisbameteo@gmail.com"))
                        .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

                startActivity(intent);
            }
        });

        cardViewlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (getActivity(),Signup.class);
                startActivity(intent);


            }
        });


        return root;
    }

}
