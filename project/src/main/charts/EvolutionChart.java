package main.charts;


import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.*;

public class EvolutionChart {

    XYChart chart;

    List<Integer> generations;
    List<Double> bestFinessList;
    List<Double> meanFinessList;

    final SwingWrapper<XYChart> sw;

    boolean displayCharts;

    public EvolutionChart(long seed) {
        this.chart = QuickChart.getChart("Evolution (seed = " + String.valueOf(seed) + ")", "Generations", "Fitness", "Best fitness", new double[]{0}, new double[]{0});
        XYSeries series = chart.addSeries("Mean fitness", new double[]{0});
        series.setMarker(SeriesMarkers.NONE);
        this.sw = new SwingWrapper<XYChart>(chart);


        this.generations = new ArrayList<>();
        this.bestFinessList = new ArrayList<>();
        this.meanFinessList = new ArrayList<>();
    }

    public void repaintChart() {
        if(displayCharts) {
            this.sw.repaintChart();
        }
    }

    public void updateData(int generation, double bestFitness, double meanFitness) {
        generations.add(generation);
        bestFinessList.add(bestFitness);
        meanFinessList.add(meanFitness);

        chart.updateXYSeries("Best fitness", generations, bestFinessList, null);
        chart.updateXYSeries("Mean fitness", generations, meanFinessList, null);
        repaintChart();
    }

    public void display(boolean displayCharts) {
        this.displayCharts = displayCharts;
        if(displayCharts) {
            sw.displayChart();
        }
    }
}
