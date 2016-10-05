package integration.api.strava;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by jared on 9/30/16.
 */
public class StravaService {

    private static final Logger log = LoggerFactory.getLogger(StravaService.class);

    private Strava stravaAPI;
    private String authHeader;

    public StravaService() {
        final ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> env = System.getenv();
        final String stravaAccessToken = env.get("STRAVA_ACCESS_TOKEN");
        if (!StringGroovyMethods.asBoolean(stravaAccessToken)) {
            log.info("Cannot find strava access token. bailing");
            return;
        }

        authHeader = "Bearer " + stravaAccessToken;
        stravaAPI = new Retrofit.Builder().baseUrl("https://www.strava.com/api/v3/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build().create(Strava.class);

    }

    public List<Activity> retrieveAllActivities() {
        Call<List<Activity>> activityCall = stravaAPI.getActivities(authHeader);
        try {
            final Response<List<Activity>> activityResponse = activityCall.execute();
            if (!activityResponse.isSuccessful()) {
                log.error(String.valueOf(activityResponse.code()) + " Error calling API: " + activityResponse.errorBody().string());
                return null;
            }

            return activityResponse.body();
        } catch (IOException ioe) {
            log.error("IOException found when retrieving activity list");
            ioe.printStackTrace();
        }
            return null;
    }

    public Athlete getAthlete() {
        Call<Athlete> athleteCall = stravaAPI.getAthlete(authHeader);
        try {
            final Response<Athlete> athleteResponse = athleteCall.execute();
            if (!athleteResponse.isSuccessful()) {
                log.error(String.valueOf(athleteResponse.code()) + " Error calling API: " + athleteResponse.errorBody().string());
                return null;

            }
            return athleteResponse.body();
        } catch (IOException ioe) {
            log.error("IOException found when retrieving activity list");
            ioe.printStackTrace();
        }
        return null;
    }

    public Strava getStravaAPI() {
        return stravaAPI;
    }

    public void setStravaAPI(Strava stravaAPI) {
        this.stravaAPI = stravaAPI;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Activity {

        private long id;
        private String name;
        private String description;
        private long elapsed_time;
        private long moving_time;
        private BigDecimal distance;
        private String type;
        private String start_date_local;
        private BigDecimal total_elevation_gain;
        private BigDecimal elev_high;
        private BigDecimal elev_low;
        private BigDecimal average_speed;
        private BigDecimal max_speed;
        private BigDecimal calories;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getElapsed_time() {
            return elapsed_time;
        }

        public void setElapsed_time(long elapsed_time) {
            this.elapsed_time = elapsed_time;
        }

        public long getMoving_time() {
            return moving_time;
        }

        public void setMoving_time(long moving_time) {
            this.moving_time = moving_time;
        }

        public BigDecimal getDistance() {
            return distance;
        }

        public void setDistance(BigDecimal distance) {
            this.distance = distance;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStart_date_local() {
            return start_date_local;
        }

        public void setStart_date_local(String start_date_local) {
            this.start_date_local = start_date_local;
        }

        public BigDecimal getTotal_elevation_gain() {
            return total_elevation_gain;
        }

        public void setTotal_elevation_gain(BigDecimal total_elevation_gain) {
            this.total_elevation_gain = total_elevation_gain;
        }

        public BigDecimal getElev_high() {
            return elev_high;
        }

        public void setElev_high(BigDecimal elev_high) {
            this.elev_high = elev_high;
        }

        public BigDecimal getElev_low() {
            return elev_low;
        }

        public void setElev_low(BigDecimal elev_low) {
            this.elev_low = elev_low;
        }

        public BigDecimal getAverage_speed() {
            return average_speed;
        }

        public void setAverage_speed(BigDecimal average_speed) {
            this.average_speed = average_speed;
        }

        public BigDecimal getMax_speed() {
            return max_speed;
        }

        public void setMax_speed(BigDecimal max_speed) {
            this.max_speed = max_speed;
        }

        public BigDecimal getCalories() {
            return calories;
        }

        public void setCalories(BigDecimal calories) {
            this.calories = calories;
        }

        @Override
        public String toString() {
            return "Activity{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", elapsed_time=" + elapsed_time +
                    ", moving_time=" + moving_time +
                    ", distance=" + distance +
                    ", type='" + type + '\'' +
                    ", start_date_local='" + start_date_local + '\'' +
                    ", total_elevation_gain=" + total_elevation_gain +
                    ", elev_high=" + elev_high +
                    ", elev_low=" + elev_low +
                    ", average_speed=" + average_speed +
                    ", max_speed=" + max_speed +
                    ", calories=" + calories +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Athlete {
        private long id;
        private String firstname;
        private String lastname;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        @Override
        public String toString() {
            return "Athlete{" +
                    "id=" + id +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    '}';
        }
    }
}
