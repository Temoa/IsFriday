package me.temoa.isfriday;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.friday_widget_layout);
    update();
  }

  private void update() {
    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    TextView leftTv = findViewById(R.id.tv_left);
    TextView rightTv = findViewById(R.id.tv_right);
    ImageView leftIv = findViewById(R.id.iv_left);
    ImageView rightIv = findViewById(R.id.iv_right);

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

    leftIv.setImageResource(leftDrawableResId);
    rightIv.setImageResource(rightDrawableResId);
    leftTv.setText(leftText);
    rightTv.setText(rightText);
  }
}
