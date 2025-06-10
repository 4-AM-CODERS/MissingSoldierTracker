import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Soldier> soldiers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMissing Soldier Tracker");
            System.out.println("1. Register Soldier");
            System.out.println("2. View Soldiers");
            System.out.println("3. Mark Soldier as Missing");
            System.out.println("4. Remove Soldier from Missing");
            System.out.println("5. View Full Details of a Soldier");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Rank: ");
                    String rank = scanner.nextLine();
                    System.out.print("Enter Unit: ");
                    String unit = scanner.nextLine();
                    System.out.print("Enter Latitude: ");
                    double latitude = scanner.nextDouble();
                    System.out.print("Enter Longitude: ");
                    double longitude = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    Soldier soldier = new Soldier(name, id, rank, unit, latitude, longitude);
                    soldiers.add(soldier);
                    System.out.println("Soldier registered successfully!");
                    break;

                case 2:
                    System.out.println("\nRegistered Soldiers:");
                    for (Soldier s : soldiers) {
                        System.out.println(s.getName() + " (" + s.getId() + ") - " + s.getStatus());
                    }
                    break;

                case 3:
                    System.out.print("Enter Soldier ID to mark as missing: ");
                    String searchId = scanner.nextLine();
                    boolean found = false;
                    for (Soldier s : soldiers) {
                        if (s.getId().equals(searchId)) {
                            s.setStatus("missing");
                            System.out.println("Soldier marked as missing.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Soldier with ID " + searchId + " not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Soldier ID to remove from missing: ");
                    String removeId = scanner.nextLine();
                    boolean removed = false;
                    for (Soldier s : soldiers) {
                        if (s.getId().equals(removeId)) {
                            if (s.getStatus().equals("missing")) {
                                s.setStatus("active");
                                System.out.println("Soldier removed from missing status.");
                            } else {
                                System.out.println("Soldier is not marked as missing.");
                            }
                            removed = true;
                            break;
                        }
                    }
                    if (!removed) {
                        System.out.println("Soldier with ID " + removeId + " not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter Soldier ID to view details: ");
                    String detailId = scanner.nextLine();
                    boolean detailFound = false;
                    for (Soldier s : soldiers) {
                        if (s.getId().equals(detailId)) {
                            System.out.println("\n--- Soldier Details ---");
                            System.out.println("Name: " + s.getName());
                            System.out.println("ID: " + s.getId());
                            System.out.println("Rank: " + s.getRank());
                            System.out.println("Unit: " + s.getUnit());
                            System.out.println("Latitude: " + s.getLatitude());
                            System.out.println("Longitude: " + s.getLongitude());
                            System.out.println("Status: " + s.getStatus());
                            detailFound = true;
                            break;
                        }
                    }
                    if (!detailFound) {
                        System.out.println("Soldier with ID " + detailId + " not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}