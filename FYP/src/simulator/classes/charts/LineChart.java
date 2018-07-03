/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.classes.charts;

import java.awt.Color;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author HP
 */
public class LineChart extends ApplicationFrame {
    public LineChart(String applicationTitle, Integer width, Integer height) {
        super(applicationTitle);
        
        panelWidth = width;
        panelHeight = height;
        makeChart("", null, "", "", -1, -1, lineChartType.DEFAULT, -1);
    }
    
    private final int panelWidth;
    private final int panelHeight;
    
    private void makeChart(String chartTitle, XYDataset dataset, 
            String xlabel, String ylabel, double xTickUnits, 
            double yTickUnits, lineChartType chartType, double xLowerBound) {
        
        JFreeChart lineChart = ChartFactory.createXYLineChart(
            chartTitle,
            xlabel, ylabel,
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );
        
        final XYPlot plot = lineChart.getXYPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
        if (xTickUnits > -1) {
            NumberAxis categoryAxis = (NumberAxis) plot.getDomainAxis();
            categoryAxis.setTickUnit(new NumberTickUnit(xTickUnits));
            categoryAxis.setTickMarksVisible(true);    
        }
        
        if (yTickUnits > -1) {
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setTickUnit(new NumberTickUnit(yTickUnits));
            rangeAxis.setTickMarksVisible(true);
        }
        
        if (xLowerBound > -1) {
            NumberAxis categoryAxis = (NumberAxis) plot.getDomainAxis();
            categoryAxis.setLowerBound(xLowerBound);
        }
        
        if (chartType == lineChartType.POINTS) {
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesLinesVisible(0, false);
            renderer.setSeriesPaint(0, Color.RED);
            plot.setRenderer(renderer); 
        }
        
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension( panelWidth , panelHeight ));
        setContentPane(chartPanel);
    }
    
    public void sendData(String chartTitle, double[] x, double[] y, 
            String xlabel, String ylabel, double xTickUnits, double yTickUnits, 
            lineChartType chartType, double xLowerBound) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("x and y arrays must have equal lengths.");
        }
        
        final XYSeries samples = new XYSeries( "Samples" );
        
        for (int i = 0; i < x.length; i++) {
            samples.add(x[i], y[i]);
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(samples);
        
        makeChart(chartTitle, dataset, xlabel, ylabel, xTickUnits, yTickUnits, chartType, xLowerBound);
    }
    
    public static void main(String[] args) {
    }
}
