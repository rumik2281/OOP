package by.bsuir.oop.lab_3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes(@JsonSubTypes.Type(value = Ship.class, name = "Ship"))
public abstract class WaterTransport extends Transport {
    private static final long serialVersionUID = -7604750566024451817L;
    private int maxTravelDistance;

    public WaterTransport(String transportClass, int length, int weight, Energy energy, int maxTravelDistance) {
        super(transportClass, length, weight, energy);
        this.maxTravelDistance = maxTravelDistance;
    }

    public WaterTransport() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WaterTransport that = (WaterTransport) o;
        return maxTravelDistance == that.maxTravelDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxTravelDistance);
    }

    public int getMaxTravelDistance() {
        return maxTravelDistance;
    }

    public void setMaxTravelDistance(int maxTravelDistance) {
        this.maxTravelDistance = maxTravelDistance;
    }
}
