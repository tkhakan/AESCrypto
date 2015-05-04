package com.example.hakan.aesdeneme4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import android.util.Log;
import android.util.Base64;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private  VeriTabani nesne;

    String filename="SECRET KEY";
    byte[] sksbyte;
    String keystring;
  //  byte[] key;
    String encoded;
    byte[] encryptedBytes = null;
    byte[] decryptedBytes = null;
    static final String TAG = "SymmetricAlgorithmAES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nesne=new VeriTabani(this);


        final Button enButton = (Button) findViewById(R.id.enbutton);
        final Button deButton = (Button) findViewById(R.id.debutton);
        final EditText input = (EditText) findViewById(R.id.Input);
        final EditText Raw = (EditText) findViewById(R.id.raw);
        final EditText output = (EditText) findViewById(R.id.originText);
        final EditText output2 = (EditText) findViewById(R.id.originText2);
        final Button anahtar = (Button) findViewById(R.id.key);




        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
             sksbyte=sks.getEncoded();
            keystring=Base64.encodeToString(sksbyte, Base64.DEFAULT);

        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }

         //  byte[] keyStart = "this is a key".getBytes();
           // KeyGenerator kgen = KeyGenerator.getInstance("AES");
           // SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
           // sr.setSeed(keyStart);
           // kgen.init(256, sr); // 192 and 256 bits may not be available
           // SecretKey skey = kgen.generateKey();
           // key = skey.getEncoded();
           //  keystring=Base64.encodeToString(key, Base64.DEFAULT);


        // Veritabanina kayit

        nesne.SifreEkle(keystring);
        Toast.makeText(getBaseContext(),  "SIFRE VERI TABANINA EKLENDI", Toast.LENGTH_LONG).show();




        String data=new String(keystring);
        FileOutputStream fos;
        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();

            Toast.makeText(getApplicationContext(), filename + " saved", Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        final SecretKeySpec finalSks2 = sks;
        anahtar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer stringBuffer = new StringBuffer();
                try {
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                            openFileInput(filename)));
                    String inputString;

                    while ((inputString = inputReader.readLine()) != null) {
                        stringBuffer.append(inputString + "\n");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
              // Toast.makeText(getApplicationContext(),stringBuffer.toString(),Toast.LENGTH_LONG).show();

               output2.setText(stringBuffer.toString());

               // output2.setText(new String(keystring));


            }
        });


        final SecretKeySpec finalSks = sks;
        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cipher c = Cipher.getInstance("AES");
                    c.init(Cipher.ENCRYPT_MODE, finalSks);
                    encryptedBytes = c.doFinal(input.getText().toString().getBytes());
                } catch (Exception e) {
                    Log.e(TAG, "AES encryption error");
                }
                 encoded=Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
                Raw.setText(encoded);
            }
        });

        final SecretKeySpec finalSks1 = sks;
        deButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] decoded=Base64.decode(encoded,Base64.DEFAULT);
                    Cipher c = Cipher.getInstance("AES");
                    c.init(Cipher.DECRYPT_MODE, finalSks1);
                    decryptedBytes = c.doFinal(decoded);
                } catch (Exception e) {
                    Log.e(TAG, "AES decryption error");
                }
                output.setText(new String(decryptedBytes));
            }
        });



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           // return true;
            Intent intent1=new Intent(MainActivity.this,Gecmis.class);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }
        }