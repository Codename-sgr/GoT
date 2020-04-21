package com.sagar.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pl.droidsonroids.gif.GifImageView;

public class loading extends AppCompatActivity {

    GifImageView gifImageView;

    Thread timer=new Thread(){
        public void run(){
            try{
                sleep(4000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            finally {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        gifImageView=findViewById(R.id.gif);
        timer.start();

    }
}
