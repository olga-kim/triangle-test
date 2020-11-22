package triangle;

import entity.AddTriangleRequest;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAreaTest extends BaseTest {

    @Test(dataProvider = "areaData")
    public void getArea(AddTriangleRequest addTriangleRequest, Double expectedArea) throws IOException {
        String addedTriangleId = controller.addTriangle(addTriangleRequest).left.getId();

        ImmutablePair<Double, String> result = controller.getArea(addedTriangleId);
        Double area = result.left;

        assertThat(area)
                .isNotNull()
                .isEqualTo(expectedArea);

        assertThat(result.right)
                .isNull();
    }

    @DataProvider(name = "areaData")
    public Object[][] createData1() {
        return new Object[][]{
                {new AddTriangleRequest("3;4;5"), 6.0},
                {new AddTriangleRequest("9;9;9"), 35.074028853269766},
        };
    }

    @Test
    public void getAreaForNonexistentTriangle() throws IOException {
        ImmutablePair<Double, String> result = controller.getArea(UUID.randomUUID().toString());

        assertThat(result.left)
                .isNull();

        assertThat(result.right)
                .contains("\"error\":\"Not Found\",\"exception\":\"com.natera.test.triangle.exception.NotFounException\"");
    }
}
