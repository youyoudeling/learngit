package com.example.a13162.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TagPageActivity extends BaseNfcActivity implements View.OnClickListener {

    private String myText;
    private Tag detectedTag;
    private int position;
    private EditText ptitle;
    private EditText ptext;
    private Button tagdele;
    private Button tagsave;
    private Button tagwrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_page_layout);
        Intent intent = getIntent();
        position=intent.getIntExtra("extra_data",0);
        TagClass tag=(TagClass) Data.getTagList().get( position);
        ptitle=(EditText) findViewById(R.id.pagetitle);
        ptext= (EditText) findViewById(R.id.pagetext);
        ptitle.setText(((TagClass) Data.getTagList().get(position)).getTitle());
        myText=((TagClass) Data.getTagList().get(position)).getText();
        //ptext.setText(((TagClass) Data.getTagList().get(position)).getText());
        ptext.setText(myText);
        tagdele=(Button)findViewById(R.id.tagdelete);
        tagsave=(Button)findViewById(R.id.tagsave);
        tagwrite=(Button)findViewById(R.id.tagwrite);

        tagdele.setOnClickListener(this);
        tagsave.setOnClickListener(this);
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
                    Data.getTagList().remove(position);
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    int i=pref.getInt("number",0);
                    SharedPreferences.Editor editor=pref.edit();
                    int mypos=position+1;
                    editor.remove("title"+mypos);
                    editor.remove("content"+mypos);
                    for(int j=mypos;j<i;j++){
                        int k=j+1;
                        editor.putString("title"+j,pref.getString("title"+k,""));
                        editor.putString("content"+j,pref.getString("content"+k,""));
                    }
                    i--;
                    editor.putInt("number",i);
                    editor.commit();
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                    tagdele.setEnabled(false);
                    tagsave.setEnabled(false);
                    tagwrite.setEnabled(false);

                } catch (Exception e) {
                }
                break;
            case R.id.tagwrite:
                writeNFCTag(detectedTag);
                break;
            case R.id.tagsave:
                try {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    int mypos=position+1;
                    Log.d("abcd",mypos+"");
                    TagClass item=new TagClass(ptitle.getText().toString(),ptext.getText().toString());
                    Data.getTagList().set(position,item);
                    editor.putString("title"+mypos,ptitle.getText().toString());
                    editor.putString("content"+mypos,ptext.getText().toString());
                    editor.commit();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }
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
            Toast.makeText(this, "请先将标签贴住手机，待震动后点击写入", Toast.LENGTH_SHORT).show();
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
