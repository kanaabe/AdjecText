package edu.rutgers.cs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.rutgers.cs.Adjective;
import edu.rutgers.cs.Adverb;
import edu.rutgers.cs.Article;
import edu.rutgers.cs.Ender;
import edu.rutgers.cs.Other;
import edu.rutgers.cs.Starter;
import edu.rutgers.cs.Token;
import edu.rutgers.cs.Verb;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class CreateActivity extends Activity{
	

	public RadioGroup group;
	public RadioButton emotionbutton;
	public String message;
	public String emoe; 
	public Button button;
	public String result;
	public static InputStream in;
	public static BufferedReader reader;
	static String tempResult;
	boolean question = false;
	boolean shortSentence = false;
	public static ArrayList<Token> s = new ArrayList<Token>();
	int size;
	AssetManager am;
	
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        am = getAssets();
        
        
    }
	
	public void create(View view){
		
		//pull text and radio option
		TextView text = (TextView) findViewById(R.id.input);
		if(text == null){
			Context context = getApplicationContext();
	        CharSequence toastmessage = "Please enter message.";
	    	int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(context, toastmessage, duration);
	    	toast.show();
	        return;
		}
		message = text.getText().toString();
        group = (RadioGroup) findViewById(R.id.group1);
    	int selected = group.getCheckedRadioButtonId();
    	emotionbutton = (RadioButton) findViewById(selected);
        emoe = (String) emotionbutton.getText();
        emoe = emoe.toLowerCase();
        
        
    	try {
			adjectext(message, emoe);
			
	    	
	    	 
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent i = new Intent(CreateActivity.this, ResultActivity.class);
        i.putExtra("sentence", toString());
		startActivity(i);
       
		
		
	}
	
	public ArrayList<Token> adjectext(String input, String emote) throws IOException{
		
		
		makeSentence(input);
        System.out.println("Sentence is: " + s.toString());
		expand();
		System.out.println("New sentence is: " + s.toString());
		return s;
		
	}
	
	
	
	public void expand() throws IOException{
		for ( int i = 0; i < this.s.size(); i++){
				Token currTk = s.get(i);
				if(s.get(i) instanceof Adjective  ||s.get(i) instanceof Verb){
					Adverb adv = this.findAdv();
					currTk.before = adv;				
				}else if(s.get(i) instanceof Article){
					Adjective adj = this.findAdj();
					currTk.after = adj;
				}else if(s.get(i) instanceof Adverb){
					if(s.get(i+1).val.equals("and")){
						continue;
					}else{
						Other and  = new Other("and");
						currTk.after = and;	
					}
				}else{
					continue;
				}
				
			}
		
			int index = 0; 
			while(index < s.size()){
				//System.out.println(s.get(index).type);
				Token currTk = s.get(index);			
				if(currTk.after != null){
					s.add(index+1, currTk.after);
					currTk.after = null;
				}
				
				if(currTk.before != null){
					s.add(index, currTk.before);
					currTk.before = null;
				}
				
				index++;
			}
			
			if(!(s.get(0) instanceof Starter)){
				Starter starter = findStarter();
				s.add(0, starter);
				System.out.println("STARTER: " + starter.val);
			}else if(s.get(0) instanceof Starter && s.get(s.size()-1) instanceof Ender){
				Starter starter = findStarter();
				s.add(0, starter);
				Ender ender = findEnder();
				s.add(ender);
			}else{
				Ender ender = findEnder();
				s.add(ender);
			}
		}
	

	public InputStream fileReader(int file){
		InputStream in = this.getResources().openRawResource(file);
		return in;
	}
	

	public Ender findEnder() {
		String filename = this.emoe+"/enders";
		try {
			InputStream in = am.open(filename);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
			System.out.println("Reader Ready: " + reader.ready() + "  " + reader.readLine());
			int numWords = this.countWords(filename);
			int random = 1 + (int)(Math.random() * ((numWords - 1) + 1));
			System.out.println("Random number: " + random);
			int count = 1;
			try {
				String line = reader.readLine();
				System.out.println(line);
				for(int i = 0; i < numWords; i++){
					if(count == random){
						System.out.println("COUNT EQUALS RANDOM");
						Ender adj = new Ender(line);
						return adj;
					}else{
						line = reader.readLine();
						count++;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	public Adjective findAdj() {

		String filename = this.emoe+"/adjectives";
		try {
			InputStream in = am.open(filename);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
			System.out.println("Reader Ready: " + reader.ready() + "  " + reader.readLine());
			int numWords = this.countWords(filename);
			int random = 1 + (int)(Math.random() * ((numWords - 1) + 1));
			System.out.println("Random number: " + random);
			int count = 1;
			try {
				String line = reader.readLine();
				System.out.println(line);
				for(int i = 0; i < numWords; i++){
					if(count == random){
						System.out.println("COUNT EQUALS RANDOM");
						Adjective adj = new Adjective(line);
						return adj;
					}else{
						line = reader.readLine();
						count++;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public Adverb findAdv() throws IOException {

		String filename = this.emoe+"/adverbs";
		try {
			InputStream in = am.open(filename);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in)); 
			System.out.println("Reader Ready: " + reader.ready() + "  " + reader.readLine());
			int numWords = this.countWords(filename);
			int random = 1 + (int)(Math.random() * ((numWords - 1) + 1));
			System.out.println("Random number: " + random);
			int count = 1;
			try {
				String line = reader.readLine();
				System.out.println(line);
				for(int i = 0; i < numWords; i++){
					if(count == random){
						Adverb adv = new Adverb(line);
						return adv;
					}else{
						line = reader.readLine();
						count++;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public Starter findStarter() throws IOException {
		try {

			String filename = this.emoe+"/starters";
			InputStream in = am.open(filename);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
			System.out.println("Reader Ready: " + reader.ready() + "  " + reader.readLine());
			int numWords = this.countWords(filename);
			int random = 1 + (int)(Math.random() * ((numWords - 1) + 1));
			System.out.println("Random number: " + random);
			int count = 1;
			try {
				String line = reader.readLine();
				System.out.println(line);
				for(int i = 0; i < numWords; i++){
					if(count == random){
						Starter start = new Starter(line);
						return start;
					}else{
						line = reader.readLine();
						count++;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public void isShort(){
		if(size - numAdders() < 3)
			shortSentence = true;
	}
	
	public void isQuestion(){
		if(firstToken() instanceof Interrogative){
			question = true;
		}else{
			question = false;
		}		
	}
	
	public int countWords(String filename) throws IOException {
	    
		
		InputStream is = am.open(filename);
		
		try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        while ((readChars = is.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	        }
	        System.out.println(count);
	        return count;
	    } finally {
	        is.close();
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	//A Sentence is made of multiple TOKENS
	public void makeSentence(String text){
		System.out.println(text);
		int count = 0;
		StringTokenizer st = new StringTokenizer(text, " !.?", true); 
		while(st.hasMoreTokens()) { 
			String tk = st.nextToken();
			
			if(isArticle(tk)){
				System.out.println("Is Article");
				Article a = new Article(tk);
				s.add(a);
			}else if(tk.equals(" ")){
				continue;
			}
			else if(tk.equals("?") || tk.equals("!") || tk.equals(".")){
				Punct p = new Punct(tk);
				s.add(p);
			}
			else if(isInter(tk)){
				Interrogative i = new Interrogative(tk);
				s.add(i);
			}else if(isVerb(tk)){
				System.out.println("Is Verb");
				Verb v = new Verb(tk);
				s.add(v);
			}else if(isAdj(tk)){
				Adjective adj = new Adjective(tk);
				s.add(adj);
			}else if(isAdv(tk)){
				Adverb adv = new Adverb(tk);
				s.add(adv);
			}else{
				Other t = new Other(tk);
				s.add(t);
			}			
			count++;
		}
		
		size = count;
	}
	
	public boolean isVerb(String text) {
		try {
			InputStream in = fileReader(R.raw.verbs);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
			try {
				String line = null; 
				while (( line = reader.readLine()) != null){
					if(line.equals(text)){
						return true;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean isArticle(String text){
		text.toLowerCase();
		if(text.equals("a") || text.equals("the")){
			return true;
		}
		else{
			return false;			
		}
	}
	
	public boolean isInter(String text){
		if(text.equals("who") || text.equals("what") || text.equals("when") || text.equals("where") || text.equals("how")){
			return true;
		}
		else{
			return false;			
		}
	}
	public boolean isAdv(String text) {

		String filename = this.emoe+"/adverbs";
		try {
			InputStream in = getAssets().open(filename);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
			try {
				String line = null; 
				while (( line = reader.readLine()) != null){
					if(line.equals(text)){
						return true;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean isAdj(String text) {

		String filename = this.emoe+"/adjectives";
		try {
			InputStream in = getAssets().open(filename);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
			try {
				String line = null; 
				while (( line = reader.readLine()) != null){
					if(line.equals(text)){
						return true;
					}
				}
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	
	public void findVerb(int index){		
		Verb v = new Verb(s.get(index).val);
		s.set(index, v);		
	}
	
	public int numAdders(){
		int count = 0;
		for(int i = 0; i < s.size(); i++){
			if(s.get(i) instanceof Starter || s.get(i) instanceof Ender){
				count++;
			}			
		}
		return count;
	}
	
	public Token firstToken(){ //ignores starters
		for(int i = 0; i < s.size(); i++){
			if(s.get(i) instanceof Starter){
				continue;
			}else{
				return s.get(i);
			}
		}		
		return null;
	}
	
	public String toString(){
		String str = ""; 
		for(int i = 0; i < s.size(); i++){
			
			if(s.get(i) instanceof Adjective && s.get(i+1) instanceof Adverb){
				str = str.concat(s.get(i).val+", ");	
			}else{				
				str = str.concat(s.get(i).val+" ");	
			}
			System.out.println(str);
			
		}
		return str;
	}
}
