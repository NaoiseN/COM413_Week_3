package com.example.com413week3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String webMessage = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView hello = (TextView) findViewById(R.id.text_hello);

        Button search = (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchFor = ((EditText) findViewById(R.id.edit_search)).getText().toString();
                Intent i = new Intent(MainActivity.this, WebSearch.class);
                i.putExtra(webMessage, searchFor);
                startActivity(i);
            }
        });

        Button text = (Button) findViewById(R.id.btn_text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, txtMessage.class);
                startActivity(i);
            }
        });
    }

    public void dialPhone(View view){
        String number = "123456789";
        if(checkPermission("android.permission.CALL_PHONE")==false) {
            Toast toast = Toast.makeText(this, "No Permissions to Call", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel: " + number));
            startActivity(intent);
        }
    }

    private boolean checkPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        return(permissionCheck == getPackageManager().PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    send.setEnabled(true);
                }
                return;
            }
        }
    }

    public void send(View view) {
        String phoneNumber = ((EditText)findViewById(R.id.editPhone)).getText().toString();
        String msg = ((EditText) findViewById(R.id.editMessage)).getText().toString();

        if (phoneNumber == null || phoneNumber.length()==0|| msg==null || msg.length()==0){
            return;
        }
        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            SmsManager.sendTextMessage(phoneNumber, null, msg, null, null);
            Toast.makeText(this, "Your message has been sent", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }
}