package triangle;

import entity.AddTriangleRequest;
import entity.TriangleResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class AddTriangleTest extends BaseTest {

    private static final int MAX_ADDED_TRIANGLES = 10;

    @AfterMethod
    public void clean() throws IOException {
        super.cleanUp();
    }

    @Test(dataProvider = "addTriangleRequest")
    public void addTriangle(AddTriangleRequest request) throws IOException {
        ImmutablePair<TriangleResponse, String> response = controller.addTriangle(request);
        TriangleResponse triangleResponse = response.left;

        assertThat(triangleResponse)
                .isNotNull();
        assertThat(response.right)
                .isNull();

        assertThat(triangleResponse.getId()).isNotEmpty();
    }

    @DataProvider(name = "addTriangleRequest")
    public Iterator<Object> getInvalidTriangleIds() {
        return Arrays.asList((Object) new AddTriangleRequest("1;1;1"),
                new AddTriangleRequest(" ", "1 1 1"))
                .iterator();
    }

    @Test
    public void addNonexistentTriangle() throws IOException {
        AddTriangleRequest request = new AddTriangleRequest("1;2;10");
        ImmutablePair<TriangleResponse, String> response = controller.addTriangle(request);

        assertThat(response.left)
                .isNull();
        assertThat(response.right)
                .isNotEmpty();
        assertThat(response.right)
                .contains("\"exception\":\"com.natera.test.triangle.exception.UnprocessableDataException\",\"message\":\"Cannot process input\",\"path\":\"/triangle\"");
    }

    @Test
    public void addMoreThanTenTriangles() throws IOException {
        AddTriangleRequest request = new AddTriangleRequest("1;1;1");

        //FIXME here is the bug - 11 triangles can be found
        for (int i = 0; i < MAX_ADDED_TRIANGLES; i++) {
            controller.addTriangle(request);
        }

        ImmutablePair<TriangleResponse, String> response = controller.addTriangle(request);

        assertThat(response.left)
                .isNull();
        assertThat(response.right)
                .isNotEmpty();
        assertThat(response.right)
                .contains("\"exception\":\"com.natera.test.triangle.exception.LimitExceededException\",\"message\":\"Limit exceeded\",\"path\":\"/triangle\"");
    }
    
    public void emptyTest(){
    }
}
