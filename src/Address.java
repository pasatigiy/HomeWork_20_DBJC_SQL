import java.util.Objects;

public class Address {
    private int idAddress;
    private String street;

    public Address(int idAddress, String street) {
        this.idAddress = idAddress;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idAddress=" + idAddress +
                ", street='" + street + '\'' +
                '}';
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return idAddress == address.idAddress && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAddress, street);
    }
}
