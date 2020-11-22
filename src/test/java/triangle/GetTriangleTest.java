package triangle;

import entity.AddTriangleRequest;
import entity.TriangleResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class GetTriangleTest extends BaseTest {

    private static String addedTriangleId;

    @BeforeClass
    public void addTriangle() throws IOException {
        TriangleResponse addedTriangle = controller.addTriangle(new AddTriangleRequest("3;4;5")).left;
        addedTriangleId = addedTriangle.getId();
    }

    @Test
    public void getExistingTriangle() throws IOException {
        ImmutablePair<TriangleResponse, String> foundTriangle = controller.getTriangle(addedTriangleId);
        assertThat(foundTriangle.left)
                .isNotNull();
        assertThat(foundTriangle.right)
                .isNull();
        assertThat(foundTriangle.left.getId())
                .isEqualTo(addedTriangleId);
    }

    @Test(dataProvider = "invalidTriangleId")
    public void getNonexistentTriangle(String triangleId) throws IOException {
        ImmutablePair<TriangleResponse, String> foundTriangle = controller.getTriangle(triangleId);
        assertThat(foundTriangle.left)
                .isNull();

        assertThat(foundTriangle.right)
                .contains("error");
    }

    @DataProvider(name = "invalidTriangleId")
    public Iterator<Object> getInvalidTriangleIds() {
        return Arrays.asList((Object) UUID.randomUUID().toString(), "").iterator();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteTriangleWithNullId() throws IOException {
        controller.deleteTriangle(null);
    }
}
