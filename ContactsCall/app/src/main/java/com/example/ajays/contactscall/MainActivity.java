package com.example.ajays.contactscall;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listItemView;
    // Define string array.
    String[] listItemsValue = new String[] {"Android","PHP","Web Development","Blogger","SEO","Photoshop"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver contactResolver = getContentResolver();
        String contactName;
        Cursor cursor = contactResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", new String[]{contactName}, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String number = cursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }while (cursor.moveToNext() );
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("contacts", listItemView);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);

        /*

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()){
            //get name
            int nameFiledColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFiledColumnIndex);

            String[] PHONES_PROJECTION = new String[] { "_id","display_name","data1","data3"};//
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            //name type ..
            while(phone.moveToNext()) {
                int i = phone.getInt(0);
                String str = phone.getString(1);
                str = phone.getString(2);
                str = phone.getString(3);
            }
            phone.close();
            //addr
            Cursor addrCur = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI ,
                    new String[]{"_id","data1","data2","data3"}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId , null, null);
            while(addrCur.moveToNext()) {
                int i = addrCur.getInt(0);
                String str = addrCur.getString(1);
                str = addrCur.getString(2);
                str = addrCur.getString(3);
            }
            addrCur.close();

            //email
            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI ,
                    new String[]{"_id","data1","data2","data3"}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId , null, null);
            while(emailCur.moveToNext()) {
                int i = emailCur.getInt(0);
                String str = emailCur.getString(1);
                str = emailCur.getString(2);
                str = emailCur.getString(3);
            }
            emailCur.close();

        }
        cursor.close();



        listItemView = (ListView)findViewById(R.id.listItemView);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,
               android.R.id.text1, listItemsValue);

        listItemView.setAdapter(adapter);

        // ListView setOnItemClickListener function apply here.

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, listItemsValue[position], Toast.LENGTH_SHORT).show();
            }
        });

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);


            }
        });     */

    }
}
