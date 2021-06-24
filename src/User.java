import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private Address address;
    private TelNumbers telNumbers;

    public User(int id, String name, String username, String password, Address address, TelNumbers telNumbers) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.telNumbers = telNumbers;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", telNumbers=" + telNumbers +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public TelNumbers getTelNumbers() {
        return telNumbers;
    }

    public void setTelNumbers(TelNumbers telNumbers) {
        this.telNumbers = telNumbers;
    }
}
