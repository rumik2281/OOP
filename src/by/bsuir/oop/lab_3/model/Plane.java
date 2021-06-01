package by.bsuir.oop.lab_3.model;

import java.io.Serializable;

public class Plane extends AirTransport implements Serializable {
    private static final long serialVersionUID = -53773596830218132L;
    private int wingspan;
    private String company;

    public Plane(int length, int weight, Energy energy, int maxFlightAltitude, int wingspan, String company) {
        super("Plane", length, weight, energy, maxFlightAltitude);
        this.wingspan = wingspan;
        this.company = company;
    }

    public Plane() {
    }


    public int getWingspan() {
        return wingspan;
    }

    public void setWingspan(int wingspan) {
        this.wingspan = wingspan;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Plane plane = (Plane) o;

        if (wingspan != plane.wingspan) return false;
        return company != null ? company.equals(plane.company) : plane.company == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + wingspan;
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "wingspan=" + wingspan +
                ", company='" + company + '\'' +
                ", maxFlightAltitude=" + getMaxFlightAltitude() +
                ", length=" + getLength() +
                ", weight=" + getWeight() +
                ", energy=" + getEnergy() +
                ", transportClass='" + getType() + '\'' +
                '}';
    }
}
