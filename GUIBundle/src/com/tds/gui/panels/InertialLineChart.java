package com.tds.gui.panels;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class InertialLineChart extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 5694783209925104916L;

    private static int numOfSeconds = 300;

    private ArrayList<Float> yValuesX;
    private ArrayList<Float> yValuesY;
    private ArrayList<Float> yValuesZ;

    private JFreeChart lineChart;

    private ChartPanel chartPanel;

    public InertialLineChart() {
        yValuesX = new ArrayList<Float>();
        yValuesY = new ArrayList<Float>();
        yValuesZ = new ArrayList<Float>();
        lineChart = ChartFactory.createXYLineChart("", // Title
                "", // x-axis Label
                "", // y-axis Label
                getDataset(), // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                false, // Use tooltips
                false // Configure chart to generate URLs?
                );
        lineChart.getXYPlot().getDomainAxis().setRange(1, numOfSeconds);
        lineChart.getXYPlot().getDomainAxis().setInverted(true);
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(265, 141));
        this.add(chartPanel);
    }

    public void addElement(float elementX, float elementY, float elementZ) {
        // remove oldest element if 5 min. reached --> 300
        if (yValuesX.size() > numOfSeconds) {
            yValuesX.remove(yValuesX.size() - 1);
            yValuesY.remove(yValuesY.size() - 1);
            yValuesZ.remove(yValuesZ.size() - 1);
        }

        // add new Element to the end
        yValuesX.add(0, elementX);
        yValuesY.add(0, elementX);
        yValuesZ.add(0, elementX);
        lineChart.getXYPlot().setDataset(getDataset());
    }

    private XYSeriesCollection getDataset() {
        XYSeries seriesX = new XYSeries("X-Series");
        for (int i = 0; i < yValuesX.size(); i++) {
            seriesX.add(i, yValuesX.get(i));
        }

        XYSeries seriesY = new XYSeries("Y-Series");
        for (int i = 0; i < yValuesY.size(); i++) {
            seriesY.add(i, yValuesY.get(i));
        }

        XYSeries seriesZ = new XYSeries("Z-Series");
        for (int i = 0; i < yValuesZ.size(); i++) {
            seriesZ.add(i, yValuesZ.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesX);
        dataset.addSeries(seriesY);
        dataset.addSeries(seriesZ);
        return dataset;
    }

    public static int getNumOfSeconds() {
        return numOfSeconds;
    }

    public static void setNumOfSeconds(int numOfSeconds) {
        InertialLineChart.numOfSeconds = numOfSeconds;
    }

}
