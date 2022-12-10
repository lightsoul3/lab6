package database;

import lab1.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Service extends DBManager{

    public Service (String url, String username, String password){
        super(url, username, password);

//        executeUpdate("CREATE TABLE region " +
//                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
//                "region_name VARCHAR(100) NOT NULL, " +
//                "square FLOAT NOT NULL, " +
//                "people_number INT NOT NULL, " +
//                "animal_number INT NOT NULL, " +
//                "CONSTRAINT UNIQUE (region_name, square, people_number, animal_number ))"
//        );
//
//        executeUpdate("CREATE TABLE vanished" +
//                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
//                "vanished_type VARCHAR(10) NOT NULL, " +
//                "sex VARCHAR(10) NOT NULL, " +
//                "vanished_name VARCHAR(100) NOT NULL, " +
//                "birth_date DATE NOT NULL, " +
//                "miss_date DATE NOT NULL, " +
//                "phone_number VARCHAR(15) NOT NULL, " +
//                "region INT NOT NULL, " +
//                "FOREIGN KEY (region) REFERENCES region (id), " +
//                "CONSTRAINT UNIQUE (vanished_type, sex, vanished_name, birth_date, miss_date, region))"
//        );
//
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO region " +
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

    public boolean addVanished(Vanished vanished){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO vanished " +
                    "(vanished_type, sex, vanished_name, birth_date, miss_date, phone_number, region)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, vanished.getType().toString());
            statement.setString(2, vanished.getSex().toString());
            statement.setString(3, vanished.getName());
            statement.setDate(4, java.sql.Date.valueOf(vanished.getBirthDate()));
            statement.setDate(5, java.sql.Date.valueOf(vanished.getMissDate()));
            statement.setString(6, vanished.getFinderPhoneNumber());
            statement.setInt(7, vanished.getRegion().getRegionId());
            statement.executeUpdate();
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public boolean addPlace(LastSeeingPlace lastSeeingPlace){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO last_seeing_place " +
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

    private Vanished getVanished(ResultSet set){
        try {
            return new Vanished.Builder().setVanishedId(set.getInt("id"))
                    .setType(Vanished.Type.valueOf(set.getString("vanished_type")))
                    .setSex(Vanished.Sex.valueOf(set.getString("sex")))
                    .setName(set.getString("vanished_name"))
                    .setBirthDate(set.getDate("birth_date").toLocalDate())
                    .setMissDate(set.getDate("miss_date").toLocalDate())
                    .setfinderPhoneNumber(set.getString("phone_number"))
                    .setRegionId(set.getInt("region")).build();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
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
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM region WHERE region_name LIKE ? ");
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

    public void updateRegion(Region region){
        executeUpdate("UPDATE region SET region_name = '" + region.getName() + "', " +
                "square = " + region.getSquare() + ", " +
                "people_number = " + region.getPeopleNumber() + ", " +
                "animal_number = " + region.getAnimalNumber() + " " +
                "WHERE id = " + region.getRegionId() + "");
    }

    public void deleteRegion(int id){
        executeUpdate("DELETE FROM region WHERE id = " + id +"");
    }



    public List<Vanished> getVanishedByQuery(String query){
        List<Vanished> result = new ArrayList<>();
        ResultSet set = getData(query);
        try {
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return result;
    }

    public List<Vanished> getVanisheds()
    {
        return getVanishedByQuery("SELECT * FROM vanished");
    }

    public List<Vanished> getVanishedById(int id){
        return getVanishedByQuery("SELECT * FROM vanished WHERE id = " + id + "");
    }

    public List<Vanished> getVanishedByType(String type){
        List<Vanished> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM vanished WHERE vanished_type LIKE ? ");
            statement.setString( 1, type);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Vanished> sortVanishedByType(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY vanished_type");
    }

    public List<Vanished> getVanishedBySex(String sex){
        List<Vanished> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM vanished WHERE sex LIKE ? ");
            statement.setString( 1, sex);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Vanished> sortVanishedBySex(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY sex");
    }

    public List<Vanished> getVanishedByName(String name){
        List<Vanished> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM vanished WHERE vanished_name LIKE ? ");
            statement.setString( 1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Vanished> sortVanishedByName(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY vanished_name");
    }

    public List<Vanished> getVanishedByBirthDate(String date){
        List<Vanished> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM vanished WHERE birth_date LIKE ? ");
            statement.setString( 1, date);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Vanished> sorVanishedByBirthDate(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY birth_date");
    }

    public List<Vanished> getVanishedByMissDate(String date){
        List<Vanished> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM vanished WHERE miss_date LIKE ? ");
            statement.setString( 1, date);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Vanished> sortVanishedByMissDate(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY miss_date");
    }

    public List<Vanished> getVanishedByFinderPhoneNumber(String number){
        List<Vanished> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM vanished WHERE phone_number LIKE ? ");
            statement.setString( 1, number);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(getVanished(set));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public List<Vanished> sorVanishedByFinderPhoneNumber(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY phone_number");
    }

    public List<Vanished> getVanishedByRegionId(int id){
        return getVanishedByQuery("SELECT * FROM vanished WHERE region = " + id + "");
    }

    public List<Vanished> sorVanishedByRegion(){
        return getVanishedByQuery("SELECT * FROM vanished ORDER BY region");
    }

    public void updateVanished(Vanished vanished){
        executeUpdate("UPDATE vanished SET vanished_type = '" + vanished.getType().toString() + "', " +
                "sex = '" + vanished.getSex().toString() + "', " +
                "vanished_name = '" + vanished.getName() + "', " +
                "birth_date = '" + java.sql.Date.valueOf(vanished.getBirthDate()) + "', " +
                "miss_date = '" + java.sql.Date.valueOf(vanished.getMissDate()) + "', " +
                "phone_number = '" + vanished.getFinderPhoneNumber() + "', " +
                "region = " + vanished.getRegionId() + " " +
                "WHERE id = " + vanished.getVanishedId() + "");
    }

    public void deleteVanished(int id){
        executeUpdate("DELETE FROM vanished WHERE id = " + id + "");
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
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM last_seeing_place WHERE region_name LIKE ? ");
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
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM last_seeing_place WHERE street_name LIKE ? ");
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
            PreparedStatement statement = connection.prepareStatement(" SELECT * FROM last_seeing_place WHERE house_number LIKE ? ");
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

    public void updateLastSeeingPlace(LastSeeingPlace lastSeeingPlace){
        executeUpdate("UPDATE last_seeing_place SET region_name = '" + lastSeeingPlace.getRegionName() + "', " +
                "street_name = '" + lastSeeingPlace.getStreetName() + "', " +
                "house_number = '" + lastSeeingPlace.getHouseNumber() + "', " +
                "vanished = " + lastSeeingPlace.getVanishedId() + " " +
                "WHERE id = " + lastSeeingPlace.getPlaceId() + "");
    }

    public void deletePlace(int id){
        executeUpdate("DELETE FROM last_seeing_place WHERE id = " + id +"");
    }

}
