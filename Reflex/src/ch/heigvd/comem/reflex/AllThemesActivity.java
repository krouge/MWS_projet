package ch.heigvd.comem.reflex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AllThemesActivity extends Activity {
	
	private ListView listThemes;
	private Context context;
	private String userID;

	@SuppressLint("NewApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_themes);
		
		userID = getIntent().getStringExtra("userID");
		context = getApplicationContext();
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.mainmenu1);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		
		ImageButton btNewTheme = (ImageButton) findViewById(R.id.bt_create_new_theme);
		btNewTheme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentCreateNewTheme = new Intent(context,CreateThemeActivity.class);
				intentCreateNewTheme.putExtra("userID", userID);
				startActivity(intentCreateNewTheme);
			}
		});
        
        listThemes = (ListView) findViewById(R.id.list_all_themes);
        
        new LongRunningGetIO().execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
			case R.id.action_home:
				Intent intentHome = new Intent(context, HomeActivity.class);
				intentHome.putExtra("userID", userID);
		      	startActivity(intentHome);
		        break;
			case R.id.action_list:
		        break;
			case R.id.action_photo:
				Toast.makeText(context, "Possible uniquement dans un thème", Toast.LENGTH_SHORT).show();
				break;
		    case R.id.action_leaderboard:
		    	Intent intentLeaderboard = new Intent(context, LeaderboardActivity.class);
		    	intentLeaderboard.putExtra("userID", userID);
		      	startActivity(intentLeaderboard);
		    	break;
		    case R.id.action_profil:
		    	Intent intentProfil = new Intent(context, ProfilActivity.class);
		    	intentProfil.putExtra("userID", userID);
		      	startActivity(intentProfil);
		        break;
		}
		return true;
	}
	
	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {

    	@Override
    	protected String doInBackground(Void... params) {
    		
	    	try {
	    		
	    		URL url = new URL(Config.IP_SERVEUR+"Application/webresources/themes");
	    		
	    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    		connection.setRequestMethod("GET");
	    		
	    		connection.setRequestProperty("Accept", "application/json");
	    		connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	    		connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
	    		
	    		connection.connect();
	     
	    		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
	     
	    		String output;
	    		String jsonString = "";

	    		while ((output = br.readLine()) != null) {
	    			jsonString += output;
	    		}
	    		
	    		connection.disconnect();
	    		return jsonString;
	    	} catch (Exception e) {
	    		return e.getLocalizedMessage();
	    	}
	    	
	    	
    	}
    	
    	@SuppressLint("NewApi")
    	protected void onPostExecute(String response){
    		
    		Log.d("Response : ", response);
    		
    	    if(response.isEmpty()){
    	    	Log.d("Erreur: ", "Erreur de chargement");
    			Toast.makeText(context, "Erreur de chargement", Toast.LENGTH_SHORT).show();
    	    }else{
    			
    	    	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    	    	HashMap <String,String> item;
    	    	
    	    	try {
    				JSONObject responseJSON = new JSONObject(response);
    				JSONArray array = responseJSON.getJSONArray("themeDTO");
    				for(int i = 0 ; i < array.length() ; i++){
    					item = new HashMap <String,String>();
    					item.put("theme", array.getJSONObject(i).getString("titre"));
    					item.put("themeID", array.getJSONObject(i).getString("id"));
    					list.add(item);
    				}

    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    	
    	    	String[] from = { "theme" };
    			int[] to = { R.id.listview_text_item };
    	    	
    	    	SimpleAdapter simpleadapter = new SimpleAdapter(context, list, R.layout.listview_normal_item, from, to);
    	    	listThemes.setAdapter(simpleadapter);
    	    	
    	    	listThemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	    		@Override
    	    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
    	    			
    	    			HashMap item = (HashMap) listThemes.getItemAtPosition(position);
    	    			
    	    			Intent intentListThemes = new Intent(context, ThemeListPhotos.class);
    					intentListThemes.putExtra("userID", userID);
    					intentListThemes.putExtra("themeID", item.get("themeID").toString());
    			      	startActivity(intentListThemes);

    	    		}
    	    	});
    	    	
    	    }
    	}
    }

}
