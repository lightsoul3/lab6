package database;

import lab1.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceVanished extends DBManager{

    public ServiceVanished(String url, String username, String password){
        super(url, username, password);

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

    }

    public boolean addVanished(Vanished vanished){
        try{
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO vanished " +
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
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM vanished WHERE vanished_type LIKE ? ");
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
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM vanished WHERE sex LIKE ? ");
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
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM vanished WHERE vanished_name LIKE ? ");
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
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM vanished WHERE birth_date LIKE ? ");
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
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM vanished WHERE miss_date LIKE ? ");
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
            PreparedStatement statement = getConnection().prepareStatement(" SELECT * FROM vanished WHERE phone_number LIKE ? ");
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

    public boolean updateVanished(Vanished vanished){
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE  vanished " +
                    "SET vanished_type = ?, sex = ?, vanished_name = ?, birth_date = ?, miss_date = ?, phone_number = ?," +
                    " region = ? WHERE id = ?;");
            statement.setString(1, vanished.getType().toString());
            statement.setString(2, vanished.getSex().toString());
            statement.setString(3, vanished.getName());
            statement.setDate(4, java.sql.Date.valueOf(vanished.getBirthDate()));
            statement.setDate(5, java.sql.Date.valueOf(vanished.getMissDate()));
            statement.setString(6, vanished.getFinderPhoneNumber());
            statement.setInt(7, vanished.getRegion().getRegionId());
            statement.setInt(8, vanished.getVanishedId());
            statement.executeUpdate();
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public void deleteVanished(int id){
        executeUpdate("DELETE FROM vanished WHERE id = " + id + "");
    }
}
