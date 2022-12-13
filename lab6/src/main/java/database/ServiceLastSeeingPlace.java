package database;

import lab1.LastSeeingPlace;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceLastSeeingPlace extends DBManager {
    public ServiceLastSeeingPlace(String url, String username, String password){
        super(url, username, password);

//        executeUpdate("CREATE TABLE last_seeing_place " +
//                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
//                "region_name VARCHAR(100) NOT NULL, " +
//                "street_name VARCHAR(100) NOT NULL, " +
//                "house_number VARCHAR(10) NOT NULL, " +
//                "vanished INT NOT NULL, " +
//                "FOREIGN KEY (vanished) REFERENCES vanished (id), " +
//                "CONSTRAINT UNIQUE (region_name, street_name, house_number, vanished))"
//        );

    }

    public boolean addPlace(LastSeeingPlace lastSeeingPlace){
        try{
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO last_seeing_place " +
                    "(region_name, street_name, house_number, vanished) VALUES (?, ?, ?, ?)");
            statement.setString(1, lastSeeingPlace.getRegionName());
            statement.setString(2, lastSeeingPlace.getStreetName());
            statement.setString(3, lastSeeingPlace.getHouseNumber());
            statement.setInt(4, lastSeeingPlace.getVanished().getVanishedId());
            statement.executeUpdate();
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    private LastSeeingPlace getPlace(ResultSet set){
        try{
            return new LastSeeingPlace.Builder().setPlaceId(set.getInt("id"))
                    .setRegionName(set.getString("region_name"))
                    .setStreetName(set.getString("street_name"))
                    .setHouseNumber(set.getString("house_number"))
                    .setVanishedId(set.getInt("vanished")).build();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public List<LastSeeingPlace> getPlaceByQuery(String query){
        List<LastSeeingPlace> result = new ArrayList<>();
        ResultSet set = getData(query);
        try {
            while (set.next()){
                result.add(getPlace(set));
            }
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return result;
    }

    public List<LastSeeingPlace> getLastSeeingPlaces()
    {
        return getPlaceByQuery("SELECT * FROM last_seeing_place");
    }

    public List<LastSeeingPlace> getPlaceById(int id){
        return getPlaceByQuery("SELECT * FROM last_seeing_place WHERE id = " + id + "");
    }

    public List<LastSeeingPlace> sortPlacesById(){
        return getPlaceByQuery("SELECT * FROM last_seeing_place ORDER BY id");
    }

    public List<LastSeeingPlace> getPlaceByRegionName(String name){
        List<LastSeeingPlace> result = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM last_seeing_place WHERE region_name LIKE ? ");
            statement.setString( 1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getPlace(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<LastSeeingPlace> sortPlacesByRegionName(){
        return getPlaceByQuery("SELECT * FROM last_seeing_place ORDER BY region_name");
    }

    public List<LastSeeingPlace> getPlaceByStreetName(String name){
        List<LastSeeingPlace> result = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM last_seeing_place WHERE street_name LIKE ? ");
            statement.setString( 1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getPlace(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<LastSeeingPlace> sortPlacesByStreetName(){
        return getPlaceByQuery("SELECT * FROM last_seeing_place ORDER BY street_name");
    }

    public List<LastSeeingPlace> getPlaceByHouseNumber(String number){
        List<LastSeeingPlace> result = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM last_seeing_place WHERE house_number LIKE ? ");
            statement.setString( 1, number);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getPlace(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<LastSeeingPlace> sortPlacesByHouseNumber(){
        return getPlaceByQuery("SELECT * FROM last_seeing_place ORDER BY house_number");
    }

    public List<LastSeeingPlace> getPlaceByVanishedId(int id){
        return getPlaceByQuery("SELECT * FROM last_seeing_place WHERE vanished = " + id + "");
    }

    public List<LastSeeingPlace> sortPlacesByVanished(){
        return getPlaceByQuery("SELECT * FROM last_seeing_place ORDER BY vanished");
    }

    public boolean updateLastSeeingPlace(LastSeeingPlace lastSeeingPlace){
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE  last_seeing_place" +
                    " SET region_name = ?, street_name = ?, house_number = ?, vanished = ? WHERE id = ?;");
            statement.setString(1, lastSeeingPlace.getRegionName());
            statement.setString(2, lastSeeingPlace.getStreetName());
            statement.setString(3, lastSeeingPlace.getHouseNumber());
            statement.setInt(4, lastSeeingPlace.getVanished().getVanishedId());
            statement.setInt(5, lastSeeingPlace.getPlaceId());
            statement.executeUpdate();
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public void deletePlace(int id){
        executeUpdate("DELETE FROM last_seeing_place WHERE id = " + id +"");
    }

}
