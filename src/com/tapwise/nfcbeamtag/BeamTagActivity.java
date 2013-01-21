 package com.tapwise.nfcbeamtag;

import java.nio.charset.Charset;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class BeamTagActivity extends Activity implements CreateNdefMessageCallback,  OnNdefPushCompleteCallback {
	NfcAdapter mNfcAdapter;
	NdefRecord ndefRecord;
	private static final int MESSAGE_SENT = 1;
	private Button beamFileBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beamFileBtn = (Button) findViewById(R.id.beamFileBtn);
		beamFileBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent a = new Intent(BeamTagActivity.this,BeamFileActivity.class);
			    startActivity(a);
			}

		});
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // Register callback to set NDEF message
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        // Register callback to listen for message-sent success
        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
      
    }
    

    @Override
	protected void onResume() {
    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {   		
    		beamFileBtn.setVisibility(Button.VISIBLE);
    	}
		super.onResume();
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onNdefPushComplete(NfcEvent arg0) {
		// TODO Auto-generated method stub
		   mHandler.obtainMessage(MESSAGE_SENT).sendToTarget(); 
		
	}



	/** This handler receives a message from onNdefPushComplete */
	private final Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	        case MESSAGE_SENT:
	            Toast.makeText(getApplicationContext(), "Message sent!",     Toast.LENGTH_LONG).show();
	            break;
	        }
	    }
	}; 
	
	/**
	 * Creates a custom MIME type encapsulated in an NDEF record
	 * @param mimeType
	 */
	public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
	    byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
	    NdefRecord mimeRecord = new NdefRecord(
	            NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
	    return mimeRecord;
	}
	

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "newIntent", Toast.LENGTH_SHORT).show();
		super.onNewIntent(intent);
	}

	public NdefMessage createNdefMessage(NfcEvent event) {
	    NdefMessage msg = new NdefMessage(
	            new NdefRecord[] {	 getNdefRecord() });
	    return msg;
	}
	
	private NdefRecord getNdefRecord() {
	    NdefRecord uriRecord = new NdefRecord(NdefRecord.TNF_ABSOLUTE_URI ,
	              "http://www.nfclabels.com".getBytes(Charset.forName("US-ASCII")),
	              new byte[0], new byte[0]);
	    NdefRecord aarRecord = NdefRecord.createApplicationRecord("com.tapwise.nfcreadtag");
	    String text = "Hello World!";
	    NdefRecord plainRecord = createMimeRecord("text/plain", text.getBytes());

		return plainRecord;
	}
	
}
