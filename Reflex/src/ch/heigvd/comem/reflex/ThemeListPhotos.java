package ch.heigvd.comem.reflex;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ThemeListPhotos extends Activity {
	
	private String userID;
	private String themeID;
	private Context context;
	private ListView listPhotos;
	private TextView title_theme;
	private ArrayList <Drawable> images = new ArrayList <Drawable>();
	
	@SuppressLint("NewApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theme_list_photos);
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    	getActionBar().setCustomView(R.layout.mainmenu1);
        getActionBar().setDisplayShowHomeEnabled(true);
    	getActionBar().setDisplayShowTitleEnabled(true);
    	
    	themeID = getIntent().getStringExtra("themeID");
    	userID = getIntent().getStringExtra("userID");
    	context = getApplicationContext();
		
		title_theme = (TextView) findViewById(R.id.title_theme);
		
		listPhotos = (ListView) findViewById(R.id.listPhotos);
		
		new LongRunningGetIO().execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			Intent intentHome = new Intent(context,
					HomeActivity.class);
			intentHome.putExtra("userID", userID);
			startActivity(intentHome);
			break;
		case R.id.action_list:
			Intent intentAllThemes = new Intent(context,
					AllThemesActivity.class);
			intentAllThemes.putExtra("userID", userID);
			startActivity(intentAllThemes);
			break;
		case R.id.action_photo:
			Intent intentCameraUpload = new Intent(context,PostPictureActivity.class);
			intentCameraUpload.putExtra("userID", userID);
			intentCameraUpload.putExtra("themeID", themeID);
			startActivity(intentCameraUpload);
			break;
		case R.id.action_leaderboard:
			Intent intentLeaderboard = new Intent(context,
					LeaderboardActivity.class);
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
	    		
	    		URL url = new URL(Config.IP_SERVEUR+"Application/webresources/themes/"+themeID+"?photos=1&tagsPhotos=1");
	    		
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
    			
    	    	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    	    	HashMap <String,Object> item;
    	    	Bitmap bm;
    	    	
    	    	try {
    				JSONObject responseJSON = new JSONObject(response);
    				
    				title_theme.setText(responseJSON.get("titre").toString());
    				
    				Object photos = responseJSON.get("photos");
    				
    				if(photos instanceof JSONArray) {
    					JSONArray array = (JSONArray) photos;
        				for(int i = 0 ; i < array.length() ; i++){
        					item = new HashMap <String,Object>();
        					item.put("desc", array.getJSONObject(i).getString("titre"));
            				item.put("img", Config.IMAGES_PATH+array.getJSONObject(i).getString("source"));
            				item.put("like", array.getJSONObject(i).getString("points") + " like");
        					item.put("photoID", array.getJSONObject(i).getString("id"));
        					list.add(item);
        				}
    				} else {
    					item = new HashMap <String,Object>();
    					JSONObject photoJSON = (JSONObject) photos;
    					item.put("desc", photoJSON.getString("titre"));
        				item.put("img", Config.IMAGES_PATH+photoJSON.getString("source"));
        				item.put("like", photoJSON.getString("points") + " like");
    					item.put("photoID", photoJSON.getString("id"));
    					list.add(item);
    				}
    				
    				

    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			String[] from = { "img", "like", "desc", "photoID"};
    			int[] to = { R.id.imageview_photo, R.id.textview_like, R.id.textview_desc, R.id.btn_like};
    			
    			SimpleAdapter simpleadapter = new SimpleAdapter(context, list, R.layout.list_photos, from, to);
    			
    			class BinderImage implements SimpleAdapter.ViewBinder{
    			    @Override
    			    public boolean setViewValue(View view, Object data, String textRepresentation) {
    			    	final String dataView = (String) data;
    			        if(view.getId() == R.id.imageview_photo){
    			            ImageView image = (ImageView) view;
    			            Drawable d = null;
    			            try {
    							d = new getImageFromUrl().execute(dataView).get();
    							image.setImageDrawable(d);
    							image.getLayoutParams().height = 400;
    							image.setScaleType(ImageView.ScaleType.CENTER_CROP);
    						} catch (InterruptedException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						} catch (ExecutionException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    			            return true;
    			        } else if(view.getId() == R.id.btn_like) {
    			        	 ImageButton btn_like = (ImageButton) view;
    			        	 btn_like.setOnClickListener(new View.OnClickListener() {
    			        	        @Override
    			        	        public void onClick(View v) {
    			        	            new LikePhoto().execute(dataView);
    			        	        }
    			        	    });
    	                     return true;
    			        }
    			        return false;
    			    }
    			}
    			
    			simpleadapter.setViewBinder(new BinderImage());
    			listPhotos.setAdapter(simpleadapter);	
    	    	
    	    }
    	    
    	}
    	
	}
	
	protected void onResume() {
		super.onResume();
		new LongRunningGetIO().execute();
	}
	
	private class getImageFromUrl extends AsyncTask <String, Void, Drawable> {
		
		@Override
		protected Drawable doInBackground(String... params) {
		
			String url = params[0];
			
			try {
				InputStream is = (InputStream) new URL(url).getContent();
	 			Drawable d = Drawable.createFromStream(is, "src name");
	 			return d;
		      } catch (Exception e) {
		        return null; 
		      }
			    
		}
		
	}
	
	private class LikePhoto extends AsyncTask <String, Void, String> {

    	@SuppressLint("NewApi") 
    	@Override
    	protected String doInBackground(String... params) {
    		
    		String photoID = params[0];
    		
	    	try {
	    		
	    		URL url = new URL(Config.IP_SERVEUR+"Application/webresources/photos/"+photoID+"/like");
	    		JSONObject jsonUtilisateur = new JSONObject();
	    		jsonUtilisateur.put("id", userID);
	    		
	    		String message = jsonUtilisateur.toString();
	    		Log.d("URL :", url.toString());
	    		Log.d("POST :", message);
	    		
	    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    		connection.setReadTimeout(10000); /*milliseconds*/
	    		connection.setConnectTimeout(15000); /*milliseconds*/
	    		connection.setRequestMethod("POST");
	    		connection.setDoInput(true);
	    		connection.setDoOutput(true);
	    		connection.setFixedLengthStreamingMode(message.getBytes().length);
	    		
	    		connection.setRequestProperty("Accept", "application/json");
	    		connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	    		connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
	    		
	    		connection.connect();
	    		
	    		// Envoi
	    		OutputStream os = new BufferedOutputStream(connection.getOutputStream());
	    		os.write(message.getBytes());
	    		// Nettoyage
	    		os.flush();
	     
	    		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
	     
	    		String output;
	    		String response = "";

	    		while ((output = br.readLine()) != null) {
	    			response += output;
	    		}
	    		
	    		connection.disconnect();
	    		return response;
	    	} catch (Exception e) {
	    		Log.d("Erreur: ", e.getLocalizedMessage());
	    		return "";
	    	}
	    	
	    	
    	}
    	
    	@SuppressLint("NewApi") 
    	protected void onPostExecute(String response){
    		
    		new LongRunningGetIO().execute();
    		
    	}
    	
    }
	
}
