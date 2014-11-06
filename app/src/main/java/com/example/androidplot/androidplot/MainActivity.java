package com.example.androidplot.androidplot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;


public class MainActivity extends Activity {

    private XYPlot plot;

    final String[] labels = {"Point 1", "Point 2", "Point 3", "Point 4", "Point 5"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        plot.getGraphWidget().setRangeValueFormat(new GraphXLabelFormat());

        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),  R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),  R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }

    class GraphXLabelFormat extends Format {

        private int index = 0;

        @Override
        public StringBuffer format(Object arg0, StringBuffer arg1, FieldPosition arg2) {
            int parsedInt = Math.round(Float.parseFloat(arg0.toString()));
            Log.d("test", parsedInt + " " + arg1 + " " + arg2);
            String labelString = labels[index];
            index ++;
            arg1.append(labelString);
            return arg1;
        }

        @Override
        public Object parseObject(String arg0, ParsePosition arg1) {
            return java.util.Arrays.asList(labels).indexOf(arg0);
        }
    }

}
