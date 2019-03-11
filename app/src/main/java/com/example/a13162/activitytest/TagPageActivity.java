package com.example.a13162.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TagPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_page_layout);
        Intent intent = getIntent();
        int position=intent.getIntExtra("extra_data",0);
        TagClass tag=(TagClass) Data.getTagList().get( position);
        TextView ptitle=(TextView) findViewById(R.id.pagetitle);
        TextView ptext= (TextView) findViewById(R.id.pagetext);
        ptitle.setText(((TagClass) Data.getTagList().get(position)).getTitle());
        ptext.setText(((TagClass) Data.getTagList().get(position)).getText());



    }
}
