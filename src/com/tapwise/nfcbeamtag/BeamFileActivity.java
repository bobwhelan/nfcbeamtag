package com.tapwise.nfcbeamtag;

	import java.io.File;

import android.app.Activity;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.Toast;

	public class BeamFileActivity extends Activity implements CreateBeamUrisCallback,  OnNdefPushCompleteCallback {
		private NfcAdapter mNfcAdapter;
		private static final int MESSAGE_SENT = 1;


	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
	        

	        
			// Set the Uri(s) to be Beamed
			mNfcAdapter.setBeamPushUrisCallback(this, this);
			
	        // Register callback to listen for message-sent success
	        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
	      
	    }
	    

	    @Override
		protected void onResume() {

			super.onResume();
		}


		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_main, menu);
	        return true;
	    }

		// I don't think this ever gets called since we are not pushing ndef
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
		            Toast.makeText(getApplicationContext(), "File sent!",     Toast.LENGTH_LONG).show();
		            break;
		        }
		    }
		}; 



		public Uri[] createBeamUris(NfcEvent event) {
			// Uri for a Þle
			File myFile = new File("file:///android_asset/smartboard.wav");
			Uri ÞleUri = Uri.fromFile(myFile);
			return new Uri[] {ÞleUri};
		}
		
	}