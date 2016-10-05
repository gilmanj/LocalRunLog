package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by jared on 10/3/16.
 */
public class Activity {

    private long id;
    private String name;
    private String description;
    private long movingTime;
    private BigDecimal distance;
    private String type; //activityType - run, bike, snowshoe
    private String runType; //EASY, LONG, TEMPO, FARTLEK, RACE, etc
    private String startDateLocal;
    private BigDecimal averageSpeed;
    private long stravaId;
    private long elapsedTime;
    private BigDecimal totalElevation;
    private LocalDateTime importDatetime;

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

    public void setDescription(String description) { this.description = description; }

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

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public long getStravaId() {
        return stravaId;
    }

    public void setStravaId(long stravaId) {
        this.stravaId = stravaId;
    }

    public long getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(long movingTime) {
        this.movingTime = movingTime;
    }

    public String getStartDateLocal() {
        return startDateLocal;
    }

    public void setStartDateLocal(String startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public BigDecimal getAverageSpeed() { return averageSpeed; }

    public void setAverageSpeed(BigDecimal averageSpeed) { this.averageSpeed = averageSpeed; }

    public BigDecimal getTotalElevation() { return totalElevation; }

    public void setTotalElevation(BigDecimal totalElevation) { this.totalElevation = totalElevation; }
}
