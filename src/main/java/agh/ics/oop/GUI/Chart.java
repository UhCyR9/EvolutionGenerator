package agh.ics.oop.GUI;

import agh.ics.oop.Classes.EvolutionMap;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Chart {
    private static final int MAX_SIZE = 10;
    private EvolutionMap map;
    private LineChart<String,Number> lineChart;
    private XYChart.Series<String,Number> animalSeries;
    private XYChart.Series<String,Number> grassSeries;
    private XYChart.Series<String,Number> energySeries;
    private XYChart.Series<String,Number> lifetimeSeries;
    private XYChart.Series<String,Number> childrenSeries;
    private int epoch = 0;

    public Chart(EvolutionMap map)
    {
        this.map = map;

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("EPOKA");
        xAxis.setAnimated(false);
        yAxis.setLabel("WARTOSC");
        yAxis.setAnimated(false);

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("DANE");
        lineChart.setAnimated(false);

        animalSeries = new XYChart.Series<>();
        animalSeries.setName("Liczba zwierzat");
        lineChart.getData().add(animalSeries);

        grassSeries= new XYChart.Series<>();
        grassSeries.setName("Liczba trawy");
        lineChart.getData().add(grassSeries);

        energySeries = new XYChart.Series<>();
        energySeries.setName("Srednia energia");
        lineChart.getData().add(energySeries);

        lifetimeSeries = new XYChart.Series<>();
        lifetimeSeries.setName("Srednia dlugosc zycia");
        lineChart.getData().add(lifetimeSeries);

        childrenSeries = new XYChart.Series<>();
        childrenSeries.setName("Srednia ilosc dzieci");
        lineChart.getData().add(childrenSeries);
        this.lineChart = lineChart;
    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }

    public void updateChart()
    {
        ArrayList<XYChart.Series<String,Number>> series = new ArrayList<>();
        series.add(animalSeries);
        series.add(grassSeries);
        series.add(energySeries);
        series.add(lifetimeSeries);
        series.add(childrenSeries);

        epoch += 1;
        animalSeries.getData().add(new XYChart.Data<>(String.valueOf(epoch), map.numberOfAnimals()));
        grassSeries.getData().add(new XYChart.Data<>(String.valueOf(epoch), map.numberOfGrass()));
        energySeries.getData().add(new XYChart.Data<>(String.valueOf(epoch), map.averageEnergy()));
        lifetimeSeries.getData().add(new XYChart.Data<>(String.valueOf(epoch),map.getAverageLifeTime()));
        childrenSeries.getData().add(new XYChart.Data<>(String.valueOf(epoch), map.averageChildren()));

        for (XYChart.Series<String,Number> tmp : series)
        {
            if (tmp.getData().size() > MAX_SIZE)
            {
                tmp.getData().remove(0);
            }
        }
    }
}
