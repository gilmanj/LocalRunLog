import com.fasterxml.jackson.databind.ObjectMapper;
import db.ActivityDAO;
import db.ConnectionManager;
import integration.api.strava.Mapper;
import integration.api.strava.StravaService;

import java.sql.Connection;
import java.util.List;

import static spark.Spark.get;

class LocalRunLog {
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();

        get("/hello", (req, res) -> {

            //TODO move to service
            ActivityDAO activityDAO = new ActivityDAO();
            StravaService stravaService = new StravaService();

            List<StravaService.Activity> stravaActivities = stravaService.retrieveAllActivities();
//            for (StravaService.Activity activity : stravaActivities) {
//                System.out.println(activity);
//            }

            activityDAO.createActivity(Mapper.from(stravaActivities.get(0)));
            return "OK";
        });

        get("/activity/:id", (req, res) -> {
            ActivityDAO activityDAO = new ActivityDAO();

            //Wrap this crap in service
            Long id = null;
            try {
                id = Long.valueOf(req.params("id"));
            } catch(NumberFormatException nfe) {
                res.status(404);
            }
            if(id == null) {
                res.status(404);
            } else {
                return activityDAO.getActivityById(id);
            }
            return null;
        }, objectMapper::writeValueAsString);
    }
}





