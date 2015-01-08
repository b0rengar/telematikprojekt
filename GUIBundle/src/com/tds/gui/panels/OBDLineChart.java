package com.tds.gui.panels;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class OBDLineChart extends JPanel {

    private final static int numOfSeconds = 60;

    private ArrayList<Float> yValues;

    private JFreeChart lineChart;

    private ChartPanel chartPanel;

    /**
     *
     */
    private static final long serialVersionUID = 2667315241762678463L;

    public OBDLineChart() {
        yValues = new ArrayList<Float>();
        lineChart = ChartFactory.createXYLineChart("", // Title
                "", // x-axis Label
                "", // y-axis Label
                getDataset(), // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                false, // Use tooltips
                false // Configure chart to generate URLs?
                );
        lineChart.getXYPlot().getDomainAxis().setRange(1, numOfSeconds);
        lineChart.getXYPlot().getDomainAxis().setInverted(true);
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(265, 141));
        this.add(chartPanel);
    }

    public void addElement(float element) {
        // remove oldest element if 5 min. reached --> 300
        if (yValues.size() > numOfSeconds) {
            yValues.remove(yValues.size() - 1);
        }

        // add new Element to the end
        yValues.add(0, element);
        lineChart.getXYPlot().setDataset(getDataset());
    }

    private XYSeriesCollection getDataset() {
        XYSeries series = new XYSeries("DatasetName");
        for (int i = 0; i < yValues.size(); i++) {
            series.add(i, yValues.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}
