package edu.rutgers.cs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdjecTextActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    }
    
    public void newtext(View view){
    	
    	 Intent i = new Intent(AdjecTextActivity.this, CreateActivity.class);
         startActivity(i);
        
    	
    }
    public void viewfavorites(View view){
    	

    	Intent i = new Intent(AdjecTextActivity.this, FavoriteActivity.class);
        startActivity(i);
       
    }
}