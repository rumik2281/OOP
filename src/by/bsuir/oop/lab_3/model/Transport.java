package by.bsuir.oop.lab_3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AirTransport.class, name = "AirTransport"),
        @JsonSubTypes.Type(value = GroundTransport.class, name = "GroundTransport"),
        @JsonSubTypes.Type(value = WaterTransport.class, name = "UnderwaterTransport")}
)
public abstract class Transport implements Serializable {
    private static final long serialVersionUID = -6462983749487371892L;
    private String type;
    private int length;
    private int weight;
    private Energy energy;

    public Transport(String type, int length, int weight, Energy energy) {
        this.type = type;
        this.length = length;
        this.weight = weight;
        this.energy = energy;
    }

    public Transport() {
    }

    public int getLength() {
        return length;
    }

    public int getWeight() {
        return weight;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transport transport = (Transport) o;

        if (length != transport.length) return false;
        if (weight != transport.weight) return false;
        if (type != null ? !type.equals(transport.type) : transport.type != null) return false;
        return energy == transport.energy;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + weight;
        result = 31 * result + (energy != null ? energy.hashCode() : 0);
        return result;
    }
}
