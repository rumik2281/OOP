package by.bsuir.oop.lab_3.model;

import java.io.Serializable;

public class Car extends GroundTransport implements Serializable {
    private static final long serialVersionUID = 2646523611819834046L;
    private int numOfDoors;
    private String brand;

    public Car(int length, int weight, Energy energy, int numberOfPassengers, int numOfDoors, String brand) {
        super("Car", length, weight, energy, numberOfPassengers);
        this.numOfDoors = numOfDoors;
        this.brand = brand;
    }

    public Car() {
    }

    public int getNumOfDoors() {
        return numOfDoors;
    }

    public void setNumOfDoors(int numOfDoors) {
        this.numOfDoors = numOfDoors;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Car car = (Car) o;

        if (numOfDoors != car.numOfDoors) return false;
        return brand != null ? brand.equals(car.brand) : car.brand == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + numOfDoors;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numOfDoors=" + numOfDoors +
                ", brand='" + brand + '\'' +
                ", numberOfPassengers=" + getNumberOfPassengers() +
                ", length=" + getLength() +
                ", weight=" + getWeight() +
                ", energy=" + getEnergy() +
                ", transportClass='" + getType() + '\'' +
                '}';
    }
}
