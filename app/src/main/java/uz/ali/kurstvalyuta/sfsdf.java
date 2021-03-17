package uz.ali.kurstvalyuta;

import android.widget.ArrayAdapter;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;

class sfsdf {
    GraphView graphView;
    void as(){
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                return super.formatLabel(value, isValueX);
            }
        });

        DataPoint[] dataPoints=new DataPoint[5];
        dataPoints[0]=new DataPoint(0.1,0.1);
        for (int i=0;i<10;i++){

        }


       graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
           @Override
           public void setViewport(Viewport viewport) {
               super.setViewport(viewport);
           }

           @Override
           public String formatLabel(double value, boolean isValueX) {
               return super.formatLabel(value, isValueX);
           }
       });
    }
}
