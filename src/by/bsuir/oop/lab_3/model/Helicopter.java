package by.bsuir.oop.lab_3.model;

import java.io.Serializable;

public class Helicopter extends AirTransport implements Serializable {
    private static final long serialVersionUID = 8622394981138420807L;
    private int numOfBlades;

    public Helicopter(int length, int weight, Energy energy, int maxFlightAltitude, int numOfBlades) {
        super("Helicopter", length, weight, energy, maxFlightAltitude);
        this.numOfBlades = numOfBlades;
    }

    public Helicopter() {
    }

    public int getNumOfBlades() {
        return numOfBlades;
    }

    public void setNumOfBlades(int numOfBlades) {
        this.numOfBlades = numOfBlades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Helicopter helicopter = (Helicopter) o;

        return numOfBlades == helicopter.numOfBlades;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + numOfBlades;
        return result;
    }

    @Override
    public String toString() {
        return "Helicopter{" +
                "maxFuelVolume=" + numOfBlades +
                ", maxFlightAltitude=" + getMaxFlightAltitude() +
                ", length=" + getLength() +
                ", weight=" + getWeight() +
                ", energy=" + getEnergy() +
                ", transportClass='" + getType() + '\'' +
                '}';
    }
}
