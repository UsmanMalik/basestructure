package com.teeku.christmas;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.teeku.christmas.Database.DatabaseHandler;
import com.teeku.christmas.Model.Item;
import com.teeku.christmas.Model.ItemChild;

/**
 * Created by usman on 12/16/15.
 */
public class JSONCustomReceiver  extends ParsePushBroadcastReceiver {

    DatabaseHandler db = null;

    @Override
    protected void onPushReceive(Context context, Intent intent) {

        super.onPushReceive(context, intent);
        Log.d("Push", "Push received");

        db = new DatabaseHandler(context);

        if (intent == null)
            return ;

        String jsonData = intent.getExtras().getString("com.parse.Data");

        Log.d("Push", "JSON Data ["+jsonData+"]");

        String data = getData(jsonData);


        // Add custom intent
        Intent cIntent = new Intent(context, CardElement.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, cIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create custom notification
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.abc_btn_check_material)
                .setContentText(data)
                .setContentTitle("New data is uploaded..")
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(1410, notification);

    }



    private String getData(String jsonData) {
        // Parse JSON Data
        try {
            System.out.println("JSON Data ["+jsonData+"]");
            JSONObject obj = new JSONObject(jsonData);

            JSONObject newItem = new JSONObject(obj.getString("message"));
            Log.d("item title: ", newItem.getString("item_id"));

            // data

            Log.d("boxes count first: ", db.getBoxesCount() + "");
            Item i = new Item();
            ItemChild c = new ItemChild();

            i.setId((int) newItem.getInt("item_id"));
            i.setTitle((String) newItem.getString("item_title"));
            i.setDescription((String) newItem.getString("item_description"));

            db.addCategory(i);
            //categoryList.add(i);


            c.setId((int) newItem.getInt("child_id"));
            c.setItem_id((int) newItem.getInt("child_item_id"));
            c.setTitle((String) newItem.getString("child_title"));
            c.setDescription((String) newItem.getString("child_description"));
            c.setImage_path((String) newItem.getString("child_video_path"));

            db.addBox(c);

            Log.d("boxes count second: ", db.getBoxesCount() + "");

            return obj.getString("message");

        }
        catch(JSONException jse) {
            jse.printStackTrace();
        }

        return "";
    }
}
