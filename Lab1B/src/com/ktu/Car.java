/** @author Eimutis Karčiauskas, KTU IF Department of Software Engineering, 23/09/2014
        *
        * This is a demonstration car class (its objects are placed in the LinkedList),
        * which implements the Parsable <T> interface.
        ******************************************************** ****************************/
package com.ktu;

import util.Ks;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import util.Parsable;

public class Car implements Parsable<Car> {

    // general data for all cars (for the whole class)
    final static private int minYear = 1994;

    final static private double minPrice = 200.0;
    final static private double maxPrice = 120_000.0;

    // individual data for each car
    private String make;
    private String model;
    private int year;
    private int mileage;
    private double price;

    public Car() {
    }

    public Car(String make, String model,
            int year, int mileage, double price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }

    public Car(String data) {
        parse(data);
    }

    @Override
    public final void parse(String data) {
        try {   // ed is elementary data separated by spaces
            Scanner ed = new Scanner(data);
            make = ed.next();
            model = ed.next();
            year = ed.nextInt();
            mileage = ed.nextInt();
            setPrice(ed.nextDouble());
        } catch (InputMismatchException e) {
            Ks.ern("Bad data format on auto -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Auto data missing -> " + data);
        }
    }

    public String validate() {
        String error = "";
        int currentYear = LocalDate.now().getYear();
        if (year < minYear || year > currentYear) {
            error = "Invalid year of manufacture assigned ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            error += " Invalid price assigned [" + minPrice
                    + ":" + maxPrice + "]";
        }
        return error;
    }

    @Override
    public String toString() {  // all necessary information is collected as string
        return String.format("%-8s %-8s %4d %7d %8.1f %s",
                make, model, year, mileage, price, validate());
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public double getPrice() {
        return price;
    }

    // it will be possible to change only the price - other parameters are constant
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Car otherCar) {
        // we compare according to the price
        double otherPrice = otherCar.getPrice();
        if (price < otherPrice) {
            return -1;
        }
        if (price > otherPrice) {
            return +1;
        }
        return 0;
    }

    // initially compare by brand and then by model
    public final static Comparator<Car> byMakeAndModel
            = Comparator.comparing(Car::getMake).thenComparing(Car::getModel);

    public final static Comparator<Car> byPrice = (car1, car2) -> {
        double price1 = car1.getPrice();
        double price2 = car2.getPrice();
        // ascending order, lowest first
        if (price1 < price2) {
            return -1;
        }
        if (price1 > price2) {
            return 1;
        }
        return 0;
    };

    public final static Comparator<Car> byYearAndPrice = (car1, car2) -> {
        // years in descending order at the same comparable price
        if (car1.getYear() < car2.getYear()) {
            return 1;
        }
        if (car1.getYear() > car2.getYear()) {
            return -1;
        }
        if (car1.getPrice() < car2.getPrice()) {
            return 1;
        }
        if (car1.getPrice() > car2.getPrice()) {
            return -1;
        }
        return 0;
    };

    //  main method = just a simple initial test of cars
    public static void main(String... args) {
        // we unify number formats according to LT locale (10th decimal point)
        Locale.setDefault(new Locale("LT"));
        Car a1 = new Car("Renault", "Laguna", 1997, 50000, 599);
        Car a2 = new Car("Renault", "Megane", 2015, 20000, 3500);
        Car a3 = new Car();
        Car a4 = new Car();
        a3.parse("Toyota Corolla 2001 20000 8500,8");
        a4.parse("Toyota Avensis 1990 20000  500,8");
        Ks.oun(a1);
        Ks.oun(a2);
        Ks.oun(a3);
        Ks.oun(a4);
    }
}
