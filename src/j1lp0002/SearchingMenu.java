/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j1lp0002;

import entities.HotelModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author PC
 */
public class SearchingMenu {
    private List<HotelModel> hotelList;
    private Scanner scanner;
    
    public SearchingMenu(List<HotelModel> hotelList){
        this.hotelList = hotelList;
        scanner = new Scanner(System.in);
    }
    
    public void displaySearchMenu(){
        System.out.println("|----------------------------- Searching Menu ----------------------------|");
        System.out.println("|1. Search by Hotel ID|2. Search by Hotel Name|0. Go back to the main menu|");
        System.out.println("|----------------------------- ************** ----------------------------|");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch(choice){
            case 1:
                searchByHotelId();
                break;
            case 2:
                searchByHotelName();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                displaySearchMenu();
                break;
        }
    }

    private void searchByHotelId() {
        System.out.print("Enter the Hotel ID to search: ");
        String hotelId = scanner.nextLine();

        boolean found = false;
        List<HotelModel> foundHotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] hotelData = line.split(", ");
                if (hotelData[0].equals(hotelId)) {
                    HotelModel hotel = new HotelModel(hotelData[0], hotelData[1], Integer.parseInt(hotelData[2]), hotelData[3], hotelData[4], hotelData[5]);
                    foundHotels.add(hotel);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading data from Hotel.dat file: " + e.getMessage());
        }

        if (found) {
            System.out.println("Hotels found:");
            // Sort the found hotels by Hotel ID in descending order
            foundHotels.sort(Comparator.comparing(HotelModel::getHotelId).reversed());
            for (HotelModel hotel : foundHotels) {
                System.out.println("Hotel ID: " + hotel.getHotelId());
                System.out.println("Hotel Name: " + hotel.getHotelName());
                System.out.println("Hotel Room Available: " + hotel.getHotelRoomAvailable());
                System.out.println("Hotel Address: " + hotel.getHotelAddress());
                System.out.println("Hotel Phone: " + hotel.getHotelPhone());
                System.out.println("Hotel Rating: " + hotel.getHotelRating());
                System.out.println("-----------------------------------------");
            }
        } else {
            System.out.println("No hotels found with the given ID.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();

        displaySearchMenu();
    }

    private void searchByHotelName() {
        System.out.print("Enter the Hotel Name to search: ");
        String hotelName = scanner.nextLine();

        boolean found = false;
        List<HotelModel> foundHotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Hotel.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] hotelData = line.split(", ");
                if (hotelData[1].equalsIgnoreCase(hotelName)) {
                    HotelModel hotel = new HotelModel(hotelData[0], hotelData[1], Integer.parseInt(hotelData[2]), hotelData[3], hotelData[4], hotelData[5]);
                    foundHotels.add(hotel);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading data from Hotel.dat file: " + e.getMessage());
        }

        if (found) {
            System.out.println("Hotels found:");
            for (HotelModel hotel : foundHotels) {
                System.out.println("Hotel ID: " + hotel.getHotelId());
                System.out.println("Hotel Name: " + hotel.getHotelName());
                System.out.println("Hotel Room Available: " + hotel.getHotelRoomAvailable());
                System.out.println("Hotel Address: " + hotel.getHotelAddress());
                System.out.println("Hotel Phone: " + hotel.getHotelPhone());
                System.out.println("Hotel Rating: " + hotel.getHotelRating());
                System.out.println("-----------------------------------------");
            }
        } else {
            System.out.println("No hotel found with the given name.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();

        displaySearchMenu();
    }
    
}
