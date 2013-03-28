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
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class ListThemesFragment extends Fragment {
	  
	  private OnItemSelectedListener listener;
	  private ListView listThemes;
	  private Context context;
	  
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		View view = inflater.inflate(R.layout.fragment_list_themes,container, false);
		
		context = view.getContext();
        
        listThemes = (ListView) view.findViewById(R.id.listView1);
        
        new LongRunningGetIO().execute();
        
	    Button button = (Button) view.findViewById(R.id.button1);
	    button.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View v) {
	        updateDetail();
	      }
	    });
	    return view;
	  }

	  public interface OnItemSelectedListener {
	      public void onRssItemSelected(String link);
	    }
	  
	  @Override
	    public void onAttach(Activity activity) {
	      super.onAttach(activity);
	      if (activity instanceof OnItemSelectedListener) {
	        listener = (OnItemSelectedListener) activity;
	      } else {
	        throw new ClassCastException(activity.toString()
	            + " must implemenet MyListFragment.OnItemSelectedListener");
	      }
	    }
	  
	  
	  // May also be triggered from the Activity
	  public void updateDetail() {
	    // Create fake data
	    String newTime = String.valueOf(System.currentTimeMillis());
	    // Send data to Activity
	    listener.onRssItemSelected(newTime);
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
	    	    	
	    	    	listThemes.setOnItemClickListener(new OnItemClickListener() {
	    	            @Override
	    	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	                
	    	                String item = (String) listThemes.getItemAtPosition(position);
	    	                
	    	                Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
	    	                
	    	            }
	    	        });
	    	    	
	    	    }
	    	}
	    }
	} 