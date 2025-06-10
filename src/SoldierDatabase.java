import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoldierDatabase {
    private static final String DB_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12783983";
    private static final String DB_USER = "sql12783983";
    private static final String DB_PASSWORD = "Gtfr5IvjcR";

    public SoldierDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS soldiers (" +
                    "id VARCHAR(50) PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "rank VARCHAR(50), " +
                    "unit VARCHAR(50), " +
                    "latitude DOUBLE, " +
                    "longitude DOUBLE, " +
                    "status VARCHAR(20)" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void addSoldier(Soldier s) {
        String sql = "REPLACE INTO soldiers (id, name, rank, unit, latitude, longitude, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getRank());
            ps.setString(4, s.getUnit());
            ps.setDouble(5, s.getLatitude());
            ps.setDouble(6, s.getLongitude());
            ps.setString(7, s.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Soldier> getAllSoldiers() {
        List<Soldier> list = new ArrayList<>();
        String sql = "SELECT * FROM soldiers";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Soldier s = new Soldier(
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("rank"),
                        rs.getString("unit"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
            System.out.println("[DEBUG] Loaded soldiers from DB: " + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateSoldierStatus(String id, String status) {
        String sql = "UPDATE soldiers SET status=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
