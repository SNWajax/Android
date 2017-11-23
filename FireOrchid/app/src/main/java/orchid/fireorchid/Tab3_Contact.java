package orchid.fireorchid;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;



//Our class extending fragment
public class Tab3_Contact extends Fragment {

    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    ArrayList<SelectUser> selectUsers;

    ListView listView;

    //SearchView search;
    SelectUserAdapter adapter;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view = inflater.inflate(R.layout.tab3_contacts, container, false);
 //       View content = view.findViewById(R.id.content);
        selectUsers = new ArrayList<SelectUser>();
        listView = (ListView)view.findViewById(R.id.usersList);



    //    usersList = (ListView)view.findViewById(R.id.usersList);
        noUsersText = (TextView)view.findViewById(R.id.noUsersText);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://fireorchid-1e1ab.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);

        /*    Ajays's trial
        CustomList adapter = new
                CustomList(Tab3_Contact.this, request, );
        usersList=(ListView)findViewById(R.id.usersList);
        usersList.setAdapter(adapter);
*/
/*        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);

                Intent intent = new Intent(getContext(), Chat.class);
                intent.putExtra("user",UserDetails.username);
                startActivity(intent);

                //For Tab2_Map
                Intent intent1 = new Intent(getContext(),Tab2_Map.class);
                intent.putExtra("user",UserDetails.username);
            }
        });
  */      return view;
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
               //     al.add(key);
                    SelectUser selectUser = new SelectUser();
                    //         selectUser.setThumb(bit_thumb);
                    selectUser.setName(key);
               //     selectUser.setPhone(phoneNumber);
                    //       selectUser.setEmail(id);
                    //       selectUser.setCheckedBox(false);
                    selectUsers.add(selectUser);

                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
//            usersList.setVisibility(View.VISIBLE);

            adapter = new SelectUserAdapter(selectUsers, getActivity());
            listView.setAdapter(adapter);
            listView.setScrollingCacheEnabled(false);
            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SelectUser data = selectUsers.get(i);
                    UserDetails.chatWith = data.getName();
                    Intent intent = new Intent(getContext(), Chat.class);
                    intent.putExtra("user",data.getName());
                    startActivity(intent);

                    //For Tab2_Map
               //     Intent intent1 = new Intent(getContext(),Tab2_Map.class);
                 //   intent.putExtra("user",data.getName());
                }
            });

            listView.setFastScrollEnabled(true);

    //        usersList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

}