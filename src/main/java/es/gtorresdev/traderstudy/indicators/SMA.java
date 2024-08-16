package es.gtorresdev.traderstudy.indicators;

import candleChart.charts.candle.Candle;
import es.gtorresdev.traderstudy.models.ChartType;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class SMA extends Indicator {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int movingAverage;
    private final List<Double> dataList;

    public SMA() {
        movingAverage = 5;
        dataList = new ArrayList<>();
    }

    public void preInitialize() {
        inputElement("Media móvil", movingAverage);

        addPaintElement(ChartType.LINE, dataList);
    }
    
    @Override
    public void initialize(List<Candle> candleList) {
        addPaintElement(ChartType.LINE, dataList);
        double sum = 0;
        for (int i = 0; i < candleList.size(); i++) {
            sum += candleList.get(i).closePrice();

            if ( i >= movingAverage) {
                sum -= candleList.get(i - movingAverage).closePrice();
            }

            if (i < movingAverage - 1) {
                dataList.add(null);
            } else {
                double average = sum / movingAverage;
                dataList.add(average);
            }
        }
    }
}
