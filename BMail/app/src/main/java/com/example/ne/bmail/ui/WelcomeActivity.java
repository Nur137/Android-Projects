package com.example.ne.bmail.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ne.bmail.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivity extends Activity implements TextToSpeech.OnInitListener {
    /** Called when the activity is first created. */
    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextView show;
    int code=0;

    CircleImageView circleImageView;
    String uEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        circleImageView=(CircleImageView)findViewById(R.id.pic);
        show=(TextView)findViewById(R.id.show);
        tts = new TextToSpeech(this, this);
        // button on click event

        Picasso.with(WelcomeActivity.this).load("https://lh3.googleusercontent.com/-1r8AUtsGv0E/Wi_6w9YZU4I/AAAAAAAAACs/8WC_6CXukJQr5BVh-k-RmqSx8qlXiA5bQCEwYBhgL/w139-h140-p/asdasdasd.jpg").into(circleImageView);

        //SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("cust_id", "");
        //editor.putString("wrkr_id", id_no);
        //editor.putString("gps","1");
        //editor.commit();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {
        String text="";

        text="Welcome cubezeera.Do you want to send email or read mails?";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    private void speakOut(int x) {
        String text="";

        text="Invalid Input. Please Try Again";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech prompt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Speech Not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    show.setText(result.get(0).trim());
                    String command=result.get(0);
                    command=command.trim();

                        if(command.equals("send")){
                            Intent intent=new Intent(WelcomeActivity.this,SendMailActivity.class);
                            startActivity(intent);
                        }

                        else if(command.equals("receive")){
                            Intent intent=new Intent(WelcomeActivity.this,ReceiveMailActivity.class);
                            startActivity(intent);
                    }
                    else speakOut(1);
                }
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            //Do something
            promptSpeechInput();
        }
        return true;
    }
}