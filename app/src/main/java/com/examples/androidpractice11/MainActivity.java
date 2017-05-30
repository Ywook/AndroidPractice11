package com.examples.androidpractice11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    TextView tv;
    EditText et;

    int index = 0;
    int img_index = 0;

    String[] name = {"치킨","햄버거", "고기", "피자"};
    int[] imgs = {R.drawable.chicken, R.drawable.hamburger, R.drawable.meat, R.drawable.pizza};

    Boolean FIRST = true;

    ChangeAsync task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("오늘 뭐먹지 ?");
        init();

    }

    public void init(){
        tv = (TextView)findViewById(R.id.textView);
        et = (EditText)findViewById(R.id.et);
        img = (ImageView)findViewById(R.id.imageView);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FIRST){
                    String temp = et.getText().toString();
                    if(!isNumber(temp)){
                        Toast.makeText(getApplicationContext(),"숫자를 입력해주세요." ,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FIRST = false;
                    task = new ChangeAsync();
                    task.execute(Integer.parseInt(et.getText().toString()));
                }else{
                    tv.setText(name[img_index-1] + "선택("+(index+1)+"초)");
                    task.cancel(true);
                }
            }
        });
    }
    public void onClick(View v){
        if(!task.isCancelled()){
            task.cancel(true);
        }
        FIRST = true;
        img_index = 0;
        index = 0;
        img.setImageResource(R.drawable.playbutton);
        et.setText("");
        tv.setText("");
    }

    public boolean isNumber(String s){
        if(s.equals("")){
            return false;
        }
        for(int i = 0; i < s.length(); i++){
            char check = s.charAt(i);
            if(check < '0' || check > '9'){
                return false;
            }
        }
        return true;
    }

    class ChangeAsync extends AsyncTask<Integer, Integer, Void>{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText("시작부터 "+(values[0]+1)+"");
            if(values[0]%values[1]==0){
                if(img_index >= imgs.length) img_index =0;
                img.setImageResource(imgs[img_index]);
                img_index++;
            }
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            while (true){
                try {
                    if(isCancelled()==true) break;
                    publishProgress(index, integers[0]);
                    Thread.sleep(1000);
                    index++;;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            return null;
        }
    }
}
