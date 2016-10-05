package integration.api.strava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

/**
 * Created by jared on 9/30/16.
 */
public interface Strava {
    @GET("athlete")
    Call<StravaService.Athlete> getAthlete(@Header("Authorization") String authHeader);

    @GET("athlete/activities")
    Call<List<StravaService.Activity>> getActivities(@Header("Authorization") String authHeader);
}
