package com.khrisna.cataloguemovie.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.activity.DetailsActivity;
import com.khrisna.cataloguemovie.model.Movie;
import com.khrisna.cataloguemovie.model.MovieResponse;
import com.khrisna.cataloguemovie.network.GetDataService;
import com.khrisna.cataloguemovie.network.RetrofitClientInstance;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulerService extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";

    private GetDataService service = RetrofitClientInstance
            .getRetrofitInstance()
            .create(GetDataService.class);

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void loadData() {
        Call<MovieResponse> getUpcoming = service.getUpcoming();
        getUpcoming.clone().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<Movie> items = response.body().getMovieResponse();
                    int index = new Random().nextInt(items.size());

                    Movie item = items.get(index);
                    String title = items.get(index).getMovieTitle();
                    String message = items.get(index).getMovieOverview();
                    int notifId = 200;

                    showNotification(getApplicationContext(), title, message, notifId, item);

                } else loadFailed();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(this, "ERROR LOAD DATA", Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, Movie item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("ARG_MOVIE_DATA", item);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, builder.build());
        }
    }
}