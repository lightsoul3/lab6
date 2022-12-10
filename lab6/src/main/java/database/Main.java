package database;


import lab1.LastSeeingPlace;
import lab1.Region;
import lab1.Vanished;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {

        Region region1 = new Region.Builder().setRegionId(1).setRegionName("Chernivtsi").setSquare(8097)
                .setPeopleNumber(9016).setAnimalNumber(1000).build();
        Region region2 = new Region.Builder().setRegionId(2).setRegionName("Lviv").setSquare(10954)
                .setPeopleNumber(15021).setAnimalNumber(28012).build();

        Vanished vanished1 = new Vanished.Builder().setVanishedId(1)
                .setType(Vanished.Type.ANIMAL).setSex(Vanished.Sex.MALE).setName("Barsik")
                .setBirthDate(LocalDate.of(2017, 8, 18))
                .setMissDate(LocalDate.of(2022, 10, 29))
                .setfinderPhoneNumber("0982602637").setRegion(region1).build();


        Vanished vanished2 = new Vanished.Builder().setVanishedId(2)
                .setType(Vanished.Type.HUMAN).setSex(Vanished.Sex.MALE).setName("Roman")
                .setBirthDate(LocalDate.of(1974, 11, 8))
                .setMissDate(LocalDate.of(2022, 8, 24))
                .setfinderPhoneNumber("0682602111").setRegion(region1).build();

        Vanished vanished3 = new Vanished.Builder().setVanishedId(3)
                .setType(Vanished.Type.HUMAN).setSex(Vanished.Sex.FEMALE).setName("Alina")
                .setBirthDate(LocalDate.of(1999, 9, 28))
                .setMissDate(LocalDate.of(2022, 2, 24))
                .setfinderPhoneNumber("0990993917").setRegion(region2).build();

        Vanished vanished4 = new Vanished.Builder().setVanishedId(4)
                .setType(Vanished.Type.HUMAN).setSex(Vanished.Sex.MALE).setName("Bogdan")
                .setBirthDate(LocalDate.of(1991, 4, 11))
                .setMissDate(LocalDate.of(2021, 9, 19))
                .setfinderPhoneNumber("0680026393").setRegion(region2).build();


        Vanished vanished5 = new Vanished.Builder().setVanishedId(5)
                .setType(Vanished.Type.ANIMAL).setSex(Vanished.Sex.FEMALE).setName("Lily")
                .setBirthDate(LocalDate.of(2019, 6, 26))
                .setMissDate(LocalDate.of(2022, 6, 6))
                .setfinderPhoneNumber("0992890628").setRegion(region2).build();


        LastSeeingPlace p1 = new LastSeeingPlace.Builder().setPlaceId(1)
                .setRegionName("Chernivtsi").setStreetName("Ruska").setHouseNumber("22-A").setVanished(vanished1).build();
        LastSeeingPlace p2 = new LastSeeingPlace.Builder().setPlaceId(2)
                .setRegionName("Chernivtsi").setStreetName("Geroiv Maidanu").setHouseNumber("105").setVanished(vanished2).build();
        LastSeeingPlace p3 = new LastSeeingPlace.Builder().setPlaceId(3)
                .setRegionName("Lviv").setStreetName("Golovna").setHouseNumber("11-B").setVanished(vanished3).build();
        LastSeeingPlace p4 = new LastSeeingPlace.Builder().setPlaceId(4)
                .setRegionName("Lviv").setStreetName("Shevchenka").setHouseNumber("98-C").setVanished(vanished4).build();
        LastSeeingPlace p5 = new LastSeeingPlace.Builder().setPlaceId(5)
                .setRegionName("Lviv").setStreetName("Lesi Ukrainky").setHouseNumber("98-A").setVanished(vanished5).build();




        Service service = new Service("jdbc:mysql://localhost:3306/information_about_vanished", "root", "xyf377j0");

//        service.addRegion(region1);
//        service.addRegion(region2);

//        service.addVanished(vanished1);
//        service.addVanished(vanished2);
//        service.addVanished(vanished3);
//        service.addVanished(vanished4);
//        service.addVanished(vanished5);
//
//        service.addPlace(p1);
//        service.addPlace(p2);
//        service.addPlace(p3);
//        service.addPlace(p4);
//        service.addPlace(p5);






        List<Region> regions = service.getRegions();

        System.out.println("SELECT ALL REGIONS");
        System.out.println(regions);

        System.out.println("\n");

        System.out.println("SELECT REGIONS BY NAME - Lviv");
        System.out.println(service.getRegionByName("Lviv"));

        System.out.println("\n");

        System.out.println("SORT REGIONS BY NAME");
        System.out.println(service.sortRegionByName());



        System.out.println("\n\n\n");

        List<Vanished> vanisheds = service.getVanisheds();

        System.out.println("SELECT ALL VANISHEDS");
        System.out.println(vanisheds);

        System.out.println("\n");

        System.out.println("SELECT VANISHED BY TYPE - Animal");
        System.out.println(service.getVanishedByType("Animal"));

        System.out.println("\n");

        System.out.println("SELECT VANISHED BY SEX - Female");
        System.out.println(service.getVanishedBySex("Female"));

        System.out.println("\n");

        System.out.println("SORT VANISHED BY NAME");
        System.out.println(service.sortVanishedByName());

        System.out.println("\n");

        System.out.println("SORT VANISHED BY MISS DATE");
        System.out.println(service.sortVanishedByMissDate());

        System.out.println("\n");



        System.out.println("\n\n\n");

        List<LastSeeingPlace> lastSeeingPlaces = service.getLastSeeingPlaces();

        System.out.println("SELECT ALL LAST SEEING PLACES");
        System.out.println(lastSeeingPlaces);

        System.out.println("\n");

        System.out.println("SELECT LAST SEEING PLACES BY REGION - Lviv");
        System.out.println(service.getPlaceByRegionName("Lviv"));

        System.out.println("\n");

        System.out.println("SORT LAST SEEING PLACES BY STREET NAME");
        System.out.println(service.sortPlacesByStreetName());

    }
}
