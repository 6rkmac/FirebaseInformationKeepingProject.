package yazdaniscodelab.firebasesimplenewproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yazdani on 6/17/2017.
 */

public class TrackList extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> trackList;

    public TrackList(Activity context,List<Track>trackList){

        super(context,R.layout.tracklist_layout,trackList);
        this.context=context;
        this.trackList=trackList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.tracklist_layout,null,true);
        TextView textViewName=(TextView)listViewItem.findViewById(R.id.tracknaem_xml);
        TextView textViewRating=(TextView)listViewItem.findViewById(R.id.rating_xml);

        Track track=trackList.get(position);

        textViewName.setText(track.getTrackName());
        textViewRating.setText(String.valueOf(track.getTrackRating()));

        return listViewItem;
    }

}
