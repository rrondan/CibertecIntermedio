package pe.edu.cibertec.capitulo2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String CHANNEL_ID = "canal_ejemplo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            crearNoticiationChannel(CHANNEL_ID, "Canal de ejemplo");
        }
        switch (position){
            case 0: mostrarNotificacion(position, construirNotificacioBasica(position)); break;
            case 1: mostrarNotificacion(position, construirNotificacionManteniendoNavegacion()); break;
            case 2: mostrarNotificacion(position, construirNotificacionConOpciones(position)); break;
            case 3: mostrarNotificacion(position, construirNotificacionTextoGrande()); break;
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    void crearNoticiationChannel(String channelId, String nombre){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.createNotificationChannel(
                    new NotificationChannel(channelId, nombre, NotificationManager.IMPORTANCE_HIGH)
            );
        }
    }

    private void mostrarNotificacion(int id, Notification notification){
        //Acá mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, notification);
    }

    private Notification construirNotificacioBasica(int id){
        Intent resultIntent = new Intent(this, NotificacionActivity.class);
        resultIntent.putExtra("notification_id", id);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Mi notificación")
                    .setColor(Color.parseColor("#F4C975"))
                    .setContentText("Notificación básica")
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(resultPendingIntent);

        return builder.build();
    }
    private Notification construirNotificacionManteniendoNavegacion() {
        Intent resultIntent = new Intent(this, NotificacionActivity.class);
        Intent homeIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntent(homeIntent);
        taskStackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Mi notificación")
                .setContentText("Manteniendo Notificación")
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#F4C975"))
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);

        return builder.build();
    }

    private Notification construirNotificacionConOpciones(int id) {
        Intent desecharIntent = new Intent(this, DesecharReceiver.class);
        desecharIntent.putExtra("notification_id", id);

        Intent posponerIntent = new Intent(this, NotificacionActivity.class);
        posponerIntent.putExtra("notification_id", id);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this,0,
                desecharIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent snoozePendingIntent = PendingIntent.getActivity(this, 0,
                posponerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Mi notificacion")
                .setContentText("Notificacion con Acciones")
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(Color.parseColor("#F4C975"))
                .addAction(R.drawable.ic_dismiss,"Dismiss", dismissPendingIntent)
                .addAction(R.drawable.ic_snooze, "Snooze", snoozePendingIntent);
                //.setContentIntent(snoozePendingIntent)
                //.setAutoCancel(true);


        return builder.build();
    }
    private Notification construirNotificacionTextoGrande(){
        Intent resultIntent = new Intent(this, NotificacionActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentIntent(resultPendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(Color.parseColor("#F4C975"))
                    .setContentTitle("Mi notificacion")
                    .setContentText("Con texto Grande")
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                    );

        return builder.build();
    }
}
