package ch.heigvd.comem.reflex;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import ch.heigvd.comem.fragments.ListeThemesActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateThemeActivity extends Activity {
	
	private Context context;
	private EditText newTheme;
	private String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_theme);
		
		context = getApplicationContext();
		userID = getIntent().getStringExtra("userID");
		
		Button btCreateNewTheme = (Button) findViewById(R.id.btCreateNewTheme);
		btCreateNewTheme.setOnClickListener(new View.OnClickListener() {
	  	      @Override
	  	      public void onClick(View v) {
	        	newTheme = (EditText) findViewById(R.id.etNewTheme);
	        	if(!newTheme.getText().toString().isEmpty()) {
	        		new LongRunningGetIO().execute();
	        	}
	  	      }
	  	    });
		
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
		    	Intent intentProfil = new Intent(context, ProfilActivity.class);
		    	intentProfil.putExtra("userID", userID);
		      	startActivity(intentProfil);
		        break;
		}
		return true;
	}
	
	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {

    	@SuppressLint("NewApi") 
    	@Override
    	protected String doInBackground(Void... params) {
    		
	    	try {
	    		
	    		URL url = new URL(Config.IP_SERVEUR+"Application/webresources/themes");
	    		JSONObject jsonTheme = new JSONObject();
	    		JSONObject jsonUtilisateur = new JSONObject();
	    		jsonTheme.put("titre", newTheme.getText());
	    		jsonUtilisateur.put("id", userID);
	    		jsonTheme.put("utilisateur", jsonUtilisateur);
	    		
	    		String message = jsonTheme.toString();
	    		Log.d("JSONObject :", message);
	    		
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
    		Intent intentHomeActivity = new Intent(context, HomeActivity.class);
    		intentHomeActivity.putExtra("userID", userID);
		    startActivity(intentHomeActivity);
    	}
    	
    }

}
