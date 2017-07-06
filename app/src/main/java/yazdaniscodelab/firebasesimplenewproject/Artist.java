package yazdaniscodelab.firebasesimplenewproject;

/**
 * Created by Yazdani on 6/16/2017.
 */

public class Artist {

    String artistId;
    String artistName;
    String artistGenre;

    String mDate;

    public Artist(){

    }

    public Artist(String artistId,String artistName,String artistGenre,String mDate) {
        this.artistId = artistId;
        this.artistGenre = artistGenre;
        this.artistName = artistName;
        this.mDate=mDate;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }


    public String getmDate() {
        return mDate;
    }
}
