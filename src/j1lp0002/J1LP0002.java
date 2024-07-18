/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j1lp0002;

import entities.HotelModel;
import management.HotelManagement;
import java.util.Scanner;

public class J1LP0002 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelManagement hotelManagement = new HotelManagement();

        while (true) {
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|************************************************* Hotel Management Menu **********************************************************|");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|1. Add New Hotel|2. Check Existing Hotel|3. Update Hotel Information|4. Delete Hotel|5. Search Hotel|6. Display Hotel List|7. Quit|");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------|");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    HotelModel newHotel = new HotelModel(null, null, 0, null, null, null);
                    hotelManagement.addHotel(newHotel);
                    break;
                case 2:
                    hotelManagement.checkHotel();
                    break;
                case 3:
                    hotelManagement.updatingHotel();
                    break;
                case 4:
                    hotelManagement.deletingHotel(null); // Pass an instance of HotelModel if required
                    break;
                case 5: 
                    // Create an instance of SearchingMenu and pass the hotel list
                    SearchingMenu searchingMenu = new SearchingMenu(hotelManagement.getListHotel());
                    searchingMenu.displaySearchMenu();
                    break;
                case 6:
                    hotelManagement.displayHotelList();
                    break;
                case 7:
                    System.out.println("Thank you for using the Hotel Management Program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}