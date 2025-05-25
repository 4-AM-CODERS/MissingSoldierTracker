public class Soldier {
    private String name;
    private String id;
    private String rank;
    private String unit;
    private double latitude;
    private double longitude;
    private String status; // "active" or "missing"

    public Soldier(String name, String id, String rank, String unit, double latitude, double longitude) {
        this.name = name;
        this.id = id;
        this.rank = rank;
        this.unit = unit;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = "active";
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public String getUnit() {
        return unit;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
