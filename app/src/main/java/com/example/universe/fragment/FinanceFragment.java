//charts: https://github.com/AnyChart/AnyChart-Android

package com.example.universe.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.universe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinanceFragment extends Fragment {
    AnyChartView anyChartViewPie;
    AnyChartView anyChartViewLine;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FinanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinanceFragment newInstance(String param1, String param2) {
        FinanceFragment fragment = new FinanceFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_finance, container, false);

        //line chart for expenses

        //setup pie chart for expense
        Pie pie = AnyChart.pie();
        anyChartViewPie = fragmentView.findViewById(R.id.circle_chart);
        anyChartViewPie.setChart(pie);

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Income", 100000));
        data.add(new ValueDataEntry("Extra Income", 12543));
        data.add(new ValueDataEntry("Living Expenses", 53450));
        data.add(new ValueDataEntry("Extra Expenses", 3213));

        pie.data(data);



        // Inflate the layout for this fragment
        return fragmentView;
    }
}