package iot.lviv.quarry.api.model;

import iot.lviv.quarry.api.model.enums.HealthStatus;
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
public class Driver {
    private Long driverId;
    private String firstName;
    private String lastName;
    private Integer age;
    private HealthStatus healthStatus;
    private Long carId;

    public static String getHeaders() {
        return "Driver id, first name, last name, age, health status, car";
    }

    public String toCSV() {
        return this.getDriverId() + ", " + this.getFirstName() + ", " + this.getLastName() + ", " + this.getAge() +
                ", " + this.getHealthStatus() + ", " + this.getCarId();
    }
}
