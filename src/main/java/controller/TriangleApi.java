package controller;

import entity.AddTriangleRequest;
import entity.AreaResponse;
import entity.PerimeterResponse;
import entity.TriangleResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.io.IOException;
import java.util.List;

public interface TriangleApi {

    @POST("/triangle")
    Call<TriangleResponse> addTriangle(@Body AddTriangleRequest request) throws IOException;

    @GET("/triangle/{triangleId}")
    Call<TriangleResponse> getTriangle(@Path("triangleId") String triangleId);

    @DELETE("/triangle/{triangleId}")
    Call<Void> deleteTriangle(@Path("triangleId") String triangleId);

    @GET("/triangle/all")
    Call<List<TriangleResponse>> getAllTriangles();

    @GET("/triangle/{triangleId}/perimeter")
    Call<PerimeterResponse> getPerimeter(@Path("triangleId") String triangleId);

    @GET("/triangle/{triangleId}/area")
    Call<AreaResponse> getArea(@Path("triangleId") String triangleId);
}
