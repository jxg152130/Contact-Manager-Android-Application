package com.example.saikrishna.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ViewEdit extends AppCompatActivity {
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile";   //storing the path of file in a string variable
    String value;
    public EditText firstname;
    public EditText lastname;
    public EditText phone;
    public EditText email;
    public String f1;
    public String f2;
    public String f3;
    public String f4;
    public String f5;
    public String f6;
    public String f7;
    int l;
    int x;
    File file = new File(path + "savedfile.txt");       //assigning the path specified file to a file variable
    File list = new File(path + "listfile.txt");        //assigning the path specified file to a file variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            f1  = extras.getString("line_no");         //getting the index of the contact sent as extra from the main activity


        }
        x = Integer.parseInt(f1);
        firstname = (EditText) findViewById(R.id.editText);
        lastname = (EditText) findViewById(R.id.editText2);         //assigning the editext fields to the variables
        phone = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);


        f2 = Loadline(file, x);     //getting the contact details of the selected contact


        int k = f2.length();

        int y= f2.indexOf("\t");
        f3 = f2.substring(0, y);


        f2 = f2.substring(y+1);

        y = f2.indexOf("\t");
        f4 = f2.substring(0, y);            // spliting the string based on the tab seperation into their respective fields

        f2 = f2.substring(y+1);
        y = f2.indexOf("\t");
        f5 = f2.substring(0, y);

        f2 = f2.substring(y+1);
        f6 = f2;
        //email.setText(f2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);   //creating a floating action button variable and assigning it to the button by id
        fab.setOnClickListener(new OnClickListener() {      //creating listener for floatig action button
            @Override
            public void onClick(View view) {
                // forming a string with all the modified contact details separated by tab

                String nline = firstname.getText() + "\t" + lastname.getText() + "\t" + phone.getText() + "\t" + email.getText();
                String nline1 = firstname.getText() + "\t" + phone.getText();
                String[] prevdata_full = Load(file);        // loading all the contacts into a string array
                String[] prevdata_list = Load(list);
                //Toast.makeText(getBaseContext(),prevdata_full[0],Toast.LENGTH_LONG).show();
                for (int i = 0; i < prevdata_full.length; i++) {
                    if (i == x) {
                        prevdata_full[i] = nline;       // modifying the specific contact line
                        prevdata_list[i] = nline1;

                    }
                }

                Arrays.sort(prevdata_full);
                Arrays.sort(prevdata_list);
                Save(file, prevdata_full);      //sorting the contacts and saving back to file
                Save(list, prevdata_list);


                Snackbar.make(view, "Contact Modified", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent l = new Intent(ViewEdit.this, MainActivity.class);       //going back to main activity after modifying
                startActivity(l);
            }
        });
        firstname.setText(f3);
        lastname.setText(f4);       // filling the fields with clicked contact details
        phone.setText(f5);
        email.setText(f6);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);  //creating a floating action button variable and assigning it to the button by id
        fab1.setOnClickListener(new OnClickListener() {             //creating listener for delete button
            @Override
            public void onClick(View v) {


                String [] predelete_full = Load(file);      //loading all the contacts to string array
                String [] predelete_list = Load(list);
                int n = predelete_full.length;
                n--;
                String [] aftdelete_full = new String[n];
                String [] aftdelete_list = new String[n];
                for(int o =0,l=1;o<(aftdelete_full.length);o++,l++){
                    if(o<x){
                    aftdelete_full[o] = predelete_full[o];
                    aftdelete_list[o] = predelete_list[o];}         //creating a new string array excluding the deleted contact
                    else{
                        aftdelete_full[o] = predelete_full[l];
                        aftdelete_list[o] = predelete_list[l];
                    }
                }
                Arrays.sort(aftdelete_full);        //sorting and saving the contacts after deletion
                Arrays.sort(aftdelete_list);
                Save(file, aftdelete_full);
                Save(list, aftdelete_list);
                Intent u = new Intent(ViewEdit.this,MainActivity.class);        //going back to main activity after deletion
                startActivity(u);
                Toast.makeText(getBaseContext(), "contact deleted",Toast.LENGTH_LONG).show();

            }
        }
        );
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);     //creating a floating action button variable and assigning it to the button by id
        fab2.setOnClickListener(new OnClickListener() {     //creating listener for reset button
            @Override
            public void onClick(View v) {

                firstname.setText("");
                lastname.setText("");       //clearing all the fileds
                phone.setText("");
                email.setText("");
                Snackbar.make(v,"fields cleared",Snackbar.LENGTH_LONG).show();
            }
        });
    }
    public  String Loadline(File file,int no)       //loading the specifc contact that is selected in the list view
    {

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);        //creating inputstream for the saved file
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);     //creating inputstream reader for inputstream
        BufferedReader br = new BufferedReader(isr);        //creaing buffered reader for the inputstreamreader

        String test;
        int len=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                len++;      // getiing the no of lines in the file
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[len];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;        //assigning each line to the string array
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        //no++;
        String out = array[no];     // assing out to the specific line which we need
        return out;                 // returning the slected contact details
    }
    public static String[] Load(File file)
    {

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);        //creating inputstream for the saved file
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);     //creating inputstream reader for inputstream
        BufferedReader br = new BufferedReader(isr);            //creaing buffered reader for the inputstreamreader

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;                       // getiing the no of lines in the file
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;            //assigning each line to the string array
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }
    public static void Save(File file, String[] data)
    {

        FileOutputStream fos = null;

        try
        {
            fos = new FileOutputStream(file,false);     // creating outputstream for the file without append mode
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
                        fos.write("\n".getBytes()); // writing to the file by getting bytes form the string and separating each contact by a new line
                    }
                }
                fos.write("\n".getBytes());
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
