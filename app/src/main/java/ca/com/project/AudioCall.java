package ca.com.project;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class AudioCall extends AppCompatActivity {


    Bundle b;
    int  id;
    String name;

    private Long startTime;
    TextView ti;
    MediaPlayer mp;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_audio_call);

        startTime = System.currentTimeMillis();

        b = getIntent().getExtras();

        id= b.getInt("id");

        TextView maintext = findViewById(R.id.txtmain);
        ImageView imgmain = findViewById(R.id.imagemain);
         ti=findViewById(R.id.timer);


        Glide.with(this).load(id).circleCrop().into(imgmain);



        mp = MediaPlayer.create(this,id);



        mp.start();



        String[]  txt = b.getString("name").split("_");

        if(txt.length>1) {
            name=txt[0]+" "+txt[1];
            maintext.setText(txt[0] + " " + txt[1]);
        }else{
            name=txt[0];
            maintext.setText(txt[0]);
        }


        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Long spentTime = System.currentTimeMillis() - startTime;
                Long minius = (spentTime/1000)/60;
                Long seconds = (spentTime/1000) % 60;
                java.text.DecimalFormat nft = new
                        java.text.DecimalFormat("#00.###");
                nft.setDecimalSeparatorAlwaysShown(false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ti.setText("Timer : "+minius+":"+nft.format(seconds));
                    }
                });

            }
        },0,1000);


        ImageButton hang = findViewById(R.id.hngbtn);
        hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AudioCall.this,MainActivity.class);
                startActivity(i);

                MobileAds.initialize(AudioCall.this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {


                        AdRequest adRequest = new AdRequest.Builder().build();

                        InterstitialAd.load(AudioCall.this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                                new InterstitialAdLoadCallback() {
                                    @Override
                                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                        // The mInterstitialAd reference will be null until
                                        // an ad is loaded.
                                        mInterstitialAd = interstitialAd;
                                        Log.i("", "onAdLoaded");

                                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                // Called when fullscreen content is dismissed.
                                                Log.d("TAG", "The ad was dismissed.");
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                // Called when fullscreen content failed to show.
                                                Log.d("TAG", "The ad failed to show.");
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                                // Called when fullscreen content is shown.
                                                // Make sure to set your reference to null so you don't
                                                // show it a second time.
                                                mInterstitialAd = null;
                                                Log.d("TAG", "The ad was shown.");
                                            }
                                        });

                                        if (mInterstitialAd != null) {
                                            mInterstitialAd.show(AudioCall.this);
                                        } else {
                                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                                        }


                                    }

                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        // Handle the error
                                        Log.i("", loadAdError.getMessage());
                                        mInterstitialAd = null;
                                    }
                                });




                    }
                });
                finish();

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        mp.pause();

    }
}