package triangle;

import entity.AddTriangleRequest;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetPerimeterTest extends BaseTest {

    @Test(dataProvider = "perimeterData")
    public void getPerimeter(AddTriangleRequest addTriangleRequest, Double expectedPerimeter) throws IOException {
        String addedTriangleId = controller.addTriangle(addTriangleRequest).left.getId();

        ImmutablePair<Double, String> result = controller.getPerimeter(addedTriangleId);
        Double perimeter = result.left;

        assertThat(perimeter)
                .isNotNull()
                .isEqualTo(expectedPerimeter);

        assertThat(result.right)
                .isNull();
    }

    @DataProvider(name = "perimeterData")
    public Object[][] createData1() {
        return new Object[][]{
                {new AddTriangleRequest("3;4;5"), 12.0},
                {new AddTriangleRequest("9;9;9"), 27.0},
        };
    }

    @Test
    public void getPerimeterForNonexistentTriangle() throws IOException {
        ImmutablePair<Double, String> result = controller.getPerimeter(UUID.randomUUID().toString());

        assertThat(result.left)
                .isNull();

        assertThat(result.right)
                .contains("\"error\":\"Not Found\",\"exception\":\"com.natera.test.triangle.exception.NotFounException\"");
    }

}
