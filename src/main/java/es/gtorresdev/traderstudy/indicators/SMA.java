package es.gtorresdev.traderstudy.indicators;

import candleChart.charts.candle.Candle;
import es.gtorresdev.traderstudy.models.ChartType;
import javafx.scene.paint.Color;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class SMA extends Indicator {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Double> dataList;

    public SMA() {
        dataList = new ArrayList<>();
    }

    @Override
    public void configuration() {
        inputInteger("period", "Periodo de la media:", 1);
        inputColor("color", "Color de media:", Color.RED, addPaintElement(ChartType.LINE, dataList));

        //addPaintElement(ChartType.LINE, dataList);
    }
    
    @Override
    public void initialize(List<Candle> candleList) {
        int period = (int) get("period");
        //addPaintElement(ChartType.LINE, (Color) get("color"), dataList);

        double sum = 0;
        for (int i = 0; i < candleList.size(); i++) {
            sum += candleList.get(i).closePrice();

            if ( i >= period) {
                sum -= candleList.get(i - period).closePrice();
            }

            if (i < period - 1) {
                dataList.add(null);
            } else {
                double average = sum / period;
                dataList.add(average);
            }
        }
    }
}
