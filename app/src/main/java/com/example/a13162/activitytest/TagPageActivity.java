package com.example.a13162.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TagPageActivity extends BaseNfcActivity implements View.OnClickListener {

    private String myText;
    private Tag detectedTag;
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
        myText=((TagClass) Data.getTagList().get(position)).getText();
        //ptext.setText(((TagClass) Data.getTagList().get(position)).getText());
        ptext.setText(myText);
        Button tagdele=(Button)findViewById(R.id.tagdelete);
        Button tagwrite=(Button)findViewById(R.id.tagwrite);

        tagdele.setOnClickListener(this);
        tagwrite.setOnClickListener(this);



    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(myText==null){
            return;
        }
        //获取tag
        detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tagdelete:
                try {
                    Ndef ndef = Ndef.get(detectedTag);
                    ndef.connect();
                    NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{NdefRecord
                            .createTextRecord(null,"null")});
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                    ndef.writeNdefMessage(ndefMessage);
                } catch (Exception e) {
                }
                break;
            case R.id.tagwrite:
                writeNFCTag(detectedTag);
                break;
            default:
                break;
        }
    }

    /**
     * 往标签写数据的方法
     *
     * @param tag
     */
    public void writeNFCTag(Tag tag) {
        if (tag == null) {
            return;
        }
//        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{NdefRecord
//                .createUri(Uri.parse(myText))});
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{NdefRecord
                .createTextRecord(null,myText)});
        //转换成字节获得大小
        int size = ndefMessage.toByteArray().length;
        try {
            //2.判断NFC标签的数据类型（通过Ndef.get方法）
            Ndef ndef = Ndef.get(tag);
            //判断是否为NDEF标签
            if (ndef != null) {
                ndef.connect();
                //判断是否支持可写
                if (!ndef.isWritable()) {
                    return;
                }
                //判断标签的容量是否够用
                if (ndef.getMaxSize() < size) {
                    return;
                }
                //3.写入数据
                ndef.writeNdefMessage(ndefMessage);
                Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
            } else { //当我们买回来的NFC标签是没有格式化的，或者没有分区的执行此步
                //Ndef格式类
                NdefFormatable format = NdefFormatable.get(tag);
                //判断是否获得了NdefFormatable对象，有一些标签是只读的或者不允许格式化的
                if (format != null) {
                    //连接
                    format.connect();
                    //格式化并将信息写入标签
                    format.format(ndefMessage);
                    Toast.makeText(this, "写入成功",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
        }
    }
}
