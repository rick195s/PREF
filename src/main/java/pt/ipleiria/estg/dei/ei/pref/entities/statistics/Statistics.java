package pt.ipleiria.estg.dei.ei.pref.entities.statistics;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartDataset;

import java.util.List;

public class Statistics {
    public String title;
    public String value;

    public List<ChartDataset> chartDatasets;

    // Construtor
    public Statistics(String title, String value, List<ChartDataset> chartDatasets) {
        this.title = title;
        this.value = value;
        this.chartDatasets = chartDatasets;
    }
}