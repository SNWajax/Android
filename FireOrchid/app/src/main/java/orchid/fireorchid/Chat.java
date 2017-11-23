package orchid.fireorchid;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    TextView chatTV;
    Firebase reference1, reference2;

    long t = System.currentTimeMillis();

    SimpleDateFormat time = new SimpleDateFormat("\t h:mm a");
    String timeString = time.format(t);

    SimpleDateFormat date = new SimpleDateFormat("\t MMM MM dd, yyyy");
    String dateString = date.format(t);
    //         textView.setText(dateString);


    //LOcal Database

    String _users;
    String _messages;
    public Chat(){

    }
    public Chat(String users, String message){
        this._users = users;
        this._messages = message;
    }
    public String getID() {
        return this._users;
    }
    public void setID(String users){
        this._users = users;
    }
    public String getMessage(){
        return this._messages;
    }
    public void setMessage(String message){
        this._messages = message;
    }

    //ENd of Database code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Intent intent = getIntent();
        String title = intent.getStringExtra("user");
   //     getSupportActionBar().setTitle(title);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String User = (String)getIntent().getExtras().get("user");
        Bundle bundle = new Bundle();


        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        chatTV = (TextView) findViewById(R.id.chatTV);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://fireorchid-1e1ab.firebaseio.com/messages/" + UserDetails.username
                + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://fireorchid-1e1ab.firebaseio.com/messages/" + UserDetails.chatWith
                + "_" + UserDetails.username);

        chatTV.setText(UserDetails.chatWith);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("time", timeString);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String time = map.get("time").toString();

                if (userName.equals(UserDetails.username)) {
                    addMessageBox("You:-\n" + message + timeString, 1);
                } else {
                    addMessageBox(UserDetails.chatWith + ":-\n" + message + time, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type) {
        //  TextView textView = (TextView)findViewById(R.id.text);
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
            textView.setGravity(Gravity.RIGHT);

        /*    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
            lp.setMargins(0, 0, 0, 10);
            textView.setLayoutParams(lp);       */


            //     textView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            //             ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            textView.setBackgroundResource(R.drawable.rounded_corner2);
            textView.setGravity(Gravity.LEFT);
       /*     LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT);
            lp.setMargins(0, 0, 0, 10);
            textView.setLayoutParams(lp);       */

        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}