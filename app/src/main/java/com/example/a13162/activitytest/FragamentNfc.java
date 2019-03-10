package com.example.a13162.activitytest;


import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragamentNfc extends Fragment {

    private ImageView imageView;
    private Button button;
    public FragamentNfc() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_nfc_layout, container, false);
        imageView= view.findViewById(R.id.nfc_view);
        button= view.findViewById(R.id.nfcbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (Data.getIsactive()){
                    case 1:
                        imageView.setImageResource(R.drawable.nfc);
                        Toast.makeText(getActivity(),"开发者模式已开启",Toast.LENGTH_SHORT).show();
                        Data.change();
                        break;
                    case 0:
                        imageView.setImageResource(R.drawable.nfcpage);
                        Toast.makeText(getActivity(),"应用者模式已开启",Toast.LENGTH_SHORT).show();
                        Data.change();

                        break;
                    default:
                        break;
                }
            }
        });


        return view;
    }


}
