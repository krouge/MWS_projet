package ch.heigvd.comem.reflex;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilActivity extends Activity {
	
	private String userID;
	private Context context;
	private GridView gridview;
	private LinearLayout badge_gallery;
	private ProgressDialog progressDialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		
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

    	badge_gallery = (LinearLayout) findViewById (R.id.badge_gallery);
    	
		gridview = (GridView) findViewById(R.id.gridview);
    	
    	new LongRunningGetIO().execute();
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
				Intent intentAllThemes = new Intent(context, AllThemesActivity.class);
				intentAllThemes.putExtra("userID", userID);
		      	startActivity(intentAllThemes);
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
		        break;
		}
		return true;
	}
	
	private class LongRunningGetIO extends AsyncTask<Void, Void, String> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = progressDialog.show(ProfilActivity.this, "", "Loading...");
		}
		
		@Override
		protected String doInBackground(Void... params) {

			try {

				URL url = new URL(Config.IP_SERVEUR+"Application/webresources/utilisateurs/"+userID+"/profil");

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");

				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Content-Type",
						"application/json;charset=utf-8");
				connection.setRequestProperty("X-Requested-With",
						"XMLHttpRequest");

				connection.connect();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(connection.getInputStream())));

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
		protected void onPostExecute(String response) {
			
			Log.d("Response : ", response);

			if (response.isEmpty()) {
				Log.d("Erreur: ", "Erreur de chargement");
				Toast.makeText(context, "Erreur de chargement",
						Toast.LENGTH_SHORT).show();
			} else {

				ArrayList <String> listPhotos = new ArrayList <String>();

				try {
					JSONObject responseJSON = new JSONObject(response);
					
					TextView pseudo = (TextView) findViewById(R.id.profil_textPseudo);
					pseudo.setText(responseJSON.getString("pseudo"));
					
					TextView firstname = (TextView) findViewById(R.id.profil_firstname);
					firstname.setText(responseJSON.getString("prenom"));
					
					TextView lastname = (TextView) findViewById(R.id.profil_lastname);
					lastname.setText(responseJSON.getString("nom"));
					
					TextView email = (TextView) findViewById(R.id.profil_email);
					email.setText(responseJSON.getString("email"));
					
					try {
    					Object badges = responseJSON.get("badges");
    					
    					if (badges instanceof JSONArray) {
    						JSONArray tagsArray = (JSONArray) badges;
    						for(int j = 0 ; j < tagsArray.length() ; j++){
    							badge_gallery.addView(getBadge(Config.BADGES_PATH+tagsArray.getJSONObject(j).getString("source")));
    						}
    					} else if (badges instanceof JSONObject) {
    						JSONObject tagsObject = (JSONObject) badges;
    						badge_gallery.addView(getBadge(Config.BADGES_PATH+tagsObject.getString("source")));
    					}
					} catch (JSONException e) {
						Log.d("JSONException", e.toString());
					}
					
					try {
    					Object photos = responseJSON.get("photos");
    					
    					if (photos instanceof JSONArray) {
    						JSONArray photosArray = (JSONArray) photos;
    						for(int j = 0 ; j < photosArray.length() ; j++){
    							listPhotos.add(Config.IMAGES_PATH+photosArray.getJSONObject(j).getString("source"));
    						}
    					} else if (photos instanceof JSONObject) {
    						JSONObject photosObject = (JSONObject) photos;
    						listPhotos.add(Config.IMAGES_PATH+photosObject.getString("source"));
    					}
					} catch (JSONException e) {
						Log.d("JSONException", e.toString());
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				gridview.setAdapter(new ImageAdapter(context, listPhotos));

			}
			
			progressDialog.dismiss();
			
		}
		
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
	
	public View getBadge (String source) {
		
		LinearLayout layout = new LinearLayout(context);
	    layout.setLayoutParams(new LayoutParams(130, 130));
	    layout.setGravity(Gravity.CENTER);
	     
	    ImageView imageView = new ImageView(context);
	    imageView.setLayoutParams(new LayoutParams(110, 110));
	    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    try {
			imageView.setImageDrawable(new getImageFromUrl().execute(source).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	    layout.addView(imageView);
	    return layout;

	}

}
