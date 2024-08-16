package es.gtorresdev.traderstudy.models;

import candleChart.charts.base.Chart;

import java.util.List;

public class ChartData {
    private final Chart<?> chart;
    private final List<Double> dataList;

    public ChartData(Chart<?> chart, List<Double> dataList) {
        this.chart = chart;
        this.dataList = dataList;
    }

    public Chart<?> getChartType() {
        return chart;
    }

    public List<Double> getDataList() {
        return dataList;
    }
}
