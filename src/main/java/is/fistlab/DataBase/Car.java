package is.fistlab.DataBase;

import lombok.Data;

@Data
public class Car {
    private Long id;
    private String make;
    private String model;
    private int year;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

}
