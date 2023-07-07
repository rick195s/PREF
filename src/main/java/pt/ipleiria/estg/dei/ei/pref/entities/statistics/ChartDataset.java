package pt.ipleiria.estg.dei.ei.pref.entities.statistics;

import java.util.List;

public class ChartDataset {
    public String chartTitle;
    public List<ChartData> data;

    // Construtor
    public ChartDataset(String chartTitle, List<ChartData> data) {
        this.chartTitle = chartTitle;
        this.data = data;
    }
}
