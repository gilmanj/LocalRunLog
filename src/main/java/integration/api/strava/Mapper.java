package integration.api.strava;

import domain.Activity;

/**
 * Created by jared on 10/3/16.
 */
public class Mapper {

    public static Activity from(StravaService.Activity stravaActivity) {
        Activity activity = new Activity();
        activity.setName(stravaActivity.getName());
        activity.setDescription(stravaActivity.getDescription());
        activity.setMovingTime(stravaActivity.getMoving_time());
        activity.setDistance(stravaActivity.getDistance());
        activity.setType(stravaActivity.getType());//TODO create app enum
        activity.setStartDateLocal(stravaActivity.getStart_date_local());
        activity.setAverageSpeed(stravaActivity.getAverage_speed());
        activity.setStravaId(stravaActivity.getId());
        activity.setElapsedTime(stravaActivity.getElapsed_time());
        activity.setTotalElevation(stravaActivity.getTotal_elevation_gain());
        return activity;
    }
}
