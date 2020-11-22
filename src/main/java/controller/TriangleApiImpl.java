package controller;

import entity.AddTriangleRequest;
import entity.AreaResponse;
import entity.PerimeterResponse;
import entity.TriangleResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.tuple.ImmutablePair;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class TriangleApiImpl {

    private static final String BASE_URL = "https://qa-quiz.natera.com";
    private TriangleApi triangleApi;

    public TriangleApiImpl() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("X-User", "b6c45900-5473-4186-81ed-eca5c70aae20")
                    .build();
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        triangleApi = retrofit.create(TriangleApi.class);
    }

    public ImmutablePair<TriangleResponse, String> addTriangle(AddTriangleRequest request) throws IOException {
        Response<TriangleResponse> response = triangleApi
                .addTriangle(request)
                .execute();

        if (response.isSuccessful()) {
            return new ImmutablePair<>(response.body(), null);
        }
        return new ImmutablePair<>(null, response.errorBody().string());
    }

    public ImmutablePair<TriangleResponse, String> getTriangle(String triangleId) throws IOException {
        Response<TriangleResponse> response = triangleApi.getTriangle(triangleId)
                .execute();

        if (response.isSuccessful()) {
            return new ImmutablePair<>(response.body(), null);
        }

        return new ImmutablePair<>(null, response.errorBody().string());
    }

    public ImmutablePair<Void, String> deleteTriangle(String triangleId) throws IOException {
        Response<Void> response = triangleApi.deleteTriangle(triangleId)
                .execute();

        if (!response.isSuccessful()) {
            return new ImmutablePair<>(null, response.errorBody().string());
        }

        return new ImmutablePair<>(null, null);
    }

    public List<TriangleResponse> getAllTriangles() throws IOException {
        Response<List<TriangleResponse>> response = triangleApi.getAllTriangles()
                .execute();

        if (response.isSuccessful()) {
            return response.body();
        }

        return null;
    }

    public ImmutablePair<Double, String> getArea(String triangleId) throws IOException {
        Response<AreaResponse> response = triangleApi.getArea(triangleId)
                .execute();

        if (response.isSuccessful()) {
            return new ImmutablePair<>(response.body().getResult(), null);
        }

        return new ImmutablePair<>(null, response.errorBody().string());
    }

    public ImmutablePair<Double, String> getPerimeter(String triangleId) throws IOException {
        Response<PerimeterResponse> response = triangleApi.getPerimeter(triangleId)
                .execute();

        if (response.isSuccessful()) {
            return new ImmutablePair<>(response.body().getResult(), null);
        }

        return new ImmutablePair<>(null, response.errorBody().string());
    }
}
