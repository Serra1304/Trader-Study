package es.gtorresdev.traderstudy.indicators;

import candleChart.charts.base.Chart;
import candleChart.charts.candle.Candle;
import es.gtorresdev.traderstudy.models.*;
import es.gtorresdev.traderstudy.models.input.InputElement;
import es.gtorresdev.traderstudy.models.input.InputElementColor;
import es.gtorresdev.traderstudy.models.input.InputType;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.*;

public abstract class Indicator implements Serializable {

    private final List<ChartData> chartData;
    private final Map<String, InputElement> inputElementList;

    public Indicator() {
        chartData = new ArrayList<>();
        inputElementList = new LinkedHashMap<>();
    }
    
    protected Object get(String key) {
        return inputElementList.get(key).getDefaultValue();
    }

    protected Chart<?> addPaintElement(ChartType chartType, List<Double> dataList) {
        ChartData chart = new ChartData(chartType.createChart(), dataList);
        chartData.add(chart);

        return chart.getChartType();
    }

    public List<ChartData> getPaintingElements() {
        return chartData;
    }

    protected void inputInteger(String key, String description, int defaultValue) {
        inputElementList.put(key, new InputElement(InputType.INTEGER, description, defaultValue));
    }

    protected void inputDouble(String key, String description, double defaultValue) {
        inputElementList.put(key, new InputElement(InputType.DOUBLE, description, defaultValue));
    }

    protected void inputColor(String key, String description, Color defaultValue, Chart<?> chartColor) {
        inputElementList.put(key, new InputElementColor(InputType.COLOR, description, defaultValue, chartColor));
    }

    protected void inputColor(String key, String description, Color defaultValue, List<Chart<?>> chartsColor) {
        inputElementList.put(key, new InputElementColor(InputType.COLOR, description, defaultValue, chartsColor));
    }

    public Map<String, InputElement> getInputElements() {
        return inputElementList;
    }

    public abstract void configuration();
    public abstract void initialize(List<Candle> candleList);
}
