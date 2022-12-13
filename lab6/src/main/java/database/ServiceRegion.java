package database;

import lab1.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceRegion extends DBManager{

    public ServiceRegion(String url, String username, String password){
        super(url, username, password);

//        executeUpdate("CREATE TABLE region " +
//                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
//                "region_name VARCHAR(100) NOT NULL, " +
//                "square FLOAT NOT NULL, " +
//                "people_number INT NOT NULL, " +
//                "animal_number INT NOT NULL, " +
//                "CONSTRAINT UNIQUE (region_name, square, people_number, animal_number ))"
//        );

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

    public boolean addRegion(Region region){
        try{
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO region " +
                    "(region_name, square, people_number, animal_number) VALUES( ?, ?, ?, ? );");
            statement.setString(1, region.getName());
            statement.setFloat(2, region.getSquare());
            statement.setInt(3, region.getPeopleNumber());
            statement.setInt(4, region.getAnimalNumber());
            statement.executeUpdate();
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }


    private Region getRegion(ResultSet set){
        try{
            return new Region.Builder().setRegionId(set.getInt("id"))
                    .setRegionName(set.getString("region_name"))
                    .setSquare(set.getFloat("square"))
                    .setPeopleNumber(set.getInt("people_number"))
                    .setAnimalNumber(set.getInt("animal_number")).build();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public List<Region> getRegionsByQuery(String query){
        List<Region> result = new ArrayList<>();
        ResultSet set = getData(query);
        try {
            while (set.next()){
                result.add(getRegion(set));
            }
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return result;
    }

    public List<Region> getRegions()
    {
        return getRegionsByQuery("SELECT * FROM region");
    }

    public List<Region> getRegionById(int id){
        return getRegionsByQuery("SELECT * FROM region WHERE id = " + id + "");
    }

    public List<Region> getRegionByName(String name){
        List<Region> result = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM region WHERE region_name LIKE ? ");
            statement.setString( 1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getRegion(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Region> sortRegionByName(){
        return getRegionsByQuery("SELECT * FROM region ORDER BY region_name");
    }

    public List<Region> getRegionBySquare(float square){
        return getRegionsByQuery("SELECT * FROM region WHERE square = '" + square + "'");
    }


    public List<Region> sortRegionBySquare(){
        return getRegionsByQuery("SELECT * FROM region ORDER BY square");
    }

    public List<Region> getRegionByPeopleNumber(int peopleNumber){
        return getRegionsByQuery("SELECT * FROM region WHERE people_number = '" + peopleNumber + "'");
    }

    public List<Region> sortRegionByPeopleNumber(){
        return getRegionsByQuery("SELECT * FROM region ORDER BY people_number");
    }

    public List<Region> getRegionByAnimalNumber(int animalNumber){
        return getRegionsByQuery("SELECT * FROM region WHERE animal_number = '" + animalNumber + "'");
    }

    public List<Region> sortRegionByAnimalNumber(){
        return getRegionsByQuery("SELECT * FROM region ORDER BY animal_number");
    }

    public boolean updateRegion(Region region){
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE region SET region_name = ?," +
                    "square = ?, people_number = ?, animal_number = ? WHERE id = ?;");
            statement.setString(1, region.getName());
            statement.setFloat(2, region.getSquare());
            statement.setInt(3, region.getPeopleNumber());
            statement.setInt(4, region.getAnimalNumber());
            statement.setInt(5, region.getRegionId());
            statement.executeUpdate();
            return true;
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public void deleteRegion(int id){ getRegionsByQuery("DELETE FROM region WHERE id = " + id +"");
    }

}
