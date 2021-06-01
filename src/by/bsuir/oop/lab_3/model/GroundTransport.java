package by.bsuir.oop.lab_3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "Car"),
        @JsonSubTypes.Type(value = Motorcycle.class, name = "Motorcycle")}
)
public abstract class GroundTransport extends Transport {
    private static final long serialVersionUID = 3540373522570284518L;
    private int numberOfPassengers;

    public GroundTransport(String transportClass, int length, int weight, Energy energy, int numberOfPassengers) {
        super(transportClass, length, weight, energy);
        this.numberOfPassengers = numberOfPassengers;
    }

    public GroundTransport() {
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GroundTransport that = (GroundTransport) o;

        return numberOfPassengers == that.numberOfPassengers;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + numberOfPassengers;
        return result;
    }
}
