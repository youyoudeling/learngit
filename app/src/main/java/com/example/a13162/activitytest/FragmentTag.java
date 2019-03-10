package com.example.a13162.activitytest;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTag extends Fragment {

    private String[] data={};
    private Button button;

    private ArrayAdapter<String> adapter;

    public FragmentTag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tag_layout, container, false);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Data.getTag());
        ListView listView=(ListView) view.findViewById(R.id.tag_list);
        listView.setAdapter(adapter);
        button=view.findViewById(R.id.tagaddbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();


            }
        });
        return view;

    }
    public void showInputDialog(){
        final EditText editText=new EditText(getActivity());
        AlertDialog.Builder inputDialog=new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("请输入要存取的id信息").setView(editText);
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Data.addTag(editText.getText().toString());
                Toast.makeText(getActivity(),editText.getText().toString(),Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();

            }
        }).show();
    }

    public void showInputDialog(String text){
        final EditText editText=new EditText(getActivity());
        AlertDialog.Builder inputDialog=new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("存取该id信息").setView(editText);
        editText.setText(text);
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Data.addTag(editText.getText().toString());
                Toast.makeText(getActivity(),editText.getText().toString(),Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();

            }
        }).show();
    }

}
