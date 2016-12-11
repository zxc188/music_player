package com.example.administrator.media_player;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button btstart;
    private Button btstop;
    private Button btnew;
    private MediaPlayer media;
    private Uri uri;
    private SeekBar seek;
    private int change;
    private Timer time;
    private TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btstart=(Button)findViewById(R.id.start);
        btnew=(Button) findViewById(R.id.newmusic);
        btstop = (Button) findViewById(R.id.stop);
        seek = (SeekBar) findViewById(R.id.seekBar);
        change=1;
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
              change=1;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media.seekTo(seek.getProgress());
               change=0;
            }
        });

        media=new MediaPlayer();
        btstart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               uri = Uri.parse("http://tj-ctfs.ftn.qq.com/ftn_handler/909b94bb707380b403b8f2649e159451b5cad7ab604266296eae1c8b748a906230ccc7d75ed0082040119c377e96b5762ed500d7a2566b96ffd6b2d222a9256a/?fname=youlanxiang.mp3&k=77333961a306f79a962a3b374363074a060a0859065306061e575a59014e015703511459565a014807035b000407535d02075804656c351c5c4655000b1b5c045d54170c15503558&fr=00&&txf_fid=4159853e3adac0e8bfb5341085832c2067a8461d&xffz=4037178/youlanxiang.mp3");
               try {
                   media.setDataSource(MainActivity.this, uri);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               try {
                   media.prepare();
               } catch (IOException e) {
                   e.printStackTrace();
               }
          if(!media.isPlaying()){
              media.start();
              btstart.setText("暂停音乐");
              seek.setMax(media.getDuration());
          }
               else{
              media.pause();
              btstart.setText("播放播放");
          }
               time = new Timer();
               task=new TimerTask() {
                   @Override
                   public void run() {
                       if(change==1){
                           return;
                       }
                       seek.setProgress(media.getCurrentPosition());
                   }
               };
              time.schedule(task,100,100);
           }
       });
        btstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(media.isPlaying()){
                    media.stop();
                }
                else{
                    Toast.makeText(MainActivity.this,"音乐没有播放",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.reset();
                uri = Uri.parse("http://tj-ctfs.ftn.qq.com/ftn_handler/602463f5211306a026ca5151dd85304caa93c2283c5754a1101865ef3a7d31dc32c2f7709a417a34e21967b5b5ca38d7ca4824f1aa6bc06bdf2e8a761bfd03f4/?fname=niulangzhinu.mp3&k=773130381819e7cd9728326e4766051d0654050c505702571f50000c034b030502001d000203531f5107095a5553020b0a53010a6176375c5b445c590f014d5a5b5f45160c1604320f&fr=00&&txf_fid=c5ba728e22399e94cf2ae9044a0c8e8f2f77aa05&xffz=3113095/niulangzhinu.mp3");
                try {
                    media.setDataSource(MainActivity.this,uri);
                    media.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                media.start();
            }
        });
    }
}
