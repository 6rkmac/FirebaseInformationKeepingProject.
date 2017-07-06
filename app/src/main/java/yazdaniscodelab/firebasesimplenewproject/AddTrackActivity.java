package yazdaniscodelab.firebasesimplenewproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    private TextView textViewArtistname;
    private EditText editTextTrackname;
    private SeekBar seekBarRating;
    private Button buttonAddTracks;

    private ListView listViewTracks;

    private List<Track>trackList;

    private DatabaseReference databaseReferenceTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistname=(TextView)findViewById(R.id.textViewartisname);
        editTextTrackname=(EditText)findViewById(R.id.edittextTrack_name);
        seekBarRating=(SeekBar)findViewById(R.id.seekBarRating);
        listViewTracks=(ListView)findViewById(R.id.listview_xml);
        buttonAddTracks=(Button)findViewById(R.id.buttonaddartist);

        Intent intent=getIntent();

        trackList=new ArrayList<>();

        String id=intent.getStringExtra(MainActivity.ARTIST_ID);
        String name=intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistname.setText(name);
        databaseReferenceTracks= FirebaseDatabase.getInstance().getReference("tracks").child(id);


        buttonAddTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTracks();
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferenceTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                trackList.clear();

                for (DataSnapshot tracklist:dataSnapshot.getChildren()){

                    Track track=tracklist.getValue(Track.class);
                    trackList.add(track);

                }

                TrackList mtracklist=new TrackList(AddTrackActivity.this,trackList);

                Collections.reverse(trackList);

                listViewTracks.setAdapter(mtracklist);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveTracks(){

        String trackName=editTextTrackname.getText().toString().trim();
        int rating=seekBarRating.getProgress();

        if (!TextUtils.isEmpty(trackName)){

            String id=databaseReferenceTracks.push().getKey();
            Track track=new Track(id,trackName,rating);
            databaseReferenceTracks.child(id).setValue(track);
            editTextTrackname.setText("");

            Toast.makeText(getApplicationContext(),"Insert Successfully",Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(getApplicationContext(),"You Should Enter Track Name",Toast.LENGTH_LONG).show();
        }

    }



}
