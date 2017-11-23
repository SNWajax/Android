package orchid.fireorchid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDetail extends AppCompatActivity {

    TextView eventName, eventAddress, eventDescription;
    Button eventMap;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventName = (TextView)findViewById(R.id.eventName);
        eventAddress = (TextView)findViewById(R.id.eventAddress);
        eventDescription = (TextView)findViewById(R.id.eventDescription);
        eventMap = (Button)findViewById(R.id.eventMap);

        String EventAddr = getIntent().getExtras().get("EventAddr").toString();
        String EventDesc = getIntent().getExtras().get("EventDesc").toString();
        String EventName = getIntent().getExtras().get("EventName").toString();
        final double latitude = (double) getIntent().getExtras().get("EventLat");
        final double longitude = (double) getIntent().getExtras().get("EventLong");

        eventName.setText(EventName);
        eventAddress.setText(EventAddr);
        eventDescription.setText(EventDesc);

        eventMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetail.this, EventMap.class);
                intent.putExtra("EventLat", latitude);
                intent.putExtra("EventLong",longitude);
                startActivity(intent);
            }
        });

    }
}
