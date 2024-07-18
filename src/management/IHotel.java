/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package management;
import entities.HotelModel;
/**
 *
 * @author PC
 */
public interface IHotel {
    void addHotel(HotelModel h);
    void checkHotel();
    void updatingHotel();
    void deletingHotel(HotelModel h);
}
