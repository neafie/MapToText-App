package edu.fsu.cs.mobile.mapfromtext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver{
	NotificationManager mNotificationManager;
	
	@Override
    public void onReceive(Context context, Intent intent) {
		mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		int NOTIFICATION_ID = 1;
		Bundle bundle = intent.getExtras();
		if (bundle == null) return;
		Object messages[] = (Object[]) bundle.get("pdus");
	    SmsMessage SMS[] = new SmsMessage[messages.length];
	        
	    for (int n = 0; n < messages.length; n++) {
	            SMS[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
	            String message = SMS[0].getMessageBody();
		    	
		    	
		    	//Returns string with only the address
		    	message = Parse(message);
	       
		    	if(message!=""){
		    	Notification notification = new Notification(R.drawable.ic_launcher,"Address Found", 10);
		    	Intent notificationIntent = new Intent(context, MainActivity.class); 
		    	notificationIntent.putExtra("message", message);
		    	notificationIntent.setAction(Long.toString(System.currentTimeMillis()));
		    	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		    	notification.setLatestEventInfo(context, "Map-From-Text App", "Address Found", contentIntent);
		    	notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL;
				mNotificationManager.notify(NOTIFICATION_ID , notification);
				Log.d("receiver", message);
		    	}
	
	        }
    }
	
	public String Parse(String s)
	{
		
		String [] streetnames = {	"Street", "street", "St.", "St", "st", "st.", 
									"Drive", "drive", "Dr.", "Dr", "dr", "dr.", 
									"Road", "road", "Rd.", "Rd", "rd", "rd.", 
									"Boulevard", "boulevard", "Bldv.", "Blvd", "blvd", "blvd.",
									"Place", "place", "Pl.", "Pl", "pl", "pl.",
									"Court", "court", "Ct." , "Ct", "ct", "ct.",
									"Circle", "circle", "Cir.", "Cir", "cir", "cir.",
									"Highway", "highway", "Hwy.", "Hwy", "hwy", "hwy.",
									"Avenue", "avenue", "Ave.", "Ave", "ave", "ave.",
									"Lane", "lane", "Ln.", "Ln", "ln", "ln." };

		boolean streetfound = false;
		int streetArrayPosition = 0;
		String part1 = "";
		String part2 = "";
		String result = s;
		
		if(s.matches("(.*)[0-9][0-9][0-9][0-9][0-9].*[A-Z][A-Z].*[0-9][0-9][0-9][0-9][0-9](.*)"))
		{
			Pattern pattern = Pattern.compile("(.*)[0-9][0-9][0-9][0-9][0-9].*[A-Z][A-Z].*[0-9][0-9][0-9][0-9][0-9](.*)");
			Matcher matcher = pattern.matcher(s);
			if (matcher.find())
			{
				part1 = matcher.group(1);
				part2 = matcher.group(2);
			}
			
			result = result.replace(part1, "");
			result = result.replace(part2, "");
			
			return result;
		}
		else if(s.matches("(.*)[0-9][0-9][0-9][0-9].*[A-Z][A-Z].*[0-9][0-9][0-9][0-9][0-9](.*)"))
		{
			Pattern pattern = Pattern.compile("(.*)[0-9][0-9][0-9][0-9].*[A-Z][A-Z].*[0-9][0-9][0-9][0-9][0-9](.*)");
			Matcher matcher = pattern.matcher(s);
			if (matcher.find())
			{
				part1 = matcher.group(1);
				part2 = matcher.group(2);
			}
			
			result = result.replace(part1, "");
			result = result.replace(part2, "");
			
			return result;
		}
		else if(s.matches("(.*)[0-9][0-9][0-9].*[A-Z][A-Z].*[0-9][0-9][0-9][0-9][0-9](.*)"))
		{
			Pattern pattern = Pattern.compile("(.*)[0-9][0-9][0-9].*[A-Z][A-Z].*[0-9][0-9][0-9][0-9][0-9](.*)");
			Matcher matcher = pattern.matcher(s);
			if (matcher.find())
			{
				part1 = matcher.group(1);
				part2 = matcher.group(2);
			}
			
			result = result.replace(part1, "");
			result = result.replace(part2, "");
			
			return result;
		}
		for(int i = 0; i < 60; ++i)
		{
			if(s.contains(streetnames[i]))
			{
				streetfound = true;
				streetArrayPosition = i;
			}
		}
		if(streetfound)
		{
			int endIdx = s.indexOf(streetnames[streetArrayPosition]) + streetnames[streetArrayPosition].length();
			result = s.substring(0, endIdx);
			
			if(result.matches("(.*)[0-9][0-9][0-9][0-9][0-9](.*)"))
			{
				Pattern pattern = Pattern.compile("(.*)[0-9][0-9][0-9][0-9][0-9](.*)");
				Matcher matcher = pattern.matcher(result);
				if(matcher.find())
				{
					part1 = matcher.group(1);
				}
				
				result = result.replace(part1, "");
				
				return result;
			}
			else if(result.matches("(.*)[0-9][0-9][0-9][0-9](.*)"))
			{
				Pattern pattern = Pattern.compile("(.*)[0-9][0-9][0-9][0-9](.*)");
				Matcher matcher = pattern.matcher(result);
				if(matcher.find())
				{
					part1 = matcher.group(1);
				}
				
				result = result.replace(part1, "");
				
				return result;
			}
			else if(result.matches("(.*)[0-9][0-9][0-9](.*)"))
			{
				Pattern pattern = Pattern.compile("(.*)[0-9][0-9][0-9](.*)");
				Matcher matcher = pattern.matcher(result);
				if(matcher.find())
				{
					part1 = matcher.group(1);
				}
				
				result = result.replace(part1, "");
				
				return result;
			}
		}
		
		return "";
	}

}