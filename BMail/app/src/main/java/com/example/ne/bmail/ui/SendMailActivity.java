package com.example.ne.bmail.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ne.bmail.R;
import com.example.ne.bmail.classes.SendMail;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendMailActivity extends AppCompatActivity implements View.OnClickListener,TextToSpeech.OnInitListener  {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    TextView show;
    //Send button
    private Button buttonSend;
    public static final Pattern PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    int val=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        show=(TextView)findViewById(R.id.show);
        tts = new TextToSpeech(this, this);

        speakOut();

        //Adding click listener
        buttonSend.setOnClickListener(this);
    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        Matcher matcher = PATTERN.matcher(editTextEmail.getText().toString());
        if(matcher.matches()) {
            sm.execute();
            show.setText("YUP");
        }
        else show.setText("NOPE");
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }

    private void speakOut() {

        String text = "";

        if(val==0)
        {
            text="Recipient Email";
        }

        else if(val==1){
            text="You have entered "+editTextEmail.getText().toString();
            text+="           ";
            text+="Do you confirm? ";
        }

        else if(val==2){
            text="Subject";
        }

        else if(val==3){
            text="You have entered "+editTextSubject.getText().toString();
            text+="           ";
            text+="Do you confirm? ";
        }

        else if(val==4){
            text="Message";
        }


        else if(val==5){
            text="You have entered "+editTextMessage.getText().toString();
            text+="           ";
            text+="Do you confirm? ";
        }

        else if(val==6){
            text="Do you want to send Email?";
        }

        else if(val==7){
            Intent intent=new Intent(SendMailActivity.this,WelcomeActivity.class);
            startActivity(intent);
        }
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

                    String command=result.get(0);

                    show.setText(command);
                    if(result.get(0).trim().equalsIgnoreCase("main")){
                        Intent intent=new Intent(SendMailActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    }

                    else if(val==0){
                        command=command.replaceAll("\\s+","");
                        editTextEmail.setText(command);
                        val++;
                    }

                    else if(val==1){
                        command=command.replaceAll("\\s+","");
                        if(command.equals("yes"))
                        val++;
                        else val--;
                    }

                    else if(val==2){
                        editTextSubject.setText(command);
                        val++;
                    }

                    else if(val==3){
                        command=command.replaceAll("\\s+","");
                        if(command.equals("yes"))
                            val++;
                        else val--;
                    }

                    else if(val==4){
                        editTextMessage.setText(command);
                        val++;
                    }

                    else if(val==5){
                        command=command.replaceAll("\\s+","");
                        if(command.equals("yes"))
                            val++;
                        else val--;
                    }

                    else if(val==6){
                        if(command.trim().equals("yes")){
                            Matcher matcher = PATTERN.matcher(editTextEmail.getText().toString());
                            if(matcher.matches()){
                                sendEmail();
                                Toast.makeText(this, "Mail sent", Toast.LENGTH_SHORT).show();
                                show.setText("Mail sent");
                            }
                            else {
                                Toast.makeText(this, "Wrong Mail Address", Toast.LENGTH_SHORT).show();
                                show.setText("Wrong Mail Address");
                            }
                            val++;
                        }

                        else {

                        }
                    }

                    else if(val==7){
                        command=command.replaceAll("\\s+","");
                        if(command.equals("yes"))
                            val++;
                        else val--;
                    }


                    speakOut();
                }
                }
                break;
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
}