package orchid.contactedit;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactService extends IntentService {
    ArrayList<SelectUser> selectUsers;
    //    List<SelectUser> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list

 //   String dataToSend;
    // Pop up
  //  ContentResolver resolver;
    //SearchView search;
    SelectUserAdapter adapter;
  //  private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    Cursor phones;
    public ContactService() {
        super("ContactService");
    }


 
    @Override
    protected void onHandleIntent(Intent intent) {

      //   LoadContact loadContact = new LoadContact();
 //        loadContact.execute();
     //    phones = intent.

    }

    class LoadContact extends AsyncTask<Void, Void, Void> {
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
                    Toast.makeText(ContactService.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }
                String lastNumber = "";

                while (phones.moveToNext()) {
                    //      Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    //  String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
             /*   try {
                    if (image_thumb != null) {
                        bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                    } else {
                        Log.e("No Image Thumb", "--------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/              if(phoneNumber.replace("+91","").replace(" ","").length()==10 && !lastNumber.replace(" ", "").contains(phoneNumber)){
                        lastNumber = phoneNumber;
                        SelectUser selectUser = new SelectUser();
                        //         selectUser.setThumb(bit_thumb);
                        selectUser.setName(name);
                        selectUser.setPhone(phoneNumber);
                        selectUser.setEmail(id);
                        selectUser.setCheckedBox(false);
                        selectUsers.add(selectUser);
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
            adapter = new SelectUserAdapter(selectUsers,this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    SelectUser data = selectUsers.get(i);
                    Intent intent = new Intent(ContactService.this, ChatActivity.class);
                    intent.putExtra("id", data.getName());
                    startActivity(intent);
                    Toast.makeText(ContactService.this,data.getPhone(),Toast.LENGTH_LONG).show();

                }
            });

            listView.setFastScrollEnabled(true);
        }
    }


}
