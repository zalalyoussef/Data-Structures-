/** @author Eimutis Karčiauskas, KTU IF Department of Software Engineering, 23/09/2014
        *
        * This is a class on working with lists for a speed study.
        * The example shows a study of sorting methods.
        * The methods presented in the study are used as templates,
        * testing various aspects of sorting.
        ******************************************************** ***************************/
package com.ktu;

import util.Ks;
import util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class SimpleBenchmark {

    Car[] cars;
    LinkedList<Car> carSeries = new LinkedList<>();
    Random rg = new Random();  // random generator
    int[] counts = {2_000, 4_000, 8_000, 16_000};
//    try to see if your computer passes these experiments
//    static int[] counts = {10_000, 20_000, 40_000, 80_000};

    void generateCars(int count) {
        String[][] makesAndModels = { // an array of possible car brands and models
            {"Mazda", "121", "323", "626", "MX6"},
            {"Ford", "Fiesta", "Escort", "Focus", "Sierra", "Mondeo"},
            {"Saab", "92", "96"},
            {"Honda", "Accord", "Civic", "Jazz"},
            {"Renault", "Laguna", "Megane", "Twingo", "Scenic"},
            {"Peugeot", "206", "207", "307"}
        };
        cars = new Car[count];
        rg.setSeed(2017);
        for (int i = 0; i < count; i++) {
            int makeIndex = rg.nextInt(makesAndModels.length);        // brand index 0 ..
            int modelIndex = rg.nextInt(makesAndModels[makeIndex].length - 1) + 1;// model index 1 ..
            cars[i] = new Car(makesAndModels[makeIndex][0], makesAndModels[makeIndex][modelIndex],
                    1994 + rg.nextInt(20), // years between 1994 and 2013
                    6000 + rg.nextInt(222_000), // mileage between 6000 and 228000
                    1000 + rg.nextDouble() * 350_000); // price between 1000 and 351_000
        }
        Collections.shuffle(Arrays.asList(cars));
        carSeries.clear();
        for (Car c : cars) {
            carSeries.add(c);
        }
    }

    void generateAndExecute(int elementCount) {
// The preparatory part of the study
        long t0 = System.nanoTime();
        generateCars(elementCount);
        LinkedList<Car> carSeries2 = carSeries.clone();
        LinkedList<Car> carSeries3 = carSeries.clone();
        LinkedList<Car> carSeries4 = carSeries.clone();
        long t1 = System.nanoTime();
        System.gc();
        System.gc();
        System.gc();
        long t2 = System.nanoTime();
//  Speed tests and time measurements
        carSeries.sortSystem();
        long t3 = System.nanoTime();
        carSeries2.sortSystem(Car.byPrice);
        long t4 = System.nanoTime();
        carSeries3.sortBuble();
        long t5 = System.nanoTime();
        carSeries4.sortBuble(Car.byPrice);
        long t6 = System.nanoTime();
        Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n", elementCount,
                (t1 - t0) / 1e9, (t2 - t1) / 1e9, (t3 - t2) / 1e9,
                (t4 - t3) / 1e9, (t5 - t4) / 1e9, (t6 - t5) / 1e9);
    }

    void execute() {
        long memTotal = Runtime.getRuntime().totalMemory();
        Ks.oun("memTotal= " + memTotal);
        // Let's see how generates cars (20) units)
        generateCars(20);
        for (Car c : carSeries) {
            Ks.oun(c);
        }
        Ks.oun("1 - Preparing for the study - data generation");
        Ks.oun("2 - Preparing for the test - garbage collection");
        Ks.oun("3 - Sort by system fast without Comparator");
        Ks.oun("4 - Sort systematically fast with Comparator");
        Ks.oun("5 - Sort List Bubble without Comparator");
        Ks.oun("6 - Sort List Bubble with Comparator");
        Ks.ouf("%6d %7d %7d %7d %7d %7d %7d \n", 0, 1, 2, 3, 4, 5, 6);
        for (int n : counts) {
            generateAndExecute(n);
        }
    }

    public static void main(String[] args) {
        // we unify number formats according to LT locale (10th decimal point)
        Locale.setDefault(new Locale("LT"));
        new SimpleBenchmark().execute();
    }
}
