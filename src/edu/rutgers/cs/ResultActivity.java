package edu.rutgers.cs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity{
	
	
	public String sentence;
	public ArrayList<Token> arraylist;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Bundle extras = getIntent().getExtras();
        sentence = extras.getString("sentence");
        TextView newtext = (TextView) findViewById(R.id.newtext);
    	newtext.setText(sentence);
    	arraylist = CreateActivity.s;
    }
	
	public void save(View view) throws IOException{
		
		String FILENAME = sentence;
		String string = sentence;
		
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		fos.write(string.getBytes());
		fos.close();
		
		Context context = getApplicationContext();
        CharSequence toastmessage = "Saved!";
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, toastmessage, duration);
    	toast.show();
	}
	
	public void adjectext2(View view){
		finish();
		setContentView(R.layout.create);
	}
	
	//goes back to main screen
	public void home(View view){
		
		Intent i = new Intent(ResultActivity.this, AdjecTextActivity.class);
        startActivity(i);
       
	}
}
