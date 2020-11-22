package entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TriangleResponse {

    private String id;
    private double firstSide;
    private double secondSide;
    private double thirdSide;
}
