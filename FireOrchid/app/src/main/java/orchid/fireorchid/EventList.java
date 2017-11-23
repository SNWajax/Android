package orchid.fireorchid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arr;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        listView = (ListView) findViewById(R.id.listView);
        arr = new ArrayList<>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
        listView.setAdapter(arrayAdapter);

        ref = FirebaseDatabase.getInstance().getReference().child("Events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    String eventNo = data.getKey();
                    String eventName = dataSnapshot.child(eventNo).child("EventName").getValue().toString();

                    arr.add(eventName);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String EventName = (String) parent.getItemAtPosition(position);
                Log.d("Event Name = ",EventName);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            String Event = data.getKey();
                            String eve = dataSnapshot.child(Event).child("EventName").getValue().toString();
                            if(EventName.equals(eve)) {

                                String EventAddr = dataSnapshot.child(Event).child("EventAddress").getValue().toString();
                                String EventDesc = dataSnapshot.child(Event).child("EventDesc").getValue().toString();
                                double latitude = (double) dataSnapshot.child(Event).child("EventLat").getValue();
                                double longitude = (double) dataSnapshot.child(Event).child("EventLong").getValue();


                                Intent intent = new Intent(EventList.this, EventDetail.class);
                                intent.putExtra("EventAddr",EventAddr);
                                intent.putExtra("EventDesc",EventDesc);
                                intent.putExtra("EventName", EventName);
                                intent.putExtra("EventLat",latitude);
                                intent.putExtra("EventLong",longitude );
                                startActivity(intent);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
