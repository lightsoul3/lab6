package database;


import lab1.LastSeeingPlace;
import lab1.Region;
import lab1.Vanished;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String args[]) {

        Region region1 = new Region.Builder().setRegionName("Chernivtsi").setSquare(8097)
                .setPeopleNumber(9016).setAnimalNumber(1000).build();
        Region region2 = new Region.Builder().setRegionId(2).setRegionName("Lviv").setSquare(10954)
                .setPeopleNumber(15021).setAnimalNumber(28012).build();

        Vanished vanished1 = new Vanished.Builder()
                .setType(Vanished.Type.ANIMAL).setSex(Vanished.Sex.MALE).setName("Barsik")
                .setBirthDate(LocalDate.of(2017, 8, 18))
                .setMissDate(LocalDate.of(2022, 10, 29))
                .setfinderPhoneNumber("0982602637").setRegion(region1).build();


        Vanished vanished2 = new Vanished.Builder()
                .setType(Vanished.Type.HUMAN).setSex(Vanished.Sex.MALE).setName("Roman")
                .setBirthDate(LocalDate.of(1974, 11, 8))
                .setMissDate(LocalDate.of(2022, 8, 24))
                .setfinderPhoneNumber("0682602111").setRegion(region1).build();

        Vanished vanished3 = new Vanished.Builder()
                .setType(Vanished.Type.HUMAN).setSex(Vanished.Sex.FEMALE).setName("Alina")
                .setBirthDate(LocalDate.of(1999, 9, 28))
                .setMissDate(LocalDate.of(2022, 2, 24))
                .setfinderPhoneNumber("0990993917").setRegion(region2).build();

        Vanished vanished4 = new Vanished.Builder().setVanishedId(4)
                .setType(Vanished.Type.HUMAN).setSex(Vanished.Sex.MALE).setName("Bogdan")
                .setBirthDate(LocalDate.of(1991, 4, 11))
                .setMissDate(LocalDate.of(2021, 9, 19))
                .setfinderPhoneNumber("0680026393").setRegion(region2).build();


        Vanished vanished5 = new Vanished.Builder()
                .setType(Vanished.Type.ANIMAL).setSex(Vanished.Sex.FEMALE).setName("Lily")
                .setBirthDate(LocalDate.of(2019, 6, 26))
                .setMissDate(LocalDate.of(2022, 6, 6))
                .setfinderPhoneNumber("0992890628").setRegion(region2).build();


        LastSeeingPlace p1 = new LastSeeingPlace.Builder()
                .setRegionName("Chernivtsi").setStreetName("Ruska").setHouseNumber("22-A").setVanished(vanished1).build();
        LastSeeingPlace p2 = new LastSeeingPlace.Builder()
                .setRegionName("Chernivtsi").setStreetName("Geroiv Maidanu").setHouseNumber("105").setVanished(vanished2).build();
        LastSeeingPlace p3 = new LastSeeingPlace.Builder()
                .setRegionName("Lviv").setStreetName("Golovna").setHouseNumber("11-B").setVanished(vanished3).build();
        LastSeeingPlace p4 = new LastSeeingPlace.Builder()
                .setRegionName("Lviv").setStreetName("Shevchenka").setHouseNumber("98-C").setVanished(vanished4).build();
        LastSeeingPlace p5 = new LastSeeingPlace.Builder()
                .setRegionName("Lviv").setStreetName("Lesi Ukrainky").setHouseNumber("98-A").setVanished(vanished5).build();




        ServiceRegion serviceRegion = new ServiceRegion("jdbc:mysql://localhost:3306/information_about_vanished", "root", "xyf377j0");

//        serviceRegion.addRegion(region1);
//        serviceRegion.addRegion(region2);



        List<Region> regions = serviceRegion.getRegions();

        System.out.println("SELECT ALL REGIONS");
        System.out.println(regions);

        System.out.println("\n");

        System.out.println("SELECT REGIONS BY NAME - Lviv");
        System.out.println(serviceRegion.getRegionByName("Lviv"));

        System.out.println("\n");

        System.out.println("SORT REGIONS BY NAME");
        System.out.println(serviceRegion.sortRegionByName());





        ServiceVanished serviceVanished = new ServiceVanished("jdbc:mysql://localhost:3306/information_about_vanished", "root", "xyf377j0");

//        serviceVanished.addVanished(vanished1);
//        serviceVanished.addVanished(vanished2);
//        serviceVanished.addVanished(vanished3);
//        serviceVanished.addVanished(vanished4);
//        serviceVanished.addVanished(vanished5);
//

        System.out.println("\n\n\n");

        List<Vanished> vanisheds = serviceVanished.getVanisheds();

        System.out.println("SELECT ALL VANISHEDS");
        System.out.println(vanisheds);

        System.out.println("\n");

        System.out.println("SELECT VANISHED BY TYPE - Animal");
        System.out.println(serviceVanished.getVanishedByType("Animal"));

        System.out.println("\n");

        System.out.println("SELECT VANISHED BY SEX - Female");
        System.out.println(serviceVanished.getVanishedBySex("Female"));

        System.out.println("\n");

        System.out.println("SORT VANISHED BY NAME");
        System.out.println(serviceVanished.sortVanishedByName());

        System.out.println("\n");

        System.out.println("SORT VANISHED BY MISS DATE");
        System.out.println(serviceVanished.sortVanishedByMissDate());

        System.out.println("\n");




        ServiceLastSeeingPlace serviceLastSeeingPlace = new ServiceLastSeeingPlace("jdbc:mysql://localhost:3306/information_about_vanished", "root", "xyf377j0");

//        serviceLastSeeingPlace.addPlace(p1);
//        serviceLastSeeingPlace.addPlace(p2);
//        serviceLastSeeingPlace.addPlace(p3);
//        serviceLastSeeingPlace.addPlace(p4);
//        serviceLastSeeingPlace.addPlace(p5);


        System.out.println("\n\n\n");

        List<LastSeeingPlace> lastSeeingPlaces = serviceLastSeeingPlace.getLastSeeingPlaces();

        System.out.println("SELECT ALL LAST SEEING PLACES");
        System.out.println(lastSeeingPlaces);

        System.out.println("\n");

        System.out.println("SELECT LAST SEEING PLACES BY REGION - Lviv");
        System.out.println(serviceLastSeeingPlace.getPlaceByRegionName("Lviv"));

        System.out.println("\n");

        System.out.println("SORT LAST SEEING PLACES BY STREET NAME");
        System.out.println(serviceLastSeeingPlace.sortPlacesByStreetName());

        LastSeeingPlace p6 = new LastSeeingPlace.Builder()
                .setRegionName("Lviv").setStreetName("Ivana Franka").setHouseNumber("98-A").setVanished(vanished5).build();

        System.out.println("UPDATE LAST SEEING PLACE WHERE id = 5");
        serviceLastSeeingPlace.updateLastSeeingPlace(p6);
        System.out.println(serviceLastSeeingPlace.getPlaceById(5));
    }
}
