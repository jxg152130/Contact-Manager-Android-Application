/*------------------------------------------------------------------------------------------------------------------------------------

                             AUTHORED BY : JAYA SAI KRISHNA GANDHAM
                             NET-ID : JXG152130
                             SECTION :005


---------------------------------------------------------------------------------------------------------------------------------------*/



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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile";//storing the path of file in a string variable



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        File file = new File(path + "savedfile.txt");   //assigning the path specified file to a file variable
        File list = new File(path + "listfile.txt");    //assigning the path specified file to a file variable

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);  //creating a floating action button variable and assigning it to the button by id
        fab.setOnClickListener(new View.OnClickListener() {     //creating listener for floatig action button
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, addnew.class); //creating a new intent and giving parameters to go to other activity
                startActivity(i);
            }
        });

        String [] pre_carss = Load(file);
        Arrays.sort(pre_carss);
        Save(file, pre_carss);
        String [] pre_list = Load(list);            // reading sorting and storing back to files
        Arrays.sort(pre_list);
        Save(list,pre_list);


        String[] carss = Load(list);






        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,carss);   // creating arrayadapter with the input of details from the saved file
        ListView listView = (ListView) findViewById(R.id.listView);         // assigning the list view to listview variable by id
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {     //creating listener for all the list view items
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String item = Integer.toString(position);



                Intent j = new Intent(MainActivity.this, ViewEdit.class);   //creating intent for going to viewedit activity
                j.putExtra("line_no",item);         // sending the postion of the item clicked in the list view
                startActivity(j);


            }
        });}





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        BufferedReader br = new BufferedReader(isr);        //creaing buffered reader for the inputstreamreader

        String test;
        int len=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                len++;              // getiing the no of lines in the file
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
                array[i] = line;                //assigning each line to the string array
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
            fos = new FileOutputStream(file,false);     // creating outputstream for the file
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
                        fos.write("\n".getBytes());         // writing to the file by getting bytes form the string
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
                fos.close();       //closing file
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }


}
