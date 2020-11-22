package triangle;

import entity.AddTriangleRequest;
import entity.TriangleResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllTrianglesTest extends BaseTest {

    private static String addedTriangleId;

    @BeforeClass
    public void addTriangle() throws IOException {
        TriangleResponse addedTriangle = controller.addTriangle(new AddTriangleRequest("3;4;5")).left;
        addedTriangleId = addedTriangle.getId();
    }

    @Test
    public void getAllTriangles() throws IOException {
        List<TriangleResponse> allTriangles = controller.getAllTriangles();
        assertThat(allTriangles)
                .isNotEmpty();
        assertThat(allTriangles.stream().filter(it -> it.getId().equals(addedTriangleId)))
                .isNotEmpty();
    }
}
