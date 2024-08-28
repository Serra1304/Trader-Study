package es.gtorresdev.traderstudy.indicators;

import candleChart.charts.base.Chart;
import candleChart.charts.candle.Candle;
import es.gtorresdev.traderstudy.models.ChartType;
import javafx.scene.paint.Color;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class BollingerBands extends Indicator {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Double> sma;
    private final List<Double> upperBand;
    private final List<Double> lowerBand;

    public BollingerBands() {
        sma = new ArrayList<>();
        upperBand = new ArrayList<>();
        lowerBand = new ArrayList<>();
    }

    @Override
    public void configuration() {
        List<Chart<?>> charts = new ArrayList<>();
        charts.add(addPaintElement(ChartType.LINE, sma));
        charts.add(addPaintElement(ChartType.LINE, upperBand));
        charts.add(addPaintElement(ChartType.LINE, lowerBand));

        inputInteger("period", "Periodo de media:", 1);
        inputDouble("deviation", "Desviación", 2);
        inputColor("color", "Color de BollingerBands", Color.AQUA, charts);
    }

    @Override
    public void initialize(List<Candle> candleList) {
        int period = (int) get("period");
        double deviation = (double) get("deviation");

        for (int i = 0; i < candleList.size(); i++) {
            if (i >= period - 1) {
                // Calcular SMA
                double sum = 0.0;
                for (int j = i; j > i - period; j--) {
                    sum += candleList.get(j).closePrice();
                }
                double smaValue = sum / period;
                sma.add(smaValue);

                // Calcular Desviación Estándar
                double varianceSum = 0.0;
                for (int j = i; j > i - period; j--) {
                    varianceSum += Math.pow(candleList.get(j).closePrice() - smaValue, 2);
                }
                double stdDev = Math.sqrt(varianceSum / period);

                // Calcular Bandas Superior e Inferior
                double upperBandValue = smaValue + deviation * stdDev;
                double lowerBandValue = smaValue - deviation * stdDev;

                upperBand.add(upperBandValue);
                lowerBand.add(lowerBandValue);
            } else {
                // Para los primeros elementos donde no se puede calcular la SMA, añadir null
                sma.add(null);
                upperBand.add(null);
                lowerBand.add(null);
            }
        }
    }
}

