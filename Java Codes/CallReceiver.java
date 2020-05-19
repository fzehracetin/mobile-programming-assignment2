package com.example.myfirstfinalapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallReceiver extends BroadcastReceiver {
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String TAG = "SmsBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Date date = new Date();
        String dateStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        if(intent.getAction().equals(SMS_RECEIVED)) {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] msg = new SmsMessage[mypdu.length];
                for (int i = 0; i < mypdu.length; i++) {
                    String format = dataBundle.getString("format");
                    msg[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                    String message = msg[i].getMessageBody();
                    String phoneNo = msg[i].getOriginatingAddress();
                    Toast toast = Toast.makeText(context, phoneNo + ": " + message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    File logFile = new File(context.getFilesDir(), "myLogs.txt");
                    String text = dateStr + " - SMS Received. Phone Number: " + phoneNo +
                            " Message: " + message;
                    appendLog(text, logFile);
                }
            }
        }
        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast toast = Toast.makeText(context, "Incoming call from: " + incomingNumber, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            File logFile = new File(context.getFilesDir(), "myLogs.txt");
            String text = dateStr + " - Incoming Call. From: " + incomingNumber;
            appendLog(text, logFile);

        }
    }

    public void appendLog(String text, File logFile)
    {
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
