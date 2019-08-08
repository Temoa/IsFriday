package me.temoa.isfriday;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by lai
 * on 2019/7/29.
 */
public class FridayWidgetProvider extends AppWidgetProvider {

  private static final String TAG = "FridayWidgetProvider";

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);

    Constraints constraints = new Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build();
    PeriodicWorkRequest request
            = new PeriodicWorkRequest.Builder(DateUpdateWorker.class, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag(TAG)
            .build();
    WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork("FridayWidgetProvider", ExistingPeriodicWorkPolicy.REPLACE, request);

    update(context);
  }

  @Override
  public void onDisabled(Context context) {
    WorkManager.getInstance(context).cancelAllWorkByTag(TAG);
    super.onDisabled(context);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    update(context);
  }

  private void update(Context context) {
    Calendar calendar = Calendar.getInstance();
    int date = calendar.get(Calendar.DAY_OF_WEEK);

    int leftDrawableResId;
    int rightDrawableResId = R.drawable.emoji_u1f914;
    String leftText = "今天是周五吗?";
    String rightText;
    switch (date) {
      case 1:
        leftDrawableResId = R.drawable.emoji_u1f60e_sun;
        rightDrawableResId = R.drawable.emoji_u1f60e_sun;
        leftText = "今天是周日!";
        rightText = "是的";
        break;
      case 2:
        leftDrawableResId = R.drawable.emoji_u1f622_mon;
        rightText = "不, 周一";
        break;
      case 3:
        leftDrawableResId = R.drawable.emoji_u1f616_tue;
        rightText = "不, 周二";
        break;
      case 4:
        leftDrawableResId = R.drawable.emoji_u1f971_wed;
        rightText = "不, 周三";
        break;
      case 5:
        leftDrawableResId = R.drawable.emoji_u1f924_thu;
        rightText = "不, 周四";
        break;
      default:
      case 6:
        leftDrawableResId = R.drawable.emoji_u1f601_fri;
        leftText = "今天是周五吗?是哒!";
        rightText = "...";
        break;
      case 7:
        leftDrawableResId = R.drawable.emoji_u1f60d_sat;
        rightDrawableResId = R.drawable.emoji_u1f60d_sat;
        leftText = "今天是周末!!!";
        rightText = "今天是周末!!!";
        break;
    }
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.friday_widget_layout);
    views.setImageViewResource(R.id.iv_left, leftDrawableResId);
    views.setImageViewResource(R.id.iv_right, rightDrawableResId);
    views.setTextViewText(R.id.tv_left, leftText);
    views.setTextViewText(R.id.tv_right, rightText);
    ComponentName name = new ComponentName(context, FridayWidgetProvider.class);
    AppWidgetManager.getInstance(context).updateAppWidget(name, views);
  }

  public static class DateUpdateWorker extends Worker {

    private Context mContext;

    public DateUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
      super(context, workerParams);
      mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
      runOnUIThread(new Runnable() {
        @Override
        public void run() {
          Intent i = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
          mContext.sendBroadcast(i);
        }
      });
      return Result.success();
    }

    private void runOnUIThread(Runnable runnable) {
      new Handler(Looper.getMainLooper()).post(runnable);
    }
  }
}
