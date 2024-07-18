/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.HotelModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class HotelManagement implements IHotel {
    private List<HotelModel> listHotel;
    private Scanner scanner;
    public HotelManagement(){
        listHotel = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void addHotel(HotelModel h) {
        System.out.println("Add New Hotel");
        List<String> existingHotelIds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] hotelData = line.split(", ");
                existingHotelIds.add(hotelData[0]);
            }
        } catch (IOException e) {
            System.out.println("Error reading data from Hotel.dat file: " + e.getMessage());
        }
        int nextHotelId = 1;
        String hotelId = "H" + String.format("%02d", nextHotelId);
        
        while (existingHotelIds.contains(hotelId)) {
            nextHotelId++;
            hotelId = "H" + String.format("%02d", nextHotelId);
        }   
        h.setHotelId(hotelId);
        
        System.out.print("Enter Hotel Name: ");
        String hotelName = scanner.nextLine();
        System.out.print("Enter Hotel Room Available: ");
        int hotelRoomAvailable = scanner.nextInt();
        scanner.nextLine(); // Đọc dòng mới
        System.out.print("Enter Hotel Address: ");
        String hotelAddress = scanner.nextLine();
        System.out.print("Enter Hotel Phone: ");
        String hotelPhone = scanner.nextLine();
        System.out.print("Enter Hotel Rating: ");
        String hotelRating = scanner.nextLine();
        h = new HotelModel(hotelId, hotelName, hotelRoomAvailable, hotelAddress, hotelPhone, hotelRating);
        listHotel.add(h);
        System.out.println("New hotel added successfully.");

        while (true) {
            System.out.println("Do you want to create a new hotel? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                addHotel(h);
                break;
            } else if (choice.equalsIgnoreCase("N")) {
                // Go back to the main menu
                break;
            } else {
                System.out.println("Invalid choice. Please enter Y for Yes or N for No.");
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Hotel.dat", true))) {
            writer.write(h.toString());
            writer.newLine();
            System.out.println("Data added to Hotel.dat file.");
        } catch (IOException e) {
            System.out.println("Error writing data to Hotel.dat file: " + e.getMessage());
        }
    }

    @Override
    public void checkHotel() {
        System.out.println("Enter Hotel ID: ");
        String hotelId = scanner.nextLine();

        if (isHotelExists(hotelId)) {
            System.out.println("Exist Hotel");
        } else {
            System.out.println("No Hotel Found!");
        }

        while (true) {
            System.out.println("Do you want to go back to the main menu? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                break;
            } else if (choice.equalsIgnoreCase("N")) {
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please enter Y for Yes or N for No.");
            }
        }
    }   

    @Override
    public void updatingHotel() {
        System.out.print("Enter the Hotel ID to update: ");
        String hotelId = scanner.nextLine();

        boolean found = false;
        List<HotelModel> updatedHotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] hotelData = line.split(", ");
                if (hotelData[0].equals(hotelId)) {
                    System.out.print("Enter updated Hotel Name (leave blank to keep the old value): ");
                    String hotelName = scanner.nextLine().trim();
                    if (hotelName.isEmpty()) {
                        hotelName = hotelData[1]; // Keep the old value
                    }

                    System.out.print("Enter updated Hotel Room Available (leave blank to keep the old value): ");
                    String hotelRoomAvailableStr = scanner.nextLine().trim();
                    int hotelRoomAvailable;
                    if (hotelRoomAvailableStr.isEmpty()) {
                        hotelRoomAvailable = Integer.parseInt(hotelData[2]); // Keep the old value
                    } else {
                        hotelRoomAvailable = Integer.parseInt(hotelRoomAvailableStr);
                    }

                    System.out.print("Enter updated Hotel Address (leave blank to keep the old value): ");
                    String hotelAddress = scanner.nextLine().trim();
                    if (hotelAddress.isEmpty()) {
                        hotelAddress = hotelData[3]; // Keep the old value
                    }

                    System.out.print("Enter updated Hotel Phone (leave blank to keep the old value): ");
                    String hotelPhoneStr = scanner.nextLine().trim();
                    String hotelPhone;
                    if (hotelPhoneStr.isEmpty()) {
                        hotelPhone = hotelData[4]; // Keep the old value
                    } else {
                        hotelPhone = hotelPhoneStr;
                    }

                    System.out.print("Enter updated Hotel Rating (leave blank to keep the old value): ");
                    String hotelRating = scanner.nextLine().trim();
                    if (hotelRating.isEmpty()) {
                        hotelRating = hotelData[5]; // Keep the old value
                    }

                    HotelModel updatedHotel = new HotelModel(hotelData[0], hotelName, hotelRoomAvailable, hotelAddress, hotelPhone, hotelRating);
                    updatedHotels.add(updatedHotel);
                    found = true;
                } else {
                    // Keep the existing hotel information
                    HotelModel hotel = new HotelModel(hotelData[0], hotelData[1], Integer.parseInt(hotelData[2]), hotelData[3], hotelData[4], hotelData[5]);
                    updatedHotels.add(hotel);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading data from Hotel.dat file: " + e.getMessage());
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Hotel.dat"))) {
                for (HotelModel hotel : updatedHotels) {
                    writer.write(hotel.toString());
                    writer.newLine();
                }
                System.out.println("Hotel information updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing data to Hotel.dat file: " + e.getMessage());
            }
        } else {
            System.out.println("No hotel found with the given ID.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();

        displayHotelList();
    }

    @Override
    public void deletingHotel(HotelModel h) {
        System.out.println("Enter the Hotel ID to delete: ");
        String hotelId = scanner.nextLine();

        boolean found = false;
        for (HotelModel hotel : listHotel) {
            if (hotel.getHotelId().equals(hotelId)) {
                listHotel.remove(hotel);
                found = true;
                System.out.println("Hotel deleted successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("No hotel found with the given ID.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Hotel.dat"))) {
            for (HotelModel hotel : listHotel) {
                writer.write(hotel.toString());
                writer.newLine();
            }
            System.out.println("Data updated in Hotel.dat file.");
        } catch (IOException e) {
            System.out.println("Error writing data to Hotel.dat file: " + e.getMessage());
        }

        while (true) {
            System.out.println("Do you want to delete another hotel? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                deletingHotel(h);
                break;
            } else if (choice.equalsIgnoreCase("N")) {
                // Go back to the main menu
                break;
            } else {
                System.out.println("Invalid choice. Please enter Y for Yes or N for No.");
            }
        }
    }

    private void updateHotelDataFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Hotel.dat"))) {
            for (HotelModel hotel : listHotel) {
                writer.write(hotel.toString());
                writer.newLine();
            }
            System.out.println("Data updated in Hotel.dat file.");
        } catch (IOException e) {
            System.out.println("Error updating data in Hotel.dat file: " + e.getMessage());
        }
    }

    private int findHotelIndex(String hotelId) {
        for (int i = 0; i < listHotel.size(); i++) {
            if (listHotel.get(i).getHotelId().equals(hotelId)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isHotelExists(String hotelId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 1 && parts[0].equals(hotelId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<HotelModel> getListHotel() {
        return listHotel;
    }

    private List<HotelModel> readHotelDataFromFile() {
        List<HotelModel> hotelList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] hotelData = line.split(", ");
                HotelModel hotel = new HotelModel(hotelData[0], hotelData[1], Integer.parseInt(hotelData[2]), hotelData[3], hotelData[4], hotelData[5]);
                hotelList.add(hotel);
            }
        } catch (IOException e) {
            System.out.println("Error reading data from Hotel.dat file: " + e.getMessage());
        }

        return hotelList;
    }
    
    public void displayHotelList() {
        System.out.println("Displaying Hotel List (Descending by Hotel_Name):");
    
        List<HotelModel> sortedHotels = new ArrayList<>(listHotel);
    
        // Sort the hotels by Hotel_Name in descending order
        sortedHotels.sort(Comparator.comparing(HotelModel::getHotelName).reversed());
    
        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] hotelData = line.split(", ");
                String hotelId = hotelData[0];
                String hotelName = hotelData[1];
                int hotelRoomAvailable = Integer.parseInt(hotelData[2]);
                String hotelAddress = hotelData[3];
                String hotelPhone = hotelData[4];
                String hotelRating = hotelData[5];
            
                HotelModel hotel = new HotelModel(hotelId, hotelName, hotelRoomAvailable, hotelAddress, hotelPhone, hotelRating);
                sortedHotels.add(hotel);
            }
        } catch (IOException e) {
            System.out.println("Error reading data from Hotel.dat file: " + e.getMessage());
            return;
        }
    
        for (HotelModel hotel : sortedHotels) {
            System.out.println("Hotel ID: " + hotel.getHotelId());
            System.out.println("Hotel Name: " + hotel.getHotelName());
            System.out.println("Hotel Room Available: " + hotel.getHotelRoomAvailable());
            System.out.println("Hotel Address: " + hotel.getHotelAddress());
            System.out.println("Hotel Phone: " + hotel.getHotelPhone());
            System.out.println("Hotel Rating: " + hotel.getHotelRating());
            System.out.println("-----------------------------------------");
        }
    }
}
