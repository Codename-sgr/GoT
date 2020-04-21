package com.sagar.guessthecelebrity;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity2 extends Activity implements View.OnKeyListener {

    EditText name;

    public void gotoloading(){
        Intent intent=new Intent(getApplicationContext(),loading.class);
        startActivity(intent);
    }

    public void toMainActivity2(View view){

        if(name.getText().toString().matches("")){
            Toast.makeText(this, "UserName is Required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            gotoloading();

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_activity);

        name=findViewById(R.id.editText);
        name.setOnKeyListener(this);



    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
         if(keyCode==KeyEvent.KEYCODE_ENTER){
            gotoloading();
         }
         return false;
    }
}
