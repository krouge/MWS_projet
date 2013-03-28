package ch.heigvd.comem.reflex;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context context;
	private EditText pseudo, password;
	private String userID;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.context = getApplicationContext();
        
        setContentView(R.layout.activity_main);
   
        final Button bt_sign_in = (Button) findViewById(R.id.btLogIn);
        bt_sign_in.setOnClickListener(new OnClickListener() {
      			
	        @Override
	        public void onClick(View v) {
	        	
	        	pseudo = (EditText) findViewById(R.id.etPseudo);
	        	password = (EditText) findViewById(R.id.etPassword);
	        	
	        	new LongRunningGetIO().execute();
	        	
	        }
	    });
        
        if(this.getIntent().getBooleanExtra("subscribed", false)) {
        	
        	Toast toast = Toast.makeText(this, "Thank you for joing us!", Toast.LENGTH_LONG);
        	toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, -50);
        	toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void showSubscribeActivity(View view) {
    	Intent intentHomeActivity = new Intent(context, SubscribeActivity.class);
    	startActivity(intentHomeActivity);
    }
    
    private class LongRunningGetIO extends AsyncTask <Void, Void, Integer> {

    	@SuppressLint("NewApi") 
    	@Override
    	protected Integer doInBackground(Void... params) {
    		
	    	try {
	    		
	    		URL url = new URL(Config.IP_SERVEUR+"Application/webresources/utilisateurs/login");
	    		JSONObject jsonObject = new JSONObject();
	    		jsonObject.put("mdp", password.getText());
	    		jsonObject.put("pseudo", pseudo.getText());
	    		
	    		String message = jsonObject.toString();
	    		Log.d("JSONObject :", message);
	    		
	    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    		connection.setReadTimeout( 10000 /*milliseconds*/ );
	    		connection.setConnectTimeout( 15000 /* milliseconds */ );
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
	     
	    		int response = connection.getResponseCode();
	    		userID = connection.getHeaderField("userID");
	    		connection.disconnect();
	    		return response;
	    	} catch (Exception e) {
	    		Log.d("Erreur: ", e.getLocalizedMessage());
	    		return HttpURLConnection.HTTP_INTERNAL_ERROR;
	    	}
	    	
	    	
    	}
    	
    	@SuppressLint("NewApi") 
    	protected void onPostExecute(Integer response){
    		
    	    if(response == HttpURLConnection.HTTP_OK){
    	    	Log.d("userID", userID);
    	    	Intent intentHomeActivity = new Intent(context, HomeActivity.class);
    	    	intentHomeActivity.putExtra("userID", userID);

		      	startActivity(intentHomeActivity);
		      	MainActivity.this.finish();
    	    }else{
    	    	Log.d("Erreur: ", "Erreur de connexion");
    			Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
    	    }
    	}
    	
    }  
}
