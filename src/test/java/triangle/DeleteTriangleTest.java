package triangle;

import entity.AddTriangleRequest;
import entity.TriangleResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteTriangleTest extends BaseTest {

    private static String addedTriangleId;

    @BeforeClass
    public void addTriangle() throws IOException {
        TriangleResponse addedTriangle = controller.addTriangle(new AddTriangleRequest("3;4;5")).left;
        addedTriangleId = addedTriangle.getId();
    }

    @Test
    public void deleteTriangle() throws IOException {
        ImmutablePair<Void, String> result = controller.deleteTriangle(addedTriangleId);
        assertThat(result.left)
                .isNull();
        assertThat(result.right)
                .isNull();

        ImmutablePair<TriangleResponse, String> foundTriangle = controller.getTriangle(addedTriangleId);
        assertThat(foundTriangle.left)
                .isNull();
        assertThat(foundTriangle.right)
                .contains("\"error\":\"Not Found\",\"exception\":\"com.natera.test.triangle.exception.NotFounException\",\"message\":\"Not Found\",\"path\":\"/triangle/" + addedTriangleId);
    }

    @Test
    public void deleteNonexistentTriangle() throws IOException {
        ImmutablePair<Void, String> result = controller.deleteTriangle(UUID.randomUUID().toString());
        assertThat(result.left)
                .isNull();
        assertThat(result.right)
                .isNull();
    }

    @Test
    public void deleteTriangleWithEmptyId() throws IOException {
        ImmutablePair<Void, String> result = controller.deleteTriangle("");
        assertThat(result.left)
                .isNull();
        assertThat(result.right)
                .contains("\"error\":\"Method Not Allowed\",\"exception\":\"org.springframework.web.HttpRequestMethodNotSupportedException\",\"message\":\"Request method 'DELETE' not supported\",\"path\":\"/triangle/\"");

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteTriangleWithNullId() throws IOException {
        controller.deleteTriangle(null);
    }

}
