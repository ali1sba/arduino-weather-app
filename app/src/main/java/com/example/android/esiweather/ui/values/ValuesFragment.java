package com.example.android.esiweather.ui.values;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.APIlib;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.android.esiweather.R;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.text.HAlign;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;
//import com.anychart.sample.R;




public class ValuesFragment extends Fragment {

    private ValuesViewModel valuesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        valuesViewModel =
                ViewModelProviders.of(this).get(ValuesViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_values, container, false);






        /////////////////////////////////////////////////////////////////////

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("last-val");


        final AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));

        final CircularGauge circularGauge = AnyChart.circular();
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0, 0, 0, 0)
                .margin(30, 30, 30, 30);
        circularGauge.startAngle(0)
                .sweepAngle(360);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                double temp = Double.parseDouble( dataSnapshot.child("temp").getValue().toString());
                APIlib.getInstance().setActiveAnyChartView(anyChartView);
                circularGauge.data(new SingleValueDataSet(new Double[] { temp }));
                circularGauge.label(1)
                        .text("<span style=\"font-size: 20\">" + temp + "</span>")
                        .useHtml(true)
                        .fontColor("black")
                        .hAlign(HAlign.CENTER);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        circularGauge.axis(0)
                .startAngle(-150)
                .radius(80)
                .sweepAngle(300)
                .width(5)
                .ticks("{ type: 'line', length: 4, position: 'outside' }");

        circularGauge.axis(0).labels().position("outside");

        circularGauge.axis(0).scale()
                .minimum(-30)
                .maximum(70);

        circularGauge.axis(0).scale()
                .ticks("{interval: 5}")
                .minorTicks("{interval: 10}");

        circularGauge.needle(0)
                .stroke(null)
                .startRadius("6%")
                .endRadius("70%")
                .startWidth("2%")
                .endWidth(0);

        circularGauge.cap()
                .radius("8%")
                .enabled(true)
                .stroke(null);

        circularGauge.label(0)
                .text("<span style=\"font-size: 25\">Temperature</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge.label(0)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(20, 0, 0, 0);


        circularGauge.label(2)
                .text("<span style=\"font-size: 25\">C°</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge.label(2)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(140, 0, 0, 0);


        circularGauge.label(1)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(-100)
                .padding(5, 10, 0, 0)
                .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}");

        circularGauge.range(0,
                "{\n" +
                        "    from: 10,\n" +
                        "    to: 40,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'green 0.7',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge.range(1,
                "{\n" +
                        "    from: 40,\n" +
                        "    to: 70,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'red 0.6',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");
        circularGauge.range(3,
                "{\n" +
                        "    from: -10,\n" +
                        "    to: 10,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'blue 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");
        circularGauge.range(4,
                "{\n" +
                        "    from: -25,\n" +
                        "    to: -10,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'blue 0.7',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");
        circularGauge.range(5,
                "{\n" +
                        "    from: -40,\n" +
                        "    to: -25,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'blue',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        anyChartView.setChart(circularGauge);
        final AnyChartView anyChartView1 = root.findViewById(R.id.any_chart_view1);
        APIlib.getInstance().setActiveAnyChartView(anyChartView1);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));

        final CircularGauge circularGauge1 = AnyChart.circular();
        circularGauge1.fill("#fff")
                .stroke(null)
                .padding(0, 0, 0, 0)
                .margin(30, 30, 30, 30);
        circularGauge1.startAngle(0)
                .sweepAngle(360);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, " befor: ");
                // Map<String, String> mape =dataSnapshot.getValue(Map.class);
                Log.d(TAG, " after: ");
                double hum = Double.parseDouble( dataSnapshot.child("humidty").getValue().toString());




                APIlib.getInstance().setActiveAnyChartView(anyChartView1);
                circularGauge1.data(new SingleValueDataSet(new Double[] { hum }));

                circularGauge1.label(1)
                        .text("<span style=\"font-size: 20\">" + hum + "</span>")
                        .useHtml(true)
                        .fontColor("black")
                        .hAlign(HAlign.CENTER);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        circularGauge1.axis(0)
                .startAngle(-150)
                .radius(80)
                .sweepAngle(300)
                .width(5)
                .ticks("{ type: 'line', length: 4, position: 'outside' }");

        circularGauge1.axis(0).labels().position("outside");

        circularGauge1.axis(0).scale()
                .minimum(0)
                .maximum(100);

        circularGauge1.axis(0).scale()
                .ticks("{interval: 5}")
                .minorTicks("{interval: 10}");

        circularGauge1.needle(0)
                .stroke(null)
                .startRadius("6%")
                .endRadius("70%")
                .startWidth("2%")
                .endWidth(0);

        circularGauge1.cap()
                .radius("8%")
                .enabled(true)
                .stroke(null);

        circularGauge1.label(0)
                .text("<span style=\"font-size: 25\">Humidity</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge1.label(0)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(20, 0, 0, 0);

        circularGauge1.label(2)
                .text("<span style=\"font-size: 25\">%</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge1.label(2)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(140, 0, 0, 0);


        circularGauge1.range(0,
                "{\n" +
                        "    from: 70,\n" +
                        "    to: 30,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'green 0.7',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge1.range(1,
                "{\n" +
                        "    from: 70,\n" +
                        "    to: 100,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'red 0.6',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge1.range(3,
                "{\n" +
                        "    from: 0,\n" +
                        "    to: 30,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'blue 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");



        circularGauge1.label(1)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(-100)
                .padding(5, 10, 0, 0)
                .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}");

        anyChartView1.setChart(circularGauge1);




        final AnyChartView anyChartView2 = root.findViewById(R.id.any_chart_view2);
        APIlib.getInstance().setActiveAnyChartView(anyChartView2);
        anyChartView2.setProgressBar(root.findViewById(R.id.progress_bar));

        final CircularGauge circularGauge2 = AnyChart.circular();
        circularGauge2.fill("#fff")
                .stroke(null)
                .padding(0, 0, 0, 0)
                .margin(30, 30, 30, 30);
        circularGauge2.startAngle(0)
                .sweepAngle(360);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, " befor: ");
                // Map<String, String> mape =dataSnapshot.getValue(Map.class);
                Log.d(TAG, " after: ");
                double wind = Double.parseDouble( dataSnapshot.child("wind").getValue().toString());




                APIlib.getInstance().setActiveAnyChartView(anyChartView2);
                circularGauge2.data(new SingleValueDataSet(new Double[] { wind }));

                circularGauge2.label(1)
                        .text("<span style=\"font-size: 20\">" + wind + "</span>")
                        .useHtml(true)

                        .fontColor("black")
                        .hAlign(HAlign.CENTER);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        circularGauge2.axis(0)
                .startAngle(-150)
                .radius(80)
                .sweepAngle(300)
                .width(5)
                .ticks("{ type: 'line', length: 4, position: 'outside' }");

        circularGauge2.axis(0).labels().position("outside");

        circularGauge2.axis(0).scale()
                .minimum(0)
                .maximum(100);

        circularGauge2.axis(0).scale()
                .ticks("{interval: 5}")
                .minorTicks("{interval: 10}");

        circularGauge2.needle(0)
                .stroke(null)
                .startRadius("6%")
                .endRadius("70%")
                .startWidth("2%")
                .endWidth(0);

        circularGauge2.cap()
                .radius("8%")
                .enabled(true)
                .stroke(null);

        circularGauge2.label(0)
                .text("<span style=\"font-size: 25\">Wind</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge2.label(0)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(20, 0, 0, 0);

        circularGauge2.label(2)
                .text("<span style=\"font-size: 25\">KM/H</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge2.label(2)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(140, 0, 0, 0);

        circularGauge2.label(1)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(-100)
                .padding(5, 10, 0, 0)
                .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}");

        anyChartView2.setChart(circularGauge2);


        final AnyChartView anyChartView3 = root.findViewById(R.id.any_chart_view3);
        APIlib.getInstance().setActiveAnyChartView(anyChartView3);
        anyChartView3.setProgressBar(root.findViewById(R.id.progress_bar));

        final CircularGauge circularGauge3 = AnyChart.circular();
        circularGauge3.fill("#fff")
                .stroke(null)
                .padding(0, 0, 0, 0)
                .margin(30, 30, 30, 30);
        circularGauge3.startAngle(0)
                .sweepAngle(360);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, " befor: ");
                // Map<String, String> mape =dataSnapshot.getValue(Map.class);
                Log.d(TAG, " after: ");
                double rain = Double.parseDouble( dataSnapshot.child("rain").getValue().toString());




                APIlib.getInstance().setActiveAnyChartView(anyChartView3);
                circularGauge3.data(new SingleValueDataSet(new Double[] { rain }));

                circularGauge3.label(1)
                        .text("<span style=\"font-size: 20\">" + rain + "</span>")
                        .useHtml(true)
                        .fontColor("black")
                        .hAlign(HAlign.CENTER);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        circularGauge3.axis(0)
                .startAngle(-150)
                .radius(80)
                .sweepAngle(300)
                .width(5)
                .ticks("{ type: 'line', length: 4, position: 'outside' }");

        circularGauge3.axis(0).labels().position("outside");

        circularGauge3.axis(0).scale()
                .minimum(0)
                .maximum(100);

        circularGauge3.axis(0).scale()
                .ticks("{interval: 5}")
                .minorTicks("{interval: 10}");

        circularGauge3.needle(0)
                .stroke(null)
                .startRadius("6%")
                .endRadius("70%")
                .startWidth("2%")
                .endWidth(0);

        circularGauge3.cap()
                .radius("8%")
                .enabled(true)
                .stroke(null);

        circularGauge3.label(0)
                .text("<span style=\"font-size: 25\">Rain</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge3.label(0)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(20, 0, 0, 0);

        circularGauge3.label(2)
                .text("<span style=\"font-size: 25\">%</span>")
                .useHtml(true)
                .fontColor("black")
                .hAlign(HAlign.CENTER);
        circularGauge3.label(2)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .padding(140, 0, 0, 0);
        circularGauge3.range(0,
                "{\n" +
                        "    from: 70,\n" +
                        "    to: 30,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'green 0.7',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge3.range(1,
                "{\n" +
                        "    from: 70,\n" +
                        "    to: 100,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'red 0.6',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge3.range(3,
                "{\n" +
                        "    from: 0,\n" +
                        "    to: 30,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'blue 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");
        circularGauge3.label(1)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(-100)
                .padding(5, 10, 0, 0)
                .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}");

        anyChartView3.setChart(circularGauge3);




        return root;
    }

    public void onStart () {
        super.onStart();

        View view = getView();




    }
}