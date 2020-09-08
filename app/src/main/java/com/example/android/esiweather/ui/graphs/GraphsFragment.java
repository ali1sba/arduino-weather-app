package com.example.android.esiweather.ui.graphs;

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
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.android.esiweather.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GraphsFragment extends Fragment {

    final List<DataEntry> seriesData = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("DATA");
    private GraphsViewModel graphsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphsViewModel =
                ViewModelProviders.of(this).get(GraphsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graphs, container, false);
        final TextView textView = root.findViewById(R.id.text_graphs);
        graphsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        final AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));
        Query lastQuery = myRef.orderByKey().limitToLast(1);
        final Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 20d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);



        cartesian.yAxis(0).title("value (C° , % , pha , % )");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        cartesian.xScroller(true);
        final Set set = Set.instantiate();

        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String temp = "0" ;
                String rain = "0" ;
                String presion = "0" ;
                String humidty = "0" ;

                //double temp = Double.parseDouble( dataSnapshot.child("temp").getValue().toString());
                APIlib.getInstance().setActiveAnyChartView(anyChartView);

String title = "";
int b = 0;
                for (DataSnapshot timeKeySnapshot : dataSnapshot.getChildren()) {
                   // DataSnapshot valKeySnapshot = (DataSnapshot) timeKeySnapshot.getChildren();

                    if ((b % 5)==0) {

                      //  Log.d("tag", "hello");
                       // Log.d("tag", timeKeySnapshot.getValue().toString());

                        for (DataSnapshot valKeySnapshot : timeKeySnapshot.getChildren()) {
                            //   Log.d("tag", "hello");
                            title = timeKeySnapshot.getKey();
                            int w =1;
                            for (DataSnapshot valKeySnapshot2 : valKeySnapshot.getChildren()) {
                                if (w == 2) {
                                    //     Log.d("tag", valKeySnapshot.getValue().toString());
                                    temp = valKeySnapshot2.getValue().toString();
                                    Log.d("temp", temp);
                                } else if (w == 3) {
                                    humidty = valKeySnapshot2.getValue().toString();
                                    Log.d("humidty", humidty);
                                } else if (w == 5) {
                                    presion = valKeySnapshot2.getValue().toString();
                                    Log.d("presion", presion);
                                } else if (w == 8) {
                                    rain = valKeySnapshot2.getValue().toString();
                                    Log.d("rain", rain);
                                }


                                w++;
                       /* temp = (String) valKeySnapshot.getValue();
                        Log.d("tag",temp);
                        valKeySnapshot = (DataSnapshot) timeKeySnapshot.getChildren();
                        temp = (String) valKeySnapshot.getValue();
                        Log.d("tag",temp);
                        valKeySnapshot = (DataSnapshot) timeKeySnapshot.getChildren();
                        temp = (String) valKeySnapshot.getValue();
                        Log.d("tag",temp);*/
                            }
                            seriesData.add(new CustomDataEntry(valKeySnapshot.getKey(), Double.parseDouble(temp), Double.parseDouble(humidty), Double.parseDouble(presion),Double.parseDouble(rain)));
                        }


                        Log.d("tag",timeKeySnapshot.getKey().replaceAll("[\\D]",""));
                        Log.d("tag", "hello");
                        // Log.d("tag",valKeySnapshot.getKey());
                        //   rain = (String) valKeySnapshot.getValue();
                        //  wind = (String) valKeySnapshot.getValue();
                        // humidty = (String) valKeySnapshot.getValue();

                        //   Log.d("tag",rain);
                        //   Log.d("tag",wind);
                        //  Log.d("tag",humidty);
                        b++;
                        //
                    }

                    }
                cartesian.title("The chart of the sensors day : "+ title );
                Log.d("size",String.valueOf(seriesData.size()));
                set.data(seriesData);
                //anyChartView.setChart(cartesian);
                }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        seriesData.add(new CustomDataEntry("00:00:00", 0, 0, 0,0));
     /*   seriesData.add(new CustomDataEntry("1987", 7.1, 4.0, 4.1,28));
        seriesData.add(new CustomDataEntry("1988", 8.5, 6.2, 5.1,28));
        seriesData.add(new CustomDataEntry("1989", 9.2, 11.8, 6.5,28));
        seriesData.add(new CustomDataEntry("1990", 10.1, 13.0, 12.5,28));
        seriesData.add(new CustomDataEntry("1991", 11.6, 13.9, 18.0,28));
        seriesData.add(new CustomDataEntry("1992", 16.4, 18.0, 21.0,28));
        seriesData.add(new CustomDataEntry("1993", 18.0, 23.3, 20.3,28));
        seriesData.add(new CustomDataEntry("1994", 13.2, 24.7, 19.2,28));
        seriesData.add(new CustomDataEntry("1995", 12.0, 18.0, 14.4,28));
        seriesData.add(new CustomDataEntry("1996", 3.2, 15.1, 9.2,28));
        seriesData.add(new CustomDataEntry("1997", 4.1, 11.3, 5.9,28));
        seriesData.add(new CustomDataEntry("1998", 6.3, 14.2, 5.2,28));
        seriesData.add(new CustomDataEntry("1999", 9.4, 13.7, 4.7,28));
        seriesData.add(new CustomDataEntry("2000", 11.5, 9.9, 4.2,28));
        seriesData.add(new CustomDataEntry("2001", 13.5, 12.1, 1.2,28));
        seriesData.add(new CustomDataEntry("2002", 14.8, 13.5, 5.4,28));
        seriesData.add(new CustomDataEntry("2003", 16.6, 15.1, 6.3,28));
        seriesData.add(new CustomDataEntry("2004", 18.1, 17.9, 8.9,28));
        seriesData.add(new CustomDataEntry("2005", 17.0, 18.9, 10.1,28));
        seriesData.add(new CustomDataEntry("2006", 16.6, 20.3, 11.5,28));
        seriesData.add(new CustomDataEntry("2007", 14.1, 20.7, 12.2,28));
        seriesData.add(new CustomDataEntry("2008", 15.7, 21.6, 10,28));
        seriesData.add(new CustomDataEntry("2009", 12.0, 22.5, 8.9,28));*/


        set.data( seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("temperature");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("humidity");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("presser");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series4 = cartesian.line(series4Mapping);
        series4.name("rain");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
        return root;
    }
    private static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3,Number value4) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }



    }

}
