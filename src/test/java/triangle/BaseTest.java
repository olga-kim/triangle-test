package triangle;

import controller.TriangleApiImpl;
import entity.TriangleResponse;
import org.testng.annotations.AfterClass;

import java.io.IOException;
import java.util.List;

public class BaseTest {

    TriangleApiImpl controller = new TriangleApiImpl();

    @AfterClass
    public void cleanUp() throws IOException {
        List<TriangleResponse> allTriangles = controller.getAllTriangles();

        allTriangles.forEach(it -> {
            try {
                controller.deleteTriangle(it.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
