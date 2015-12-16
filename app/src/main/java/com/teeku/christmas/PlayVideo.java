package com.teeku.christmas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import com.teeku.christmas.R;


import com.teeku.christmas.utilities.DMWebVideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class PlayVideo extends Activity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;
    // Insert your Video URL
    DMWebVideoView mVideoView;
    TextView mViedoTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_play_video);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                build();
        mAdView.loadAd(adRequest);

        String videoKey;
        String videoTitle;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                videoKey= null;
                videoTitle = null;
            } else {
                videoKey= extras.getString("video_key");
                videoTitle = extras.getString("video_title");

            }
        } else {
            videoKey= (String) savedInstanceState.getSerializable("video_key");
            videoTitle = (String) savedInstanceState.getSerializable("video_title");


        }
        //Toast.makeText(this, "Video Key: " + videoKey, Toast.LENGTH_SHORT).show();
        // Find your VideoView in your video_main.xml layout

        mVideoView = ((DMWebVideoView) findViewById(R.id.dmWebVideoView));
        mViedoTitle = ((TextView) findViewById(R.id.play_video_title));

        mViedoTitle.setText(videoTitle);
        mVideoView.setVideoId(videoKey.trim());


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_play_video, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
