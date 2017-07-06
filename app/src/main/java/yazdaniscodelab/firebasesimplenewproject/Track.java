package yazdaniscodelab.firebasesimplenewproject;

/**
 * Created by Yazdani on 6/17/2017.
 */

public class Track {

    private String trackId;
    private String trackName;
    private int trackRating;

    public Track(){

    }

    public Track(String trackId, String trackName, int trackRating) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRating = trackRating;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getTrackId() {
        return trackId;
    }

    public int getTrackRating() {
        return trackRating;
    }


}
