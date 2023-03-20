package mvvmconsole.crmapp.repository;

import mvvmconsole.crmapp.dto.Admin;
import mvvmconsole.crmapp.dto.Customer;
import mvvmconsole.crmapp.dto.Interaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository data;
    private static final String url = "jdbc:mysql://localhost:3306/admin_db";
    String username = "root";
    String password = "root";
    private Connection conn;
    private Repository(){
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Repository getInstance(){
        if(data==null){
            data=new Repository();
        }
        return data;
    }




    public boolean addAdmin(Admin admin)  {
        String adminName = admin.getAdminName();
        String password = admin.getPassword();
        String query = "INSERT INTO admin (adminName, password) VALUES (?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, adminName);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Admin verifyAdmin(String userName, String password) {
        String query = "SELECT * FROM admin";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString("adminName");
                String pass = result.getString("password");
                if(name.equals(userName) && pass.equals(password)) {
                    return new Admin(name,pass);
                }
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String query1="SELECT COUNT(*) FROM interaction WHERE customer_id = ?";
                PreparedStatement statement1=conn.prepareStatement(query1);
                statement1.setInt(1,id);
                ResultSet rs=statement1.executeQuery();
                rs.next();
                int interactions=rs.getInt(1);
                customers.add(new Customer(id, name, email, phone,interactions));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Interaction> getAllInteraction() {
        List<Interaction> interactions = new ArrayList<>();
        String query = "SELECT * FROM interaction";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int customerId = resultSet.getInt("customer_id");
                String type = resultSet.getString("interaction_type");
                String notes = resultSet.getString("interaction_notes");
                interactions.add(new Interaction(id,customerId, type, notes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return interactions;
    }

    public boolean checkCustomerForInteraction(int customerId) {
        try {
            String query = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int addInteraction(Interaction interaction) {
        String query = "INSERT INTO interaction (customer_id, interaction_type, interaction_notes) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, interaction.getCustomerId());
            statement.setString(2, interaction.getType());
            statement.setString(3, interaction.getNotes());
            statement.executeUpdate();
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addCustomer(Customer customer) {
        try {
            String query = "INSERT INTO customer (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating customer failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer checkCustomerForUpdation(int id) {
        try {
            String query = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                return new Customer(id,name, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCustomer(int id, String name, String email, String phone) {
        try{
            String query = "UPDATE customer SET name = ?, email = ?, phone = ? WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, phone);
                statement.setInt(4, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Interaction checkInteractionForUpdation(int id) {
        try {
            String query = "SELECT * FROM interaction WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Interaction(resultSet.getInt("id"),resultSet.getInt("customer_id"), resultSet.getString("interaction_type"), resultSet.getString("interaction_notes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateInteraction(int customerId, int id, String type, String notes) {
        try {
            String query = "UPDATE interaction SET interaction_type = ?, interaction_notes = ? WHERE customer_id = ? AND id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, type);
                statement.setString(2, notes);
                statement.setInt(3, customerId);
                statement.setInt(4, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Interaction checkInteractionForDeletion(int id) {
        try {
            String query = "SELECT * FROM interaction WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int customerId = result.getInt("customer_id");
                String type = result.getString("interaction_type");
                String notes = result.getString("interaction_notes");
                return new Interaction(id, customerId, type, notes);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer checkCustomerForDeletion(int id) {
        try {
            String query = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                return new Customer(id1, name, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCustomer(int id) {
        try {
            String query = "DELETE FROM customer WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInteraction(int id) {
        String query = "DELETE FROM interaction WHERE id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("problem in exitting");
        }
    }
}

