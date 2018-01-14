package com.example.mona.mahen.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.User;
import com.example.mona.mahen.R;
import com.firebase.client.Firebase;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Worker_Propic extends AppCompatActivity {
    private final int SELECT_PHOTO = 1;
    String name, un, email, pass, type, b_day, sex, ssn, pp,acttp,sel,ppid,address,country,contact;
    User user;
    @Bind(R.id.pic)
    CircleImageView pic;
    @Bind(R.id.idpic)
    CircleImageView idpic;
    @Bind(R.id.Upload)
    Button Upload;
    @Bind(R.id.reg)
    Button IDUpload;
    @Bind(R.id.IDUpload)
    Button reg;

    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker__propic);
        Firebase.setAndroidContext(this);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("full_name_user");
        un = bundle.getString("user_name_user");
        email = bundle.getString("email_user");
        pass = bundle.getString("pass_user");
        type = bundle.getString("type_user");
        b_day = bundle.getString("bday_user");
        sex = bundle.getString("sex_user");
        ssn = bundle.getString("ssn_user");
        acttp=bundle.getString("acttype_user");
        address=bundle.getString("address");
        country=bundle.getString("country");
        contact=bundle.getString("contact");
        pp="anfjkwjkfnejrfnkjernfjkernfkjenfkjef";
        ppid="dajkdnajkdnajkdnajkdnjaknsdjkandjkan";
        firebase = new Firebase(Config.FIREBASE_URL);
        pic.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
        idpic.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));

    }


    protected void setUpUser() {
        user = new User();
        user.setFull_name(name);
        user.setUn(un);
        user.setEmail(email);
        user.setPass(pass);
        user.setType(acttp);
        user.setBirth_date(b_day);
        user.setSex(sex);
        user.setID(ssn);
        user.setIdpic(ppid);
        user.setPropic(pp);
        user.setAddress(address);
        user.setContact(contact);
        user.setNationality(country);
        user.saveUser(firebase,type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, bos);


                        if (selectedImage.getByteCount() < 1500000) {
                            if(sel.equalsIgnoreCase("1"))
                            {
                                byte[] img = bos.toByteArray();

                                String encodedImage = Base64.encodeToString(img, Base64.DEFAULT);
                                pp = encodedImage;

                                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                pic.setImageBitmap(decodedByte);


                            }
                            else
                            {
                                byte[] img = bos.toByteArray();

                                String encodedImage = Base64.encodeToString(img, Base64.DEFAULT);
                                ppid = encodedImage;

                                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                idpic.setImageBitmap(decodedByte);

                            }

                        } else

                            Toast.makeText(Worker_Propic.this,
                                    "Please select a profile picture less than 3500000 byte" , Toast.LENGTH_LONG).show();


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }


        }
    }

    @OnClick({R.id.Upload, R.id.reg,R.id.IDUpload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Upload:

                sel="1";
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                break;


            case R.id.IDUpload:
                sel="2";
                Intent photoPickerIntent2 = new Intent(Intent.ACTION_PICK);
                photoPickerIntent2.setType("image/*");
                startActivityForResult(photoPickerIntent2, SELECT_PHOTO);

                break;
            case R.id.reg:
                setUpUser();
                Intent in=new Intent(Worker_Propic.this,Front.class);
                startActivity(in);
                Toast.makeText(Worker_Propic.this,
                        "Your request is placed under admin observation. You'll be mailed when you are approved" , Toast.LENGTH_LONG).show();

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}

