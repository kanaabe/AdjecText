package edu.rutgers.cs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class FavoriteActivity extends Activity {
	AssetManager am;
	String[] list;
	ArrayList<String> texts;
	ListView listView;  // list of route names

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.create);
		am = getAssets();
		
		
		
		list = fileList();
		System.out.println(list.length);
		System.out.println(list[0]);
		for(int i = 0; i < list.length; i++){
			try {
				FileInputStream stream = openFileInput(list[i]);
				stream.read();
				StringBuffer str = null;
				int ch;
				//append the char
				while( (ch = stream.read()) != -1){
					str.append((char)ch);
				}
				
				String string = str.toString();


				texts.add(string);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		// set up the list view to hold route names
		listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.favorites, texts));

	}

}

