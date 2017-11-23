package orchid.fireorchid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class EventCreator extends AppCompatActivity {

    EditText eventName, eventdesc;
    TextView addr;
    Button createEvent;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator);

        eventName = (EditText) findViewById(R.id.eventName);
        eventdesc = (EditText) findViewById(R.id.eventDescription);
        addr = (TextView) findViewById(R.id.etaddr);
        createEvent = (Button) findViewById(R.id.but);

        ref = FirebaseDatabase.getInstance().getReference();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        final String address = getIntent().getExtras().get("address").toString() +", "+
                getIntent().getExtras().get("City").toString()+", "+getIntent().getExtras().get("state").toString() +
                        ", "+getIntent().getExtras().get("country").toString();

        final double latitude = (double)getIntent().getExtras().get("Latitude");
        final double longitude = (double)getIntent().getExtras().get("Longitude");

        addr.setText(address);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eName = eventName.getText().toString();
                String eDesc = eventdesc.getText().toString();

                int min = 1;
                int max = 1000000;

                Random r = new Random();
                int i1 = r.nextInt(max - min + 1) + min;
                String i = ""+i1;
                ref.child("Events").child(i).child("EventName").setValue(eName);
                ref.child("Events").child(i).child("EventDesc").setValue(eDesc);
                ref.child("Events").child(i).child("EventLat").setValue(latitude);
                ref.child("Events").child(i).child("EventLong").setValue(longitude);
                ref.child("Events").child(i).child("EventAddress").setValue(address);

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Events:
                Intent intent = new Intent(EventCreator.this, EventList.class);
                startActivity(intent);
                break;
            case R.id.signOut:
                startActivity(new Intent(EventCreator.this, Login.class));
                break;
        }
        return true;
    }
}
