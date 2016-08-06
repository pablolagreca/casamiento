package com.lagreca.casamiento.casamiento;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by pablolagreca on 7/25/16.
 */
public class ShellMessageHelper {

    public static void createShellMessageTextView(AppCompatActivity appCompatActivity, RelativeLayout shellLayout, String mensaje, final Runnable onCompleteTask, final Integer timeBetweenCharacters) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

        final TextView textView = new TextView(appCompatActivity);
        textView.setBackgroundResource(R.drawable.rounded_border_text_view);
//                textView.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(100, 100, 100, 100);
        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        shellLayout.addView(textView);

        final Queue<Character> queue = new LinkedList();
        for (int i = 0; i < mensaje.length(); i++) {
            queue.offer(mensaje.charAt(i));
        }

        final Random random = new Random(System.currentTimeMillis());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Character character = queue.poll();
                if (character != null) {
                    int timeToWait = random.nextInt(timeBetweenCharacters == null ? 50 : timeBetweenCharacters) + 50;
                    if (character.equals('#'))
                    {
                        textView.setText("");
                    }
                    else
                    {
                        SpannableString spanString = new SpannableString(String.valueOf(character));
                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
                        textView.append(spanString);
                        if (".".equals(String.valueOf(textView.getText().charAt(textView.getText().length() - 1)))) {
                            timeToWait = 600;
                        }
                    }
                    new Handler().postDelayed(this, timeToWait);
                } else {
                    onCompleteTask.run();
                }
            }
        };
        new Handler().postDelayed(runnable, 100);
    }
}
