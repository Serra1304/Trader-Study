package es.gtorresdev.traderstudy.models;

import candleChart.charts.bar.BarChart;
import candleChart.charts.base.Chart;
import candleChart.charts.curvedLine.CurvedLineChart;
import candleChart.charts.line.LineChart;

public enum ChartType {
    LINE(LineChart.class),
    CURVED_LINE(CurvedLineChart.class),
    BAR(BarChart.class);

    private final Class<? extends Chart<?>> chartClass;

    ChartType(Class<? extends Chart<?>> chartClass) {
        this.chartClass = chartClass;
    }

    public Chart<?> createChart() {
            try {
                return  chartClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("No se pudo crear la instancia del gráfico", e);
            }
    }

    public Class<?> getChartType() {
        return chartClass;
    }
}
