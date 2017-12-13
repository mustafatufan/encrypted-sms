package tr.netix.encryptedsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver 
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle extras = intent.getExtras();
		if(extras!=null)
		{
			Object[] smsExtra = (Object[]) extras.get( "pdus" );
			for ( int i = 0; i < smsExtra.length; ++i ) 
			{
				SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
				String address = sms.getOriginatingAddress();
				String body = sms.getMessageBody();
				try {
					MainActivity.receive(body,address);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
