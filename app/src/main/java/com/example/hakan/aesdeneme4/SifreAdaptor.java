package com.example.hakan.aesdeneme4;

/**
 * Created by hakan on 24.04.2015.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SifreAdaptor extends BaseAdapter
{
    TextView ad;

    private LayoutInflater flater;
    private ArrayList<SifreDegerleri> sifreListesi;



    public SifreAdaptor(Activity activity, ArrayList<SifreDegerleri> sifreler) {

        flater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        sifreListesi = sifreler;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return sifreListesi.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return sifreListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View satir;
        satir=flater.inflate(R.layout.satirlar,null);
        ad=(TextView) satir.findViewById(R.id.textView3);


        SifreDegerleri sifre=sifreListesi.get(position);

        ad.setText(sifre.getSifre());

//		forward.setImageResource(R.drawable.ic_action_forward);


        return satir;



    }

}
