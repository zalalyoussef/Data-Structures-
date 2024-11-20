/** @author Eimutis Karčiauskas, KTU IF Department of Software Engineering, 23/09/2014
 *
 * This is a demonstration car list testing class that is designed simply
 * for testing actions with lists.
 *************************************************************************** */
package com.ktu;

import util.Ks;
import java.util.Comparator;
import java.util.Locale;

public class ManualTest {

    CarList cars = new CarList();

    void execute() {
        createCars();
        createCarList();
//        countRenault();
//        appendCarList();
//        checkCarMarketFilters();
//        checkCarMarketSorting();
    }

    void createCars() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        Car c4 = new Car();
        Car c5 = new Car();
        Car c6 = new Car();
        c4.parse("Renault Laguna 2001 115900 7500");
        c5.parse("Renault Megane 1946 365100 9500");
        c6.parse("Honda   Civic  2007  36400 8500,3");

        Ks.oun(c1);
        Ks.oun(c2);
        Ks.oun(c3);
        Ks.oun("Average mileage of the first 3 cars "
                + (c1.getMileage() + c2.getMileage() + c3.getMileage()) / 3);
        Ks.oun(c4);
        Ks.oun(c5);
        Ks.oun(c6);
        Ks.oun("Sum of other 3 car prices"
                + (c4.getPrice() + c5.getPrice() + c6.getPrice()));
    }

    void createCarList() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        cars.println("First 3 cars");
        cars.add("Renault Laguna 2001 115900 7500");
        cars.add("Renault Megane 1946 365100 9500");
        cars.add("Honda   Civic  2007  36400 8500,3");

        cars.println("All 6 cars");
        cars.forEach(System.out::println);
        Ks.oun("Average mileage of the first 3 cars"
                + (cars.get(0).getMileage() + cars.get(1).getMileage()
                + cars.get(2).getMileage()) / 3);

        Ks.oun("Sum of other 3 car prices "
                + (cars.get(3).getPrice() + cars.get(4).getPrice()
                + cars.get(5).getPrice()));
        // Gradually uncover the following lines and try
//        cars.add(0, new Car("Mazda","6",2007,50000,27000));
//        cars.add(6, new Car("Hyundai","Lantra",1998,9500,777));
//        cars.set(4, c3);
//        cars.println("After insertions");
//        cars.remove(7);
//        cars.remove(0);
//        cars.println("After deletions");
//        cars.remove(0); cars.remove(0); cars.remove(0);
//        cars.remove(0); cars.remove(0); cars.remove(0);
//        cars.println("After all deletions");
//        cars.remove(0);
//        cars.println("After all deletions");
    }

    void countRenault() {
        int sk = 0;
        for (Car c : cars) {
            if (c.getMake().compareTo("Renault") == 0) {
                sk++;
            }
        }
        Ks.oun("Renault cars are available = " + sk);
    }

    void appendCarList() {
        for (int i = 0; i < 8; i++) {
            cars.add(new Car("Ford", "Focus",
                    2009 - i, 40000 + i * 10000, 36000 - i * 2000));
        }
        cars.add("Ford Mondeo  2009 37000 36000.0");
        cars.add("Fiat Bravo   2008 27000 32500,0");
        cars.add("Ford Fiesta  2009 37000 16000,0");
        cars.add("Audi A6      2006 87000 36000,0");
        cars.println("List of tested cars");
        cars.save("ban.txt");
    }
    
    void checkCarMarketFilters() {
        CarMarket market = new CarMarket();

        market.allCars.load("ban.txt");
        market.allCars.println("Test kit");

        cars = market.getNewerCars(2001);
        cars.println("Starting in 2001");

        cars = market.getByPrice(3000, 10000);
        cars.println("Price between 3000 and 10,000");

        cars = market.getHighestMileageCars();
        cars.println("Most drove");

        cars = market.getByMakeAndModel("F");
        cars.println("There should only be Fiat and Ford");

        cars = market.getByMakeAndModel("Ford M");

        cars.println("Must only be a Ford Mondeo");
        int n = 0;
        for (Car c : cars) {
            n++;    // we test the operation of the cycle
        }
        Ks.oun("Ford Mondeo quantity =" + n);
    }

    void checkCarMarketSorting() {
        CarMarket market = new CarMarket();

        market.allCars.load("ban.txt");
        Ks.oun("========" + market.allCars.get(0));
        market.allCars.println("Test kit");
        market.allCars.sortBuble(Car.byMakeAndModel);
        market.allCars.println("Sort by Brand and Model");
        market.allCars.sortBuble(Car.byPrice);
        market.allCars.println("Sort by price");
        market.allCars.sortBuble(Car.byYearAndPrice);
        market.allCars.println("Sort by Year and Price");
        market.allCars.sortBuble(byMileage);
        market.allCars.sortBuble(Comparator.comparingInt(Car::getMileage));
        market.allCars.println("Sort by Ride");
        market.allCars.sortBuble();
        market.allCars.println("Sort by compareTo - Price");
    }

    static Comparator<Car> byMileage = (car1, car2) -> {
        int m1 = car1.getMileage();
        int m2 = car2.getMileage();
        // mileage in reverse descending order, starting from the highest
        if (m1 < m2) {
            return 1;
        }
        if (m1 > m2) {
            return -1;
        }
        return 0;
    };

    public static void main(String... args) {
        // we unify number formats according to LT locale (10th decimal point)
        Locale.setDefault(new Locale("LT"));
        new ManualTest().execute();
    }
}
