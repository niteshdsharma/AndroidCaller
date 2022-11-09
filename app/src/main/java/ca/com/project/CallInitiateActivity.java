package ca.com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class CallInitiateActivity extends AppCompatActivity {
    Bundle b;
    int  id;
    String name;
    Vibrator vib;
    Ringtone r;
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
        setContentView(R.layout.activity_call_initiate);

         b = getIntent().getExtras();

         id= b.getInt("id");

        TextView maintext = findViewById(R.id.txtmain);
        ImageView imgmain = findViewById(R.id.imagemain);


        Glide.with(this).load(id).circleCrop().into(imgmain);

        String[]  txt = b.getString("name").split("_");

        if(txt.length>1) {
            name=txt[0]+"_"+txt[1];
            maintext.setText(txt[0] + " " + txt[1]);
        }else{
            name=txt[0];
            maintext.setText(txt[0]);
        }


        ImageButton rec = findViewById(R.id.recbtn);
        ImageButton hang = findViewById(R.id.hangup);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long[] pattern = { 0, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500};
            vib.vibrate( VibrationEffect.createWaveform(pattern,0));
        } else {
            //deprecated in API 26
            long[] pattern = { 0, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500};
            vib.vibrate(pattern , 0);
        }

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
         r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.stop();
                vib.cancel();
                if(b.getInt("type")==1){
                    Intent i = new Intent(CallInitiateActivity.this ,VideoCall.class);

                    Bundle b = new Bundle();

                    b.putInt("id",id);

                    i.putExtras(b);
                    startActivity(i);
                    finish();

                }else{
                    r.stop();
                    vib.cancel();
                        Intent i = new Intent(CallInitiateActivity.this ,AudioCall.class);

                        Bundle b = new Bundle();

                        b.putInt("id",id);
                        b.putString("name",name);

                        i.putExtras(b);
                        startActivity(i);
                        finish();


                }


                Log.d("happy","happy");
                }



        });


        hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.cancel();
                r.stop();
                finish();

            }
        });




    }


    @Override
    protected void onPause() {
        super.onPause();
        vib.cancel();
        r.stop();
    }
}