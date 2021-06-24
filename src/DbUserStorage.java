import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbUserStorage {
    /**
     * Реализовать методы:
     */
    //save +
    //updateNameById +
    //updatePasswordById +
    //deleteById +
    //getAll +
    //getAllByName+
    //getByUsername +
    //getAllByStreet +
    //exist
    //existById
    public static void main(String[] args) {
        DbUserStorage dbUserStorage = new DbUserStorage();

//        dbUserStorage.save(new User(0, "Maxim", "Max", "password",
//                new Address(0, "5 Avenue"), new TelNumbers(0, "+19857777777")));

//       dbUserStorage.updateNameById(13, "Valera" );

//        dbUserStorage.updatePasswordById(13, "xxxxxxx");

//        dbUserStorage.deleteById(14);

//        dbUserStorage.getAll();
//        System.out.println(dbUserStorage.getAllByName("Maxim"));
    }

    public void save(User user) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {

            connection.setAutoCommit(false);

            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into addresses values (default , ?) returning id");
            preparedStatement1.setString(1, user.getAddress().getStreet());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            int newAddressId = resultSet.getInt(1);

            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into telephones values (default , ?) returning id");
            preparedStatement2.setString(1, user.getTelNumbers().getTelNum());
            ResultSet resultSet1 = preparedStatement2.executeQuery();
            resultSet1.next();
            int newTelId = resultSet1.getInt(1);

            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values (default , ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, newAddressId);
            preparedStatement.setInt(5, newTelId);
            preparedStatement.execute();

            connection.commit();


        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void updateNameById(int id, String name) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update users set name = ? where id = ?");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setString(1, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setInt(2, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void updatePasswordById(int id, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update users set password = ? where id = ?");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setString(1, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setInt(2, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("delete from users where id = ? ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Optional<List<User>> getAll() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet resultSet = statement.executeQuery("select * from users u\n" +
                    "join user_address_tel uat on u.id = uat.user_id\n" +
                    "join addresses a on uat.address_id = a.id\n" +
                    "join telephones t on uat.tel_id = t.id");
            resultSet.next();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                int idAddress = resultSet.getInt(10);
                String street = resultSet.getString(11);
                int telId = resultSet.getInt(12);
                String telNum = resultSet.getString(13);
                User user = new User(id, name, username, password, new Address(idAddress, street), new TelNumbers(telId, telNum));
                users.add(user);
            }
            return Optional.of(users);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<User>> getAllByName(String name) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from users u\n" +
                    "join user_address_tel uat on u.id = uat.user_id\n" +
                    "join addresses a on uat.address_id = a.id\n" +
                    "join telephones t on uat.tel_id = t.id\n" +
                    "where name = ? ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setString(1, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name1 = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                int idAddress = resultSet.getInt(10);
                String street = resultSet.getString(11);
                int telId = resultSet.getInt(12);
                String telNum = resultSet.getString(13);
                User user = new User(id, name1, username, password, new Address(idAddress, street), new TelNumbers(telId, telNum));
                users.add(user);
            }
            return Optional.of(users);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<User>> getByUserName(String username) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from users u\n" +
                    "join user_address_tel uat on u.id = uat.user_id\n" +
                    "join addresses a on uat.address_id = a.id\n" +
                    "join telephones t on uat.tel_id = t.id\n" +
                    "where username = ? ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setString(1, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name1 = resultSet.getString(2);
                String username1 = resultSet.getString(3);
                String password = resultSet.getString(4);
                int idAddress = resultSet.getInt(10);
                String street = resultSet.getString(11);
                int telId = resultSet.getInt(12);
                String telNum = resultSet.getString(13);
                User user = new User(id, name1, username1, password, new Address(idAddress, street), new TelNumbers(telId, telNum));
                users.add(user);
            }
            return Optional.of(users);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<User>> getAllByStreet (String street){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from users u\n" +
                    "join user_address_tel uat on u.id = uat.user_id\n" +
                    "join addresses a on uat.address_id = a.id\n" +
                    "join telephones t on uat.tel_id = t.id\n" +
                    "where street = ? ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setString(1, street);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name1 = resultSet.getString(2);
                String username1 = resultSet.getString(3);
                String password = resultSet.getString(4);
                int idAddress = resultSet.getInt(10);
                String street1 = resultSet.getString(11);
                int telId = resultSet.getInt(12);
                String telNum = resultSet.getString(13);
                User user = new User(id, name1, username1, password, new Address(idAddress, street1), new TelNumbers(telId, telNum));
                users.add(user);
            }
            return Optional.of(users);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();

    }
    public boolean exist (User user){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "reenGU8zcnAl");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            boolean execute = statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




    }
}
