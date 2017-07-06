package yazdaniscodelab.firebasesimplenewproject;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME="artisname";
    public static final String ARTIST_ID="artistid";

    private TextView date;
    private EditText editTextName;
    private Button buttonAdd;
    private Spinner spinnerGenres;
    private DatabaseReference databaseArtist;
    private ListView listView;
    private List<Artist> artistlist;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();


        databaseArtist= FirebaseDatabase.getInstance().getReference("artist");

        editTextName = (EditText) findViewById(R.id.edittext_name);
        buttonAdd = (Button) findViewById(R.id.add_item_btn);
        spinnerGenres = (Spinner) findViewById(R.id.spinner_id_xml);
        //date=(TextView)findViewById(R.id.txtDate);
        listView=(ListView)findViewById(R.id.listview_xml);

        artistlist=new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addaArtist();

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artist artist=artistlist.get(i);
                Intent intent=new Intent(getApplicationContext(),AddTrackActivity.class);
                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artist artist=artistlist.get(i);
                Collections.reverse(artistlist);
                showupdatedialog(artist.getArtistId(),artist.getArtistName());
                return false;
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               artistlist.clear();


                for (DataSnapshot artissnapshot: dataSnapshot.getChildren()){
                    Artist artist=artissnapshot.getValue(Artist.class);
                    artistlist.add(artist);
                }


                ArtistList artistList=new ArtistList(MainActivity.this,artistlist);
                Collections.reverse(artistlist);
                listView.setAdapter(artistList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showupdatedialog(final String id, final String artistname){

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);

        LayoutInflater inflater=getLayoutInflater();

       final View myview=inflater.inflate(R.layout.update_dialog,null);
        builder.setView(myview);


        TextView artisName=(TextView)myview.findViewById(R.id.text_update_xml);
        final EditText updatename=(EditText)myview.findViewById(R.id.update_edittext_id);
        final Spinner spinner=(Spinner)myview.findViewById(R.id.spinner_id_xml);
        Button buttonupdate=(Button)myview.findViewById(R.id.updatedata_btn_xml);
        Button buttondelete=(Button)myview.findViewById(R.id.delet_btn_xml);

        builder.setTitle("updating"+artistname);
        final AlertDialog dialog=builder.create();
        dialog.show();

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=updatename.getText().toString().trim();
                String genre=spinner.getSelectedItem().toString();
                String mDate=DateFormat.getDateTimeInstance().format(new Date());

                String add=name+"+"+genre;

                if (TextUtils.isEmpty(name)){
                    updatename.setError("Name Required..");
                    return;
                }
                updateArtist(id,name,add,mDate);
                dialog.dismiss();

            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(id);
                dialog.dismiss();

            }
        });

    }

    private void deleteArtist(String id){

        DatabaseReference mdatabaseReference=FirebaseDatabase.getInstance().getReference("artist").child(id);
        DatabaseReference drTracks=FirebaseDatabase.getInstance().getReference("tracks").child(id);
        mdatabaseReference.removeValue();
        drTracks.removeValue();

        Toast.makeText(getApplicationContext(),"Artis Delete Successfully",Toast.LENGTH_LONG).show();

    }



    private boolean updateArtist(String id,String name,String genre,String mDate){

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("artist").child(id);
        Artist artist=new Artist(id,name,genre,mDate);
        databaseReference.setValue(artist);

        Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_LONG).show();
        return true;

    }



    private void addaArtist() {

        String name=editTextName.getText().toString().trim();
        String genre=spinnerGenres.getSelectedItem().toString();
        String mDate= DateFormat.getDateTimeInstance().format(new Date());

        String add=name+"+"+genre;


        if (!TextUtils.isEmpty(name)){

            String id=databaseArtist.push().getKey();
            Artist artist=new Artist(id,name,add,mDate);
            databaseArtist.child(id).setValue(artist);

            editTextName.setText("");


            Toast.makeText(getApplicationContext(),"Artist Added",Toast.LENGTH_LONG).show();
        }

        else {

            editTextName.setError("Name Required..");
            //Toast.makeText(getApplicationContext(),"Input Name",Toast.LENGTH_LONG).show();
        }

    }//end addArtist


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:
                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
