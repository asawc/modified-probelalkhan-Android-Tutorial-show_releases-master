package myapp.releaseActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import net.simplifiedcoding.simplifiedcoding.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import myapp.JSONParser;
import myapp.ShowEmployeesActivity;

import static myapp.api.URLs.URL_GET_ALL_RELEASES;

public class ReleaseActivity extends ListActivity {

    JSONParser jsonParser = new JSONParser();

    public ArrayList<HashMap<String, String>> releasesList;

    //private static String url_display_user = "http://10.0.3.2/android_connect/display_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "error";
    private static final String TAG_MESSAGE = "message";

    private static final String TAG_ID = "id";

    private static final String TAG_EMPLOYEES = "employeeslist";

    private static final String TAG_EMPLOYEE = "employee";

    //private static final String TAG_NAME = "name";

    // employees JSONArray
    JSONArray employees = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_activity);

        releasesList = new ArrayList<HashMap<String, String>>();

        new getReleasesList().execute();

        // getListView
        ListView lv = getListView();
//
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                //  String id = ((TextView) view.findViewById(R.id.uid)).getText()
                //          .toString();

                // Intent in = new Intent(getBaseContext(), StatusList.class);
                // in.putExtra(TAG_ID, uid);

                // startActivity(in);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // tutaj nowa aktywność dodawania wydań
                Intent intent = new Intent(ReleaseActivity.this, AddReleaseActivityCustViewDial.class);
                startActivity(intent);
            }
        });
    }

    class getReleasesList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ReleaseActivity.this.setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            // Building Parameters
            List<NameValuePair> parametres = new ArrayList<NameValuePair>();

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(URL_GET_ALL_RELEASES);
            String json = null;
            try {
                HttpResponse response = client.execute(request);
                HttpEntity httpEntity = response.getEntity();
                json = EntityUtilsHC4.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String json) {

            Boolean result = null;
            String message = null;

            Log.d("All Releases: ", json);
            // dismiss the dialog after getting all releases
            try {
                JSONObject jsonObj = new JSONObject(json);
                // wybranie tablicy releases
                result = jsonObj.getBoolean("error");
                message = jsonObj.getString("message");

                JSONArray jsonArray = jsonObj.getJSONArray("object");
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Wybranie pojedyńczego obiektu w tablicy - release
                    JSONObject jsonObj2 = jsonArray.getJSONObject(i);
                    // Wybranie poszczególnych
                    // id= jsonObj.getString("id").toInteger ?? // id konwertuj na int
                    String id = jsonObj2.getString("id");
                    String date_creation = jsonObj2.getString("date_creation");
                    String status = jsonObj2.getString("status");
                    String id_employee = jsonObj2.getString("id_employee");

                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("id", id);
                    map.put("date_creation", date_creation);
                    map.put("status", status);
                    map.put("id_employee", id_employee);


                    // adding HashList to ArrayList
                    releasesList.add(map);
                }
            } catch(Exception e) {
                Log.e("Error", e.getMessage());
                Toast.makeText(getBaseContext(), "Error while parsing response - " + e.getMessage(),  Toast.LENGTH_LONG).show();
            }

            if (result != null && (result == false)) {

                ReleaseActivity.this
                        .setProgressBarIndeterminateVisibility(false);
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into ListView
                         * */
                        ListAdapter adapter = new SimpleAdapter(
                                ReleaseActivity.this, releasesList,
                                R.layout.content_release, new String[] { //TAG_ID,
                                "id", "date_creation", "status", "surname", "name"}, new int[] { //R.id.id,
                                R.id.id_release, R.id.date_creation,  R.id.status, R.id.employee_surname, R.id.employee_name/*R.id.id_employee*/});
                        // updating listview
                        setListAdapter(adapter);
                    }
                });

                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_list, menu);
        return true;
    }*/
}