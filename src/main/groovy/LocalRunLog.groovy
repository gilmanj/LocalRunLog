import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.ToString
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.*

import com.fasterxml.jackson.databind.ObjectMapper

import groovy.util.logging.Slf4j
import retrofit2.Retrofit

@Slf4j
class LocalRunLog {
    static void main(String ... args) {
        log.info 'starting strava read...'
        ScriptRunner sr = new ScriptRunner()
        sr.go()
        log.info 'finished strava read'
    }
}


@Slf4j
class ScriptRunner {
    final ObjectMapper objectMapper
    ScriptRunner() {
        objectMapper = new ObjectMapper()
    }

    void go() {

        def env = System.getenv()
        String stravaAccessToken = env['STRAVA_ACCESS_TOKEN']
        if(!stravaAccessToken) {
            log.info 'Cannot find strava access token. bailing'
            return
        }
        String authHeader = "Bearer ${stravaAccessToken}"

        Strava stravaAPI = new Retrofit.Builder()
                .baseUrl('https://www.strava.com/api/v3/')
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(Strava)

        Call athleteCall = stravaAPI.getAthlete(authHeader)
        Response athleteResponse = athleteCall.execute()
        if (!athleteResponse.isSuccessful()) {
            log.error "${athleteResponse.code()} Error calling API: ${athleteResponse.errorBody().string()}"
            return
        }
        log.info 'Found athlete'
        log.info athleteResponse.body().toString()

        Call activityCall = stravaAPI.getActivities(authHeader)
        Response activityResponse = activityCall.execute()
        activityResponse.body().each { activity ->
            log.info activity.toString()
        }

  }
}

interface Strava {
    @GET("athlete")
    Call<Athlete> getAthlete(@Header('Authorization') String authHeader)

    @GET("athlete/activities")
    Call<List<Activity>> getActivities(@Header('Authorization') String authHeader)
}

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(includeNames = true)
class Athlete {
    long id
    String firstname
    String lastname

}


@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(includeNames = true)
class Activity {
    long id
    String name
    String description
    long elapsed_time //seconds
    long moving_time //seconds
    BigDecimal distance //meters
    String type //run, ride, etc
    String start_date_local //2012-12-12T19:43:19Z
    BigDecimal total_elevation_gain
    BigDecimal elev_high
    BigDecimal elev_low
    BigDecimal average_speed
    BigDecimal max_speed
    BigDecimal calories
}
