package by.bsuir.oop.lab_3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Helicopter.class, name = "Helicopter"),
        @JsonSubTypes.Type(value = Plane.class, name = "Plane")}
)
public abstract class AirTransport extends Transport {
    private static final long serialVersionUID = -1145034483433917599L;
    private int maxFlightAltitude;

    public AirTransport(String transportClass, int length, int weight, Energy energy, int maxFlightAltitude) {
        super(transportClass, length, weight, energy);
        this.maxFlightAltitude = maxFlightAltitude;
    }

    public AirTransport() {
    }

    public int getMaxFlightAltitude() {
        return maxFlightAltitude;
    }

    public void setMaxFlightAltitude(int maxFlightAltitude) {
        this.maxFlightAltitude = maxFlightAltitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AirTransport that = (AirTransport) o;

        return maxFlightAltitude == that.maxFlightAltitude;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + maxFlightAltitude;
        return result;
    }
}
