package lab1;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Objects;
import java.time.LocalDate;
import java.util.Set;

/**
 * class "Vanished" with fields: <b>type, sex, name,
 * birthDate, missDate
 * finderPhoneNumber - phone number <b>
 *
 * @author User
 * @version 1.0
 */

public class Vanished implements Comparable<Vanished>{


    public enum Type {ANIMAL, HUMAN;

        public boolean contains(Type animal) {
            if (this.compareTo(animal)== 0){
                return true;
            }
            return false;
        }
    }

    public enum Sex {MALE, FEMALE;

        public boolean contains(Sex female) {
            if (this.compareTo(female)== 0){
                return true;
            }
            return false;
        }
    }

    ;
    public Type type;
    public Sex sex;
//    @Pattern(regexp = "[A-Z][a-z]{1,32}", message = "Name must consists only from letter and first letter must be UpperCase")
    private String name;
//    @PastOrPresent(message = "Birth Date must be a date in the past or in the present")
    private LocalDate birthDate;
//    @PastOrPresent(message = "Miss Date must be a date in the past or in the present")
    private LocalDate missDate;
//    @Pattern(regexp = "[0-9]{10}", message = "Phone number must consists only from digits (10 digits)")
    private String finderPhoneNumber;

    private int vanishedId;

    public int getVanishedId() {
        return vanishedId;
    }

    public void setVanishedId(int vanishedId) {
        this.vanishedId = vanishedId;
    }

    private Region region;

    private int regionId;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getMissDate() {
        return missDate;
    }

    public void setMissDate(LocalDate missDate) {
        this.missDate = missDate;
    }

    public String getFinderPhoneNumber() {
        return finderPhoneNumber;
    }

    public void setFinderPhoneNumber(String finderPhoneNumber) {
        this.finderPhoneNumber = finderPhoneNumber;
    }

    @Override
    public int compareTo(Vanished o) {
        return name.compareTo(o.name);
    }

    /**
     * Overridden function of comparison an instance of
     * the class "Vanished" and an instance of the class "Object"
     *
     * @return returns the boolean value of the comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vanished)) return false;
        Vanished vanished = (Vanished) o;
        return getType() == vanished.getType() && getSex() == vanished.getSex() && Objects.equals(getName(), vanished.getName()) && getBirthDate().equals(vanished.getBirthDate());
    }

    /**
     * Overridden function of obtaining the hash code
     *
     * @return returns the numeric value of the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getType(), getSex(), getName(), getBirthDate());
    }


    /**
     * Class "Builder" with fields: vanished
     *
     * @author User
     * @version 1.0
     */
    public static class Builder {
        private Vanished vanished;

        public Builder() {
            vanished = new Vanished();
        }

        /**
         * Setter type designation
         *
         * @param type - type of vanished
         * @return returns current object
         */
        public Builder setType(Type type) {
            vanished.type = type;
            return this;
        }

        /**
         * Setter sex designation
         *
         * @param sex - sex of vanished
         * @return returns current object
         */
        public Builder setSex(Sex sex) {
            vanished.sex = sex;
            return this;
        }

        /**
         * Setter sex designation
         *
         * @param name - sex of vanished
         * @return returns current object
         */
        public Builder setName(String name) {
            vanished.name = name;
            return this;
        }

        /**
         * Setter sex designation
         *
         * @param birthDate - birth date of vanished
         * @return returns current object
         */
        public Builder setBirthDate(LocalDate birthDate) {
            vanished.birthDate = birthDate;
            return this;
        }

        /**
         * Setter sex designation
         *
         * @param missDate - miss date of vanished
         * @return returns current object
         */
        public Builder setMissDate(LocalDate missDate) {
            vanished.missDate = missDate;
            return this;
        }

        public Builder setVanishedId(int vanishedId){
            vanished.vanishedId = vanishedId;
            return this;
        }

        /**
         * Setter sex designation
         *
         * @param finderPhoneNumber - finder`s phone number
         * @return returns current object
         */
        public Builder setfinderPhoneNumber(String finderPhoneNumber) {
            vanished.finderPhoneNumber = finderPhoneNumber;
            return this;
        }

        public Builder setRegionId(int regionId){
            vanished.regionId = regionId;
            return this;
        }

        public Builder setRegion( Region region){
            vanished.region = region;
            return this;
        }
        /**
         * Function of creating an object of class "Vanished"
         * @return returns new object of class "Vanished"
         */
        public Vanished build() {
            return vanished;
        }
    }

    @Override
    public String toString() {
        return "Vanished{" +
                "type=" + type +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", missDate=" + missDate +
                ", finderPhoneNumber='" + finderPhoneNumber + '\'' +
                ", vanishedId=" + vanishedId +
                ", regionId=" + regionId +
                '}';
    }

    public static void main(String args[]) {
        try{
            Vanished vanished = new Vanished.Builder()
                    .setType(Type.HUMAN)
                    .setSex(Sex.FEMALE)
                    .setName("mariya")
                    .setBirthDate(LocalDate.of(2022, 12, 29))
                    .setMissDate(LocalDate.of(2019, 11, 12))
                    .setfinderPhoneNumber("0680993839")
                    .build();
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }
}
