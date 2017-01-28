package com.example.saikrishna.contactmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class addnew extends AppCompatActivity {
    public EditText firstname;
    public EditText lastname;
    public EditText phone;
    public EditText email;
    public String f1;
    public String f2;
    public String f3;
    public String f4;
    public String [] details ={};

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile";   //storing the path of file in a string variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstname = (EditText) findViewById(R.id.editText);
        lastname = (EditText) findViewById(R.id.editText2);         //assigning the edit text fields to the variables
        phone = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);
        f1= firstname.toString();
        f2 = lastname.toString();
        f3 = phone.toString();
        f4 = email.toString();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);      //creating a floating action button variable and assigning it to the button by id
        fab.setOnClickListener(new View.OnClickListener() {     //creating listener for floatig action button
            @Override
            public void onClick(View view) {


                File file = new File(path + "savedfile.txt");       //assigning the path specified file to a file variable
                File list = new File(path + "listfile.txt");        //assigning the path specified file to a file variable

                //saving all the fileds entered to the string array
                String [] savetext = {String.valueOf(firstname.getText()),String.valueOf(lastname.getText()), String.valueOf(phone.getText()),String.valueOf(email.getText())};
                //saving names and phone no to the string array
                String [] listtext = {String.valueOf(firstname.getText()),String.valueOf(lastname.getText()),String.valueOf(phone.getText())};



                Save(file, savetext);      //saving the contact to the file
                Save(list,listtext);


                Snackbar.make(view, "Your Contact is saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();   // showing that the contact is saved
                Intent k = new Intent(addnew.this,MainActivity.class);      // going back to main activity after saving
                startActivity(k);





            }
        });

    }
    public static void Save(File file, String[] data)
    {




        FileOutputStream fos = null;

        try
        {
            fos = new FileOutputStream(file,true);         //opening file for writing with append mode
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {

                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\t".getBytes());           // writing to file by getting each bit from the string array and each field separated by tab
                    }
                }
                fos.write("\n".getBytes());                 // going to new line after writing each contact
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }





}

