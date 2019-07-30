package me.temoa.isfriday;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Created by lai
 * on 2019/7/29.
 */
public class FridayWidgetProvider extends AppWidgetProvider {

  private static final String TAG = "FridayWidgetProvider";

  private TimeChangeReceiver mTimeChangeReceiver;

  public class TimeChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      Intent i = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
      context.sendBroadcast(i);
    }
  }

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    mTimeChangeReceiver = new TimeChangeReceiver();
    context.getApplicationContext().registerReceiver(mTimeChangeReceiver, new IntentFilter(Intent.ACTION_DATE_CHANGED));
//    context.getApplicationContext().registerReceiver(mTimeChangeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    update(context);
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    if (mTimeChangeReceiver != null) {
      context.getApplicationContext().unregisterReceiver(mTimeChangeReceiver);
    }
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    update(context);
  }

  private void update(Context context) {
    Log.d(TAG, "update() called");

    Calendar calendar = Calendar.getInstance();
    int date = calendar.get(Calendar.DAY_OF_WEEK);
//    int max = 7;
//    int min = 1;
//    Random random = new Random();
//    int date = random.nextInt(max) % (max - min + 1) + min;

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
}
