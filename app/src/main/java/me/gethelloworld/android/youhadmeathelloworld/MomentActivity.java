package me.gethelloworld.android.youhadmeathelloworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.gethelloworld.android.youhadmeathelloworld.api.APIManager;
import me.gethelloworld.android.youhadmeathelloworld.api.Hackathon;
import me.gethelloworld.android.youhadmeathelloworld.auth.AuthenticationManager;
import me.gethelloworld.android.youhadmeathelloworld.data.HackathonDataManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MomentActivity extends Activity implements Callback<Void> {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.wtf("upload", "Fail", ex);

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);

        dispatchTakePictureIntent();

        mImageView = (ImageView) findViewById(R.id.moment_image);
    }

    String mCurrentPhotoPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            final AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( getString(R.string.myAccessKey), getString(R.string.mySecretKey) ) );


            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... params) {
                    Bucket bucket = s3Client.createBucket("yhmahw-images");

                    PutObjectRequest por = new PutObjectRequest( bucket.getName() , new File(mCurrentPhotoPath).getName() , new File(mCurrentPhotoPath)).withCannedAcl(CannedAccessControlList.PublicRead);
                    s3Client.putObject( por );

                    String url = "https://yhmahw-images.s3.amazonaws.com/" + new File(mCurrentPhotoPath).getName();

                    return url;
                }

                @Override
                protected void onPostExecute(String url) {

                    String message = "Hello from #" + HackathonDataManager
                            .getCurrentHackathonId(MomentActivity.this).replace(" ", "") + " " + url;
                    APIManager.getAPI(MomentActivity.this).sendTweet(new Tweet(message, url), MomentActivity.this);
                }
            }.execute();



        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.moment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(Void aVoid, Response response) {
        Log.d("Twitter", "Sent tweet to API");
        onBackPressed();

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
