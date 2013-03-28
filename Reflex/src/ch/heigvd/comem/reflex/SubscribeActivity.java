package ch.heigvd.comem.reflex;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubscribeActivity extends Activity {

	private Context context;
	private EditText etNickName;
	private EditText etPwd1;
	private EditText etPwd2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
		
		context = getApplicationContext();
		etNickName = (EditText)findViewById(R.id.etNewNickName);
		etPwd1 = (EditText)findViewById(R.id.etNewPwd);
		etPwd2 = (EditText)findViewById(R.id.etConfirmPwd);
	}
	
	public void join(View view) {
		
		if(etNickName.getText().toString().isEmpty() || etPwd1.getText().toString().isEmpty() || etPwd2.getText().toString().isEmpty()){
			Toast.makeText(context, "Meh, some fields are empty...", Toast.LENGTH_LONG).show();
		} else if (!etPwd1.getText().toString().equals(etPwd2.getText().toString())) {
			Toast.makeText(context, "Meh, the passwords are not the same...", Toast.LENGTH_LONG).show();
		} else {
			new subscribeUser().execute();
		}
		
	}
	
	private class subscribeUser extends AsyncTask <Void, Void, Integer> {

    	@Override
    	protected Integer doInBackground(Void... params) {
    		
    		try {
	    		
	    		URL url = new URL(Config.IP_SERVEUR+"Application/webresources/utilisateurs");
	    		JSONObject jsonObject = new JSONObject();
	    		jsonObject.put("pseudo", etNickName.getText());
	    		jsonObject.put("mdp", etPwd1.getText());
	    		jsonObject.put("nom", "");
	    		jsonObject.put("prenom", "");
	    		jsonObject.put("email", "");
	    		
	    		String message = jsonObject.toString();
	    		Log.d("JSONObject :", message);
	    		
	    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
	    		
	    		Log.d("Response","Response "+response);
	    		connection.disconnect();
	    		return response;
	    	} catch (Exception e) {
	    		Log.d("Erreur", "Erreur: "+e.getLocalizedMessage());
	    		return HttpURLConnection.HTTP_INTERNAL_ERROR;
	    	}
    	}

    	protected void onPostExecute(Integer response){
    	    if (response == HttpURLConnection.HTTP_FORBIDDEN){
    	    	Log.d("Erreur: ", "Nickname already in use");
    	    	Toast toast = Toast.makeText(context, "Nickname already in use", Toast.LENGTH_LONG);
    	    	toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, -50);
            	toast.show();
    	    } else if (response == HttpURLConnection.HTTP_OK) {
    	    	
    	    	Intent login = new Intent(context, MainActivity.class);
    	    	login.putExtra("subscribed", true);
    	    	startActivity(login);
    	    	SubscribeActivity.this.finish();
    	    	
    	    } else {
    	    	Log.d("Erreur: ", "Connexion error. Check your connection.");
    	    	Toast toast = Toast.makeText(context, "Error : Check your connection", Toast.LENGTH_LONG);
    	    	toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, -50);
            	toast.show();
    	    }
    	}
    }
}
