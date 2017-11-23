package orchid.contactedit;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class MainActivity extends AppCompatActivity {


    ArrayList<SelectUser> selectUsers;
    //    List<SelectUser> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones;
    String dataToSend;
    // Pop up
    ContentResolver resolver;
    //SearchView search;
    SelectUserAdapter adapter;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        selectUsers = new ArrayList<SelectUser>();
        resolver = getContentResolver();
        listView = (ListView)findViewById(R.id.contacts_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                    null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            Intent contactintent = new Intent(MainActivity.this,ContactService.class);
          //  contactintent.putExtra("phones", String.valueOf(phones));
            startService(contactintent);
            /*LoadContact loadContact = new LoadContact();
            loadContact.execute();

        */}

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.refresh){

        }


        return super.onOptionsItemSelected(item);
    }

    // Load data on background
 /*   class LoadContact extends AsyncTask<Void, Void, Void> {
        JSONObject jsonObject = new JSONObject();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }
                String lastNumber = "";


                while (phones.moveToNext()) {
                    //      Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    //  String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.
                    // PHOTO_THUMBNAIL_URI));
             /*   try {
                    if (image_thumb != null) {
                        bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                    } else {
                        Log.e("No Image Thumb", "--------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/ /*             if(phoneNumber.replace("+91","").replace(" ","").length()==10 && !lastNumber.replace(" ", "").
                            contains(phoneNumber) || phoneNumber.replace("(","").replace(" ","").length()==10 ){

                        lastNumber = phoneNumber;
                        SelectUser selectUser = new SelectUser();
                        //         selectUser.setThumb(bit_thumb);
                        selectUser.setName(name);
                        selectUser.setPhone(phoneNumber);
                        selectUser.setEmail(id);
                        selectUser.setCheckedBox(false);
                        selectUsers.add(selectUser);
                        try{
                            jsonObject.put("params_"+phones,selectUser);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                //        JSONArray jsonArray = new JSONArray(selectUsers);
                //       dataToSend = jsonArray.toString();
                    }
                }
                phones.close();
            }
            else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            /**
             * Method to make json array request where response starts with [
             * */
     /*         //showpDialog();
                String urlJsonArry = "http://192.168.0.10/hackathon/login/temp_contact.php";
                JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                        new Response.Listener<JSONArray>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONArray responseonj = new JSONArray(response);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //   Log.d(TAG, response.toString());
                                adapter = new SelectUserAdapter(selectUsers,MainActivity.this);
                                listView.setAdapter(adapter);

                                /*
                                try {
                                    // Parsing json array response
                                    // loop through each json object
                                    jsonResponse = "";
                                    for (int i = 0; i < response.length(); i++) {

                                        JSONObject person = (JSONObject) response
                                                .get(i);

                                        String name = person.getString("name");
                                        String email = person.getString("email");
                                        JSONObject phone = person
                                                .getJSONObject("phone");
                                        String home = phone.getString("home");
                                        String mobile = phone.getString("mobile");

                                        jsonResponse += "Name: " + name + "\n\n";
                                        jsonResponse += "Email: " + email + "\n\n";
                                        jsonResponse += "Home: " + home + "\n\n";
                                        jsonResponse += "Mobile: " + mobile + "\n\n\n";

                                    }

                                    txtResponse.setText(jsonResponse);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }

                                hidepDialog();

                                }
  */
       /*                 }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //           VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        //             hidepDialog();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("contacts",dataToSend);
                        headers.put("number","9713955387");
                        headers.put("apikey","8c212cbe18336b0a4f55d20e0794b245");
                        return headers;
                    }
                };

                // Adding request to request queue
     //           AppController.getInstance().addToRequestQueue(req);


            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    SelectUser data = selectUsers.get(i);
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    intent.putExtra("id", data.getName());
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,data.getPhone(),Toast.LENGTH_LONG).show();

                }
            });

            listView.setFastScrollEnabled(true);
        }
    }

*/

    @Override
    public void onStop() {
        super.onStop();

    }
}
