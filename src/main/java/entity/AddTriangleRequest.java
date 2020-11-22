package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddTriangleRequest {
    private String separator = ";";
    private String input;

    public AddTriangleRequest(String input) {
        this.input = input;
    }
}
