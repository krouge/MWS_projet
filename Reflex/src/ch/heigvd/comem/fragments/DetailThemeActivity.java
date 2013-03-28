package ch.heigvd.comem.fragments;

import ch.heigvd.comem.reflex.R;
import ch.heigvd.comem.reflex.R.id;
import ch.heigvd.comem.reflex.R.layout;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

public class DetailThemeActivity extends Activity {
	  
	  public static final String EXTRA_URL = "url";
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Need to check if Activity has been switched to landscape mode
	    // If yes, finished and go back to the start Activity
	    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
	      finish();
	      return;
	    }
	    setContentView(R.layout.activity_detail_theme);
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	      String s = extras.getString(EXTRA_URL);
	      TextView view = (TextView) findViewById(R.id.detailsText);
	      view.setText(s);
	    }
	  }
	} 
