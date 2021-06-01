package by.bsuir.oop.lab_3.model;

import java.io.Serializable;

public class Ship extends WaterTransport implements Serializable {
    private static final long serialVersionUID = 8267414414146049563L;
    private int maximumSpeed;
    private int displacement;

    public Ship(int length, int weight, Energy energy, int maxTravelDistance, int maximumSpeed, int displacement) {
        super("Ship", length, weight, energy, maxTravelDistance);
        this.maximumSpeed = maximumSpeed;
        this.displacement = displacement;
    }

    public Ship() {
    }


    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ship ship = (Ship) o;

        if (maximumSpeed != ship.maximumSpeed) return false;
        return displacement == ship.displacement;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + maximumSpeed;
        result = 31 * result + displacement;
        return result;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "maximumSpeed=" + maximumSpeed +
                ", displacement=" + displacement +
                ", maxTravelDistance=" + getMaxTravelDistance() +
                ", length=" + getLength() +
                ", weight=" + getWeight() +
                ", energy=" + getEnergy() +
                ", transportClass='" + getType() + '\'' +
                '}';
    }
}
