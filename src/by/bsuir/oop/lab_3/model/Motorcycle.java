package by.bsuir.oop.lab_3.model;

import java.io.Serializable;

public class Motorcycle extends GroundTransport implements Serializable {
    private static final long serialVersionUID = 117769314905316692L;
    private int engineCapacity;

    public Motorcycle(int length, int weight, Energy energy, int numberOfPassengers, int engineCapacity) {
        super("Motorcycle", length, weight, energy, numberOfPassengers);
        this.engineCapacity = engineCapacity;
    }

    public Motorcycle() {
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Motorcycle that = (Motorcycle) o;

        return engineCapacity == that.engineCapacity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + engineCapacity;
        return result;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "engineCapacity=" + engineCapacity +
                ", numberOfPassengers=" + getNumberOfPassengers() +
                ", length=" + getLength() +
                ", weight=" + getWeight() +
                ", energy=" + getEnergy() +
                ", transportClass='" + getType() + '\'' +
                '}';
    }
}
