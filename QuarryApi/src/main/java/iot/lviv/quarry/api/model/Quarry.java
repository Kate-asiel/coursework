package iot.lviv.quarry.api.model;

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
public class Quarry {
    private Long quarryId;
    private String name;
    private String location;

    public static String getHeaders() {
        return "Quarry id, name, location";
    }

    public String toCSV() {
        return this.getQuarryId() + ", " + this.getName() + ", " + this.getLocation();
    }

}