package ch.heigvd.comem.fragments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.heigvd.comem.reflex.R;
import ch.heigvd.comem.reflex.R.id;
import ch.heigvd.comem.reflex.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListeThemesActivity extends Activity implements ListThemesFragment.OnItemSelectedListener{
	
	private ListView listThemes;
	private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_themes);
        
        context = getApplicationContext();
        
        listThemes = (ListView) findViewById(R.id.listView1);
        
        //new LongRunningGetIO().execute();
        
    }
    
    @SuppressLint("NewApi")
    @Override
    public void onRssItemSelected(String link) {
  	  DetailThemeFragment fragment = (DetailThemeFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
  	  if (fragment != null && fragment.isInLayout()) {
  		  fragment.setText(link);
  	  } else {
  		  Intent intent = new Intent(getApplicationContext(), DetailThemeActivity.class);
  		  startActivity(intent);
      }
    }
    

    
    private class LongRunningGetIO extends AsyncTask <Void, Void, String> {

    	@Override
    	protected String doInBackground(Void... params) {
    		
	    	try {
	    		
	    		URL url = new URL("http://10.0.2.2:8080/Application/webresources/themes");
	    		
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
    					list.add(item);
    				}

    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    	
    	    	SimpleAdapter simpleadapter = new SimpleAdapter(context, list, R.layout.listview_normal_item, new String[] {"theme"}, new int[] {R.id.listview_text_item});
    	    	listThemes.setAdapter(simpleadapter);
    	    	
    	    }
    	}
    }
} 
