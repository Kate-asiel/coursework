package iot.lviv.quarry.api.model;

import iot.lviv.quarry.api.model.enums.CarTypeEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Car {
    private Long carId;
    private String number;
    private CarTypeEnum type;
    private Long quarryId;


    public static String getHeaders() {
        return "Car id, number, type, quarry";
    }

    public String toCSV() {
        return this.getCarId() + ", " + this.getNumber() + ", " + this.getType() + ", " + this.getQuarryId();
    }
}