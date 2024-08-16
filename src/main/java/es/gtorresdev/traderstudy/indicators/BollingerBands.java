package es.gtorresdev.traderstudy.indicators;

import candleChart.charts.candle.Candle;
import es.gtorresdev.traderstudy.models.ChartType;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class BollingerBands extends Indicator {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int movingAverage;
    private double k;
    private final List<Double> sma;
    private final List<Double> upperBand;
    private final List<Double> lowerBand;

    public BollingerBands() {
        movingAverage = 5;
        k = 2;
        sma = new ArrayList<>();
        upperBand = new ArrayList<>();
        lowerBand = new ArrayList<>();
    }

    public void preInitialize() {
        inputElement("Media móvil", movingAverage);

        addPaintElement(ChartType.LINE, sma);
    }

    @Override
    public void initialize(List<Candle> candleList) {
        addPaintElement(ChartType.LINE, sma);
        addPaintElement(ChartType.LINE, upperBand);
        addPaintElement(ChartType.LINE, lowerBand);

        for (int i = 0; i < candleList.size(); i++) {
            if (i >= movingAverage - 1) {
                // Calcular SMA
                double sum = 0.0;
                for (int j = i; j > i - movingAverage; j--) {
                    sum += candleList.get(j).closePrice();
                }
                double smaValue = sum / movingAverage;
                sma.add(smaValue);

                // Calcular Desviación Estándar
                double varianceSum = 0.0;
                for (int j = i; j > i - movingAverage; j--) {
                    varianceSum += Math.pow(candleList.get(j).closePrice() - smaValue, 2);
                }
                double stdDev = Math.sqrt(varianceSum / movingAverage);

                // Calcular Bandas Superior e Inferior
                double upperBandValue = smaValue + k * stdDev;
                double lowerBandValue = smaValue - k * stdDev;

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

