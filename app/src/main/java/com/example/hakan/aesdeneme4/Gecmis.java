package com.example.hakan.aesdeneme4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Gecmis extends ActionBarActivity {

    private VeriTabani key;
    SifreAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis);

      key = new VeriTabani(this);

        final List<SifreDegerleri> alinandata=  key.sifreCek();
        try {
//    	   setListAdapter(new ArrayAdapter<KanDegerleri>(this,R.id.listView1 ,alinandata ));
            final ListView listView=(ListView)findViewById(R.id.listView1);
            adaptor = new SifreAdaptor(this, (ArrayList<SifreDegerleri>) alinandata);
            listView.setAdapter(adaptor);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gecmis, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
