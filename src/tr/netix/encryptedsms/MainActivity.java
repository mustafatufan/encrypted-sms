package tr.netix.encryptedsms;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.util.Xml.Encoding;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private AdView adView;
	public SmsManager smsmanager;
	public static ListView smslistview;
	public EditText smsedittext;
	public EditText smsnumber;
	public static Context context;
	public static ArrayList<String> smsarray;
	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		smslistview = (ListView)findViewById(R.id.listView1);
		smsedittext = (EditText)findViewById(R.id.editText1);
		smsnumber = (EditText)findViewById(R.id.editText2);
		smsarray = new ArrayList<String>();
		smsmanager = SmsManager.getDefault();
		context = getApplicationContext();
		adView = new AdView(this, AdSize.BANNER, "ca-app-pub-8278110607286937/4917293004");
		LinearLayout layout = (LinearLayout)findViewById(R.id.banner);
	    layout.addView(adView);
	    adView.loadAd(new AdRequest());
	}
	//@SuppressWarnings("null")
	public void send(View v) throws Exception
	{
		Log.e("send", "Send starting...");
		smsarray.add("to: "+smsnumber.getText().toString()+"\nmessage: "+smsedittext.getText().toString()+"\nencryption: "+Encrypt(smsedittext.getText().toString())); Log.e("send", "Message adding array...");
		smslistview.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, smsarray)); Log.e("send", "New adapter defining...");
		smsmanager.sendTextMessage(smsnumber.getText().toString(), null, Encrypt(smsedittext.getText().toString()), null, null); Log.e("send", "Message sending...");
		Toast.makeText(getApplicationContext(), "Sending...", Toast.LENGTH_LONG).show(); Log.e("send", "Toast showing...");
		smsedittext.setText(null); Log.e("send", "Message editbox deleting...");
		smsarray.add("");
	}
	public static void receive(String message, String number) throws Exception
	{
		Log.e("send", "Send starting...");
		smsarray.add("from: "+number+"\nmessage: "+message+"\ndecryption: "+Decrypt(message)); Log.e("send", "Message adding array...");
		smslistview.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, smsarray)); Log.e("send", "New adapter defining...");
		smsarray.add("");
	}
	/*
	public static String Encrypt(String message)
	{
		String eMessage = "";
		for(int i=0;i<message.length();i++)
		{
			eMessage += (char)((int)message.charAt(i)*2);
		}
		return eMessage;
	}
	public static String Decrypt(String message)
	{
		String dMessage = "";
		for(int i=0;i<message.length();i++)
		{
			dMessage += (char)((int)message.charAt(i)/2);
		}
		return dMessage;
	}*/
	public String Encrypt(String valueToEnc) throws Exception {
	    Key key = generateKey();
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    c.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encValue = c.doFinal(valueToEnc.getBytes());
	    String encryptedValue = Base64.encodeToString(encValue, Base64.DEFAULT);
	    return encryptedValue;
	}

	public static String Decrypt(String encryptedValue) throws Exception {
	    Key key = generateKey();
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    c.init(Cipher.DECRYPT_MODE, key);
	    byte[] decordedValue = Base64.decode(encryptedValue, Base64.DEFAULT);
	    byte[] decValue = c.doFinal(decordedValue);
	    String decryptedValue = new String(decValue);
	    return decryptedValue;
	}
	private static Key generateKey() throws Exception {
	    Key key = new SecretKeySpec(keyValue, ALGORITHM);
	    return key;
	}
}
