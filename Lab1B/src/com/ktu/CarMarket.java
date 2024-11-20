/**
 * This is a class for car list building and follow-up.
 ****************************************************************************/
package com.ktu;

public class CarMarket {

    public CarList allCars = new CarList();

    // a list of cars that are manufactured later than the year limit entered
    public CarList getNewerCars(int fromYear) {
        CarList cars = new CarList();
        for (Car c : allCars) {
            if (c.getYear() >= fromYear) {
                cars.add(c);
            }
        }
        return cars;
    }

    // a list of cars whose price is between price limits entered
    public CarList getByPrice(int fromPrice, int toPrice) {
        CarList cars = new CarList();
        for (Car c : allCars) {
            if (c.getPrice() >= fromPrice && c.getPrice() <= toPrice) {
                cars.add(c);
            }
        }
        return cars;
    }

    // a list of cars with the highest mileage taken as priority
    public CarList getHighestMileageCars() {
        CarList cars = new CarList();

        double maxMileage = 0;
        for (Car c : allCars) {
            double mileage = c.getMileage();
            if (mileage >= maxMileage) {
                if (mileage > maxMileage) {
                    cars.clear();
                    maxMileage = mileage;
                }
                cars.add(c);
            }
        }
        return cars;
    }

    // form a list of cars whose model code corresponds to the specified
    public CarList getByMakeAndModel(String makeAndModel) {
        CarList cars = new CarList();
        for (Car c : allCars) {
            String carMakeAndModel = c.getMake() + " " + c.getModel();
            if (carMakeAndModel.startsWith(makeAndModel)) {
                cars.add(c);
            }
        }
        return cars;
    }

}
