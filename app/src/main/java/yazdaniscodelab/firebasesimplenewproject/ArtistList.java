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
 * Created by Yazdani on 6/16/2017.
 */

public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist>artistList;

    public ArtistList(Activity context,List<Artist>artistList){

        super(context,R.layout.list_layout,artistList);
        this.context=context;
        this.artistList=artistList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName=(TextView)listViewItem.findViewById(R.id.name_xml);
        TextView textViewGenre=(TextView)listViewItem.findViewById(R.id.genre_xml);
        TextView date = (TextView)listViewItem.findViewById(R.id.txtDate);
        Artist artist=artistList.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());
        date.setText(artist.getmDate());
        return listViewItem;
    }

}
