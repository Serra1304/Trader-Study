package es.gtorresdev.traderstudy.indicators;

import candleChart.charts.candle.Candle;
import es.gtorresdev.traderstudy.models.ChartData;
import es.gtorresdev.traderstudy.models.ChartType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Indicator implements Serializable {

    private final List<ChartData> chartData;

    public Indicator() {
        chartData = new ArrayList<>();
    }

    protected void addPaintElement(ChartType chartType, List<Double> dataList) {
        chartData.add(new ChartData(chartType.createChart(), dataList));
    }

    public List<ChartData> getPaintingElements() {
        return chartData;
    }

    protected void inputElement(String description, int data) {

    }

    public abstract void initialize(List<Candle> candleList);
}
