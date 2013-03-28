package ch.heigvd.comem.reflex;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<String> listPhotos;
	
	public ImageAdapter(Context c, ArrayList<String> lp) {
		mContext = c;
		listPhotos = lp;
	}
	
	public int getCount() {
        return listPhotos.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                	
//
//                }
//            });
        } else {
            imageView = (ImageView) convertView;
        }

        try {
			imageView.setImageDrawable(new getImageFromUrl().execute(listPhotos.get(position)).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return imageView;
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

}
