package com.sagar.guessthecelebrity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {



    ArrayList<String> characterURL=new ArrayList<String>();

    ArrayList<String> characterName=new ArrayList<String>();


    ImageView imageView;
    androidx.gridlayout.widget.GridLayout gridLayout;
    int chosenChar;
    int correctAnsLoc;
    int incorrectAnsLoc;
    String[] ans=new String[4];
    ArrayList<Integer> quesDone=new ArrayList<>(characterName.size());

    Button button;
    Button button1;
    Button button2;
    Button button3;

    public void newQues(){

        button.setBackground(getDrawable(R.drawable.buttonshape));
        button1.setBackground(getDrawable(R.drawable.buttonshape));
        button2.setBackground(getDrawable(R.drawable.buttonshape));
        button3.setBackground(getDrawable(R.drawable.buttonshape));


        Random random=new Random();
        chosenChar=random.nextInt(84);
       // System.out.println(chosenChar);

        ImageDownloader imgTask=new ImageDownloader();

        if(!quesDone.contains(chosenChar)) {

            quesDone.add(chosenChar);

            try {

                Bitmap charImg;

                charImg = imgTask.execute("https://www.hbo.com" + characterURL.get(chosenChar)).get();
                imageView.setImageBitmap(charImg);
                correctAnsLoc = random.nextInt(4);
                for (int i = 0; i < 4; i++) {
                    if (i == correctAnsLoc)
                        ans[i] = characterName.get(chosenChar);
                    else {
                        incorrectAnsLoc = random.nextInt(characterName.size());
                        while (incorrectAnsLoc == chosenChar)
                            incorrectAnsLoc = random.nextInt(characterName.size());
                        ans[i] = characterName.get(incorrectAnsLoc);
                    }

                }

                button.setText(ans[0]);
                button1.setText(ans[1]);
                button2.setText(ans[2]);
                button3.setText(ans[3]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            if(quesDone.size()!=83)
            newQues();
            else {
                gridLayout.setEnabled(false);

            }

        }


        //System.out.println(quesDone.toString());
    }

    public void chosenButton(View view){
        if(view.getTag().toString().equals(Integer.toString(correctAnsLoc)))
            view.setBackground(getDrawable(R.drawable.rightans));
            // Toast.makeText(getApplicationContext(), "Correct!!", Toast.LENGTH_SHORT).show();
        else{
            view.setBackground(getDrawable(R.drawable.wrongans));

            Toast.makeText(getApplicationContext(),"Wrong!!\n Character is: "+characterName.get(chosenChar), Toast.LENGTH_SHORT).show();
        }


        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newQues();
            }
        },500);




    }

    public class ImageDownloader extends AsyncTask<String,String, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream in=connection.getInputStream();
                return BitmapFactory.decodeStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public class DownloadTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout=findViewById(R.id.gridLayout);
        imageView=findViewById(R.id.imageView);
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);

        DownloadTask task=new DownloadTask();

        String result;
        try {
            result=task.execute("https://codename-sgr.github.io/testGit2/index.html").get();
            //String[] splitResult=result.split("Get Started!");

            Pattern p;
            p=Pattern.compile("130w, (.*?) 260w");          //FOR URL
            Matcher m;
            m=p.matcher(result);

            while(m.find()){
                //System.out.println(m.group(1));
                characterURL.add(m.group(1));

            }
            p=Pattern.compile("primaryText\">(.*?)</s");            //FOR NAME
            m=p.matcher(result);

            while(m.find()){
                //System.out.println(m.group(1));
                characterName.add(m.group(1));
            }

            //System.out.println(charName.size());
            //System.out.println(charURL.size());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }




        newQues();

    }
}
