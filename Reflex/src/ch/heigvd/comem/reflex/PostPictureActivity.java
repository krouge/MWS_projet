package ch.heigvd.comem.reflex;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class PostPictureActivity extends Activity {
	
	private Context context;
	private ListView listThemes;
	private int serverResponseCode = 0;
	private Intent data;
	private String userID;
	private String themeID;
	
	// Pour la cam�ra
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private Uri fileUri;
	private File file;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = getApplicationContext();
		
		setContentView(R.layout.activity_postpicture);
		
		userID = getIntent().getStringExtra("userID");
		themeID = getIntent().getStringExtra("themeID");
		
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		//file = new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/Reflex", "IMG_"+timestamp+".jpg");
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Reflex");
		if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	        }
	    }
		file = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+timestamp+".jpg");
		
		
		// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
	    intent.putExtra("return-data", true);

	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);    
	}
	
	protected void onResume() {
		super.onResume();
	}
	
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent dataSrc) {

	    if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            switch( resultCode )
            {
                case 0:
                    Log.i( "MakeMachine", "User cancelled" );
                    this.finish();
                    break;
                case -1:
                    
                	ImageView image = (ImageView)findViewById(R.id.img_picUploaded);
                	Bitmap bm = BitmapFactory.decodeFile(file.getPath());
                	image.setImageBitmap(getRoundedShape(bm));
                	
                	break;
            }
	    }   
	}
	
	public void uploadPicture(View view) {
		
		Button button = (Button)view.findViewById(R.id.button_uploadPic);
		
		new UploadFile(this).execute();
	}
	
	private class UploadFile extends AsyncTask <Void, Void, String> {
		
		private Activity parent;
		
		public UploadFile(Activity parent) {
			this.parent = parent;
		}
		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	        progressDialog=ProgressDialog.show(parent, "", "Loading...");

	    }
		
		@Override
		protected String doInBackground(Void... params) {
		
			try {
				EditText etTitle = (EditText) findViewById(R.id.etTitlePicture);
				
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            Bitmap bm = BitmapFactory.decodeFile(file.getPath());

	            bm.compress(CompressFormat.JPEG, 75, bos);
	            byte[] data = bos.toByteArray();
	            
	            HttpParams param = new BasicHttpParams();
	            HttpProtocolParams.setVersion(param, HttpVersion.HTTP_1_1);
	            HttpProtocolParams.setContentCharset(param, "UTF-8");

	            HttpClient httpClient = new DefaultHttpClient(param);
	            HttpPost postRequest = new HttpPost(Config.IP_SERVEUR+"Application/webresources/file/upload");
	            
	            ByteArrayBody bab = new ByteArrayBody(data, "image");
	            
	            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	            
	            Charset chars = Charset.forName("UTF-8");
	            reqEntity.addPart("img", bab);
	            reqEntity.addPart("userId", new StringBody(userID));
	            reqEntity.addPart("themeId", new StringBody(themeID));
	            reqEntity.addPart("title", new StringBody(etTitle.getText().toString(), chars));
	            postRequest.setEntity(reqEntity);
	            
	            HttpResponse response = httpClient.execute(postRequest);
	            
	            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	            String sResponse;
	            StringBuilder s = new StringBuilder();
	 
	            while ((sResponse = reader.readLine()) != null) {
	                s = s.append(sResponse);
	            }
	            System.out.println("Response: " + s);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            Log.e(e.getClass().getName(), e.getMessage());
	        }
			
			return null;
		}
		
		protected void onProgressUpdate(Integer... progress) {
			
		}

	     protected void onPostExecute(String result) {
	    	 progressDialog.dismiss();
	    	 PostPictureActivity.this.finish();
	     }
		
	}

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		// TODO Auto-generated method stub
		int targetWidth = 350;
		int targetHeight = 350;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				Path.Direction.CCW);
		

		canvas.clipPath(path);
		
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, targetWidth,targetHeight), new Rect(0, 0, targetWidth,targetHeight), null);
		return targetBitmap;
	}
	

}
