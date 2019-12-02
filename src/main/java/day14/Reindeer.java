package day14;

class Reindeer {
    private final int speedInKmPerSec;
    private final int flyingTimeInSec;
    private final int restingTimeInSec;
    private boolean isFlying;
    private int remaining;
    private int distanceTravelled;
    private int points;

    public Reindeer(int speedInKmPerSec, int flyingTimeInSec, int restingTimeInSec) {
        this.speedInKmPerSec = speedInKmPerSec;
        this.flyingTimeInSec = flyingTimeInSec;
        this.restingTimeInSec = restingTimeInSec;
        this.isFlying = true;
        this.remaining = flyingTimeInSec;
        this.distanceTravelled = 0;
        this.points = 0;
    }

    public void tick() {
        if (isFlying) {
            distanceTravelled += speedInKmPerSec;
        }
        remaining--;
        if (remaining == 0) {
            if (isFlying) {
                isFlying = false;
                remaining = restingTimeInSec;
            } else {
                isFlying = true;
                remaining = flyingTimeInSec;
            }
        }
    }

    public void awardPoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }
}
