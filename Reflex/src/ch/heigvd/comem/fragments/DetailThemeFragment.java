package ch.heigvd.comem.fragments;

import ch.heigvd.comem.reflex.R;
import ch.heigvd.comem.reflex.R.id;
import ch.heigvd.comem.reflex.R.layout;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class DetailThemeFragment extends Fragment {

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		  View view = inflater.inflate(R.layout.fragment_detail_theme, container, false);
	    return view;
	  }

	  public void setText(String item) {
	    TextView view = (TextView) getView().findViewById(R.id.detailsText);
	    view.setText(item);
	  }
	}
