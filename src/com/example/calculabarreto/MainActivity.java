package com.example.calculabarreto;

import android.R.color;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String n1 = "";
	private String n2 = "";
	private String op = "";
	
	private TextView InputText;
	private TextView OutputText;
	private ImageButton[] numbers = new ImageButton[10];
	private ImageButton virgula;
	private ImageButton inverse;
	private ImageButton clear;
	private ImageButton plus;
	private ImageButton minus;
	private ImageButton mult;
	private ImageButton div;
	private ImageButton root;
	private ImageButton power;
	private ImageButton percentage;
	private ImageButton lowdivision;
	private ImageView barreto;
	private TextView resto;
	private TextView help;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputText = (TextView) findViewById(R.id.InputText);
        OutputText = (TextView) findViewById(R.id.OutputText);
        
        InputText.setText("");
        OutputText.setText("");
        
        virgula =(ImageButton) findViewById(R.id.btn_virg);
        inverse =(ImageButton) findViewById(R.id.btn_inverse);
        clear =(ImageButton) findViewById(R.id.btn_clear);
        plus =(ImageButton) findViewById(R.id.btn_plus);
        minus =(ImageButton) findViewById(R.id.btn_minus);
        mult =(ImageButton) findViewById(R.id.btn_mult);
        div =(ImageButton) findViewById(R.id.btn_div);
        root =(ImageButton) findViewById(R.id.btn_root);
        power =(ImageButton) findViewById(R.id.btn_power);
        percentage =(ImageButton) findViewById(R.id.btn_percentage);
        lowdivision =(ImageButton) findViewById(R.id.btn_lowdivision);
        barreto = (ImageView) findViewById(R.id.btn_barreto);
        resto = (TextView) findViewById(R.id.tv_Resto);
        help = (TextView) findViewById(R.id.tv_Help);
        
        help.setText("?");
        
        barreto.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(op.equals("÷") && !OutputText.getText().toString().isEmpty() && resto.getVisibility() == 0){
					OutputText.setText("Resto: " + Double.toString(Double.parseDouble(n1) % Double.parseDouble(n2)));
					resto.setVisibility(View.INVISIBLE);
				} else if(help.getVisibility() == View.VISIBLE){
					Toast toast = Toast.makeText(MainActivity.this, "Para Resto, faça a divisão e clique em mim", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
        });
        virgula.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(op.isEmpty()){
					if(n1.isEmpty()){
						n1 += "0.";
					}else{
						if(!n1.contains(".")){
							n1 += ".";
						}else{
							n1 = n1.replace(".", "");
							n1+=".";
						}
					}
				}else {
					if(n2.isEmpty()){
						n2 += "0.";
					}else{
						if(!n2.contains(".")){
							n2 += ".";
						}else{
							n2 = n2.replaceAll(".", "");
							n2+=".";
						}
					}
				}
				validation();
			}
        });
        inverse.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
					if(op.isEmpty()) n1 = Double.toString(Double.parseDouble(n1) * -1);
					else n2 = Double.toString(Double.parseDouble(n2) * -1);
					
					validation();
				}catch(Exception e){
					n1="";n2="";op="";
					InputText.setText("");
					OutputText.setText("Tem algo errado ai...");
				}
				
			}
        });
        clear.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				n1=""; n2=""; op="";
				InputText.setText("");
				OutputText.setText("");
				validation();
			}
        });
        plus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!OutputText.getText().toString().isEmpty() 
						&& OutputText.getText().toString().toLowerCase() == OutputText.getText().toString()){
					// Essa verificação evita que o Output text seja constituido por algum caractere, o que geraria
					// muitos problemas...
					n1 = OutputText.getText().toString();
					n2 = "";
				}
				op="+";
				validation();
			}
        });
        minus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!OutputText.getText().toString().isEmpty() 
						&& OutputText.getText().toString().toLowerCase() == OutputText.getText().toString().replace("e", "E")){
					n1 = OutputText.getText().toString();
					n2 = "";
				}
				op="-";
				validation();
			}
        });
        mult.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!OutputText.getText().toString().isEmpty() 
						&& OutputText.getText().toString().toLowerCase() == OutputText.getText().toString().replace("e", "E")){
					n1 = OutputText.getText().toString();
					n2 = "";
				}
				op="x";
				validation();
			}
        });
        div.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!OutputText.getText().toString().isEmpty() 
						&& OutputText.getText().toString().toLowerCase() == OutputText.getText().toString().replace("e", "E")){
					n1 = OutputText.getText().toString();
					n2 = "";
				}
				op="÷";
				validation();
			}
        });
        root.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
					if(!OutputText.getText().toString().isEmpty() 
							&& OutputText.getText().toString().toLowerCase() == OutputText.getText().toString().replace("e", "E")){
						n1 = OutputText.getText().toString();
						n2 = "";
					}
					op="na Raiz de:";
					validation();
					
				}catch(Exception e){
					n1="";n2="";op="";
					InputText.setText("");
					OutputText.setText("Tem algo errado ai...");
				}
				
			}
        });
        power.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				op="^";
				validation();
			}
        });
        percentage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
					if(!n1.isEmpty() && !n2.isEmpty()){	
						n2 = Double.toString((Double.parseDouble(n1) * Double.parseDouble(n2)/100));
					}
					validation();
				}catch(Exception e){
					n1="";n2="";op="";
					InputText.setText("");
					OutputText.setText("Tem algo errado ai...");
				}
			}
        });
        lowdivision.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try{
					if(OutputText.getText().toString().isEmpty()){
						InputText.setText("1/"+ n1);
						OutputText.setText(Double.toString(1 / Double.parseDouble(n1)));
					}else{
						InputText.setText("1/"+ OutputText.getText().toString());
						OutputText.setText(Double.toString(1 / Double.parseDouble(OutputText.getText().toString())));
					}
				}catch(Exception e){
					n1="";n2="";op="";
					InputText.setText("");
					OutputText.setText("Tem algo errado ai...");
				}
			}
        });
        
        for(int x=0; x<10;x++){
        	int id = getResources().getIdentifier("btn_" + x,"id", getPackageName());
        	numbers[x] = (ImageButton) findViewById(id);
        	final int i = x;
        	numbers[x].setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(op.isEmpty()) n1 += i;
					else n2 += i;
					validation();
				}
        	});
        }
        
        
        
    }

	private void validation() {
			
		help.setVisibility(View.INVISIBLE);
		resto.setVisibility(View.INVISIBLE);
		OutputText.setText("");
		InputText.setText(n1 + " " + op + " " + n2);
		
		//Hard-code anti-luís
		if(n2=="-0.0"){
			n2="0.0";
		}if(n1=="-0.0"){
			n1="0.0";
		} try{
			if(InputText.getText().toString().equals("") && OutputText.getText().toString().equals(""))
				help.setVisibility(View.VISIBLE);
			//Te poupei do switch case
			if(op.isEmpty() && n2.isEmpty()){}
			  else if(op.equals("+") && !n2.isEmpty()){
				OutputText.setText(Double.toString(Double.parseDouble(n1) + Double.parseDouble(n2)));
			} else if(op.equals("-") && !n2.isEmpty()){
				OutputText.setText(Double.toString(Double.parseDouble(n1)-Double.parseDouble(n2)));
			} else if(op.equals("x") && !n2.isEmpty()){
				OutputText.setText(Double.toString(Double.parseDouble(n1)*Double.parseDouble(n2)));
			} else if(op.equals("÷") && !n2.isEmpty()){
				if(n2.equals("0") || n2.equals("0.0") || n2.equals("0.")){
					OutputText.setText("POKÉMON!!");
				}else{
					OutputText.setText(Double.toString(Double.parseDouble(n1)/Double.parseDouble(n2)));
					resto.setVisibility(View.VISIBLE);
				}
			} else if(op.equals("^") && !n2.isEmpty()){
				OutputText.setText(Double.toString(Math.pow(Double.parseDouble(n1), Double.parseDouble(n2))));
			} else if(op.equals("na Raiz de:") && !n2.isEmpty()){
				Integer temp = Integer.parseInt(n2);
				if (temp < 0){
					temp *= -1;
				}
				OutputText.setText(Double.toString(Math.pow(Double.parseDouble(n1), (double) 1/ (double)temp)));
			}
			
			if(OutputText.getText().toString().endsWith(".0")){
				OutputText.setText(OutputText.getText().toString().replace(".0", ""));
			}
			Double temp = Double.parseDouble(OutputText.getText().toString());
			if(temp.isNaN() || OutputText.getText().toString().equals("NaN"));
				throw new Exception();
			
		}catch(Exception e){
			n1="";n2="";op="";
			InputText.setText("");
			OutputText.setText("Tem algo errado ai...");
		}
	}
}
