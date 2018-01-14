package com.example.ne.bmail.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ne.bmail.R;
import com.example.ne.bmail.arraylist.ReceiveMailArrayList;
import com.example.ne.bmail.classes.ReceiveMail;

import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
public class ReceiveMailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ArrayList<ReceiveMail> receiveMails;
    ListView list;
    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Button read,unread;
    int mailNo=-2;

    TextView show;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_mail);

        read=(Button)findViewById(R.id.read);
        unread=(Button)findViewById(R.id.unread);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        show=(TextView)findViewById(R.id.show);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailNo=-1;
                receiveMails.clear();
                new MailReader("all");
                setList();
                speakOut();

                Toast.makeText(ReceiveMailActivity.this, Integer.toString(receiveMails.size()), Toast.LENGTH_SHORT).show();
            }
        });


        unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailNo=-1;
                receiveMails.clear();
                new MailReader("unread");
                setList();
                speakOut();

                Toast.makeText(ReceiveMailActivity.this, Integer.toString(receiveMails.size()), Toast.LENGTH_SHORT).show();
            }
        });



        list=(ListView)findViewById(R.id.list);
        tts = new TextToSpeech(this, this);
        receiveMails=new ArrayList<>();
        String hostval = "pop.gmail.com";
        String mailStrProt = "pop3";
        String uname = "mosharafnur@gmail.com";
        String pwd = "pigeon123";
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

        if(mailNo==-2){
            String text = "Do you want to read unread mails or read mails? ";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

        else if(mailNo==-1){
            String text = "You have mail from ";
            for(int i=0;i<receiveMails.size();i++){
                text+=(i+1)+"   ";
                text+=receiveMails.get(i).getFrom();
            }
            text+="Which mail do you wanna read?";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(mailNo>-1){
            String text = "";
            //for(int i=mailNo;i<receiveMails.size();i++){
              //  text+="Mail from   ";
              //  text+=receiveMails.get(i).getFrom();
                text+="Mail Subject";

                text+=receiveMails.get(mailNo-1).getSubject();


                text+="Message   ";
                text+=receiveMails.get(mailNo-1).getMail();

            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            if(text!=null)
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            mailNo=-2;
            }

    }

    private void speakOut(int x) {
        String text="";

        text="Invalid Input. Please Try Again";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public class MailReader
    {
        Folder inbox;
        //Constructor of the calss.
        public MailReader(String type)
        {
        /*  Set the mail properties  */
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            try
            {
            /*  Create the session and get the store for read the mail. */
                Session session = Session.getDefaultInstance(props, null);
                Store store = session.getStore("imaps");
                store.connect("imap.gmail.com","mosharafnur@gmail.com", "pigeon123");

            /*  Mention the folder name which you want to read. */
                inbox = store.getFolder("Inbox");
                System.out.println("No of Unread Messages : " + inbox.getUnreadMessageCount());

            /*Open the inbox using store.*/
                inbox.open(Folder.READ_ONLY);

            /*  Get the messages which is unread in the Inbox*/
                Message messages[];
                if(type.equals("unread")) {
                    messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                }
                else{
                    messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), true));
                }


            /* Use a suitable FetchProfile    */
                FetchProfile fp = new FetchProfile();
                fp.add(FetchProfile.Item.ENVELOPE);
                fp.add(FetchProfile.Item.CONTENT_INFO);
                inbox.fetch(messages, fp);

                try
                {
                    printAllMessages(messages);
                    inbox.close(true);
                    store.close();
                }
                catch (Exception ex)
                {
                    System.out.println("Exception arise at the time of read mail");
                    ex.printStackTrace();
                }
            }
            catch (NoSuchProviderException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
                System.exit(2);
            }
        }

        public void printAllMessages(Message[] msgs) throws Exception
        {
            for (int i = 0; i < msgs.length; i++)
            {
                Toast.makeText(ReceiveMailActivity.this, "MESSAGE #" + Integer.toString(i + 1) + ":", Toast.LENGTH_LONG).show();
                System.out.println("MESSAGE #" + (i + 1) + ":");
                printEnvelope(msgs[i]);
            }
        }

        /*  Print the envelope(FromAddress,ReceivedDate,Subject)  */
        public void printEnvelope(Message message) throws Exception
        {
            ReceiveMail receiveMail=new ReceiveMail();

            Address[] a;
            // FROM
            if ((a = message.getFrom()) != null)
            {
                for (int j = 0; j < a.length; j++)
                {
                   Toast.makeText(ReceiveMailActivity.this, "FROM: " + a[j].toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("FROM: " + a[j].toString());
                    receiveMail.setFrom(a[j].toString());
                }
            }
            // TO
            if ((a = message.getRecipients(Message.RecipientType.TO)) != null)
            {
                for (int j = 0; j < a.length; j++)
                {
                    System.out.println("TO: " + a[j].toString());
                }
            }

            String subject = message.getSubject();
            Date receivedDate = message.getReceivedDate();
            String content = message.getContent().toString();
            System.out.println("Subject : " + subject);
            System.out.println("Received Date : " + receivedDate.toString());
            System.out.println("Content : " + content);

            Multipart multipart = (Multipart) message.getContent();

            for (int j = 0; j < multipart.getCount(); j++) {

                BodyPart bodyPart = multipart.getBodyPart(j);

                String disposition = bodyPart.getDisposition();

                if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) { // BodyPart.ATTACHMENT doesn't work for gmail
                    System.out.println("Mail have some attachment");

                    DataHandler handler = bodyPart.getDataHandler();
                    System.out.println("file name : " + handler.getName());
                } else {
                    System.out.println("Body: " + bodyPart.getContent());
                    content = bodyPart.getContent().toString();
                }
            }
            String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
            content=content.replaceAll(HTML_TAG_PATTERN,"");

            receiveMail.setSubject(subject);

            receiveMail.setMail(content);

            receiveMails.add(receiveMail);
        }
    }

    public void setList()
    {
        ReceiveMailArrayList receiveMailArrayList = new ReceiveMailArrayList(this,receiveMails);
        list.setAdapter(receiveMailArrayList);
        speakOut();
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
                    show.setText(result.get(0));

                    if(result.get(0).trim().equalsIgnoreCase("main")){
                        Intent intent=new Intent(ReceiveMailActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    else if(mailNo==-2){
                        if(result.get(0).equalsIgnoreCase("read")){
                                //read.performClick();
                            mailNo=-1;
                            receiveMails.clear();
                            new MailReader("all");
                            setList();
                            speakOut();
                            Toast.makeText(ReceiveMailActivity.this, Integer.toString(receiveMails.size()), Toast.LENGTH_SHORT).show();
                        }
                        else if(result.get(0).equalsIgnoreCase("unread")){
                            //unread.performClick();
                            mailNo=-1;
                            receiveMails.clear();
                            new MailReader("unread");
                            setList();
                            speakOut();

                            Toast.makeText(ReceiveMailActivity.this, Integer.toString(receiveMails.size()), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            speakOut(1);
                        }
                    }

                    else if(mailNo==-1){
                       try{
                           mailNo=Integer.parseInt(result.get(0).trim());
                           Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT).show();
                           show.setText(result.get(0).trim());
                           speakOut();
                       }
                       catch (Throwable e){
                       }

                    }
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