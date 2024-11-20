import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        int[] inputSizes = {1000000, 2000000, 3000000, 4000000, 5000000};
        List<Long> durations = new ArrayList<>();


        for (int size : inputSizes) {
            int[] inputArray = generateRandomArray(size);
            long duration = 0;
            for (int i = 0; i < 99; i++) {
                long startTime = System.nanoTime();
                boolean happyArray = isHappyArray(inputArray);
                long endTime = System.nanoTime();
                duration += endTime - startTime;
            }

            durations.add(duration / 99);
            System.out.println("Input size: " + size + ", Time: " + duration + " ns");
        }

        SwingUtilities.invokeLater(() -> createAndShowGraph(inputSizes, durations));
    }


    public static boolean isHappyArray(int[] arr) {
        int totalSum = 0;

        for (int num : arr) {
            totalSum += num;
        }

        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            int rightSum = totalSum - leftSum;
            if (leftSum == rightSum) {
                return true;
            }
            leftSum += arr[i];
        }

        return false;
    }

    private static void createAndShowGraph(int[] inputSizes, List<Long> durations) {
        XYSeries series = new XYSeries("Array Compression");
        for (int i = 0; i < inputSizes.length; i++) {
            series.add(inputSizes[i], durations.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Array Compression Performance",
                "Input Size",
                "Time (ns)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Array Compression Performance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }
}






