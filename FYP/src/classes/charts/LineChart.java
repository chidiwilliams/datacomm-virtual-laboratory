/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.charts;

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

    private static final long serialVersionUID = 1L;
    public LineChart(String applicationTitle, Integer width, Integer height) {
        super(applicationTitle);
        
        panelWidth = width;
        panelHeight = height;
        makeChart("", null, "", "", -1, -1, -1);
    }
    
    private final int panelWidth;
    private final int panelHeight;
    
    private void makeChart(String chartTitle, XYDataset dataset, 
            String xlabel, String ylabel, double xTickUnits, 
            double yTickUnits, double xLowerBound) {
        
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
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(new Color(230, 230, 230));
        
        ChartPanel chartPanel = new ChartPanel(lineChart);
//        chartPanel.setBackground(Color.WHITE);
        chartPanel.setPreferredSize(new java.awt.Dimension( panelWidth , panelHeight ));
        setContentPane(chartPanel);
    }
    
    public void sendData(String chartTitle, double[] x, double[] y, String xlabel, 
            String ylabel, double xTickUnits, double yTickUnits, double xLowerBound) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("x and y arrays must have equal lengths.");
        }
        
        final XYSeries samples = new XYSeries( "Samples" );
        
        for (int i = 0; i < x.length; i++) {
            samples.add(x[i], y[i]);
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(samples);
        
        makeChart(chartTitle, dataset, xlabel, ylabel, xTickUnits, yTickUnits, xLowerBound);
    }
}
