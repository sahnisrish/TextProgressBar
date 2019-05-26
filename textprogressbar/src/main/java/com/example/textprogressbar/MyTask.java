package com.example.textprogressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class MyTask {
    private Context context;
    private TextProgressBar textProgressBar;
    private Boolean cancel = false;
    public MyTask(Context context, TextProgressBar textProgressBar){
        this.context = context;
        this.textProgressBar = textProgressBar;
    }
    public void execute(final String theme) {
        int colorText[] = new int[8];
        if(theme.equalsIgnoreCase("light")){
            colorText = context.getResources().getIntArray(R.array.light);
        }
        else if(theme.equalsIgnoreCase("dark")){
            colorText = context.getResources().getIntArray(R.array.dark);
        }
        final int[] finalColorText = colorText;
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    int position = 0;
                    while (true){
                        int cFrom = position;
                        int cTo = (position+1)% finalColorText.length;
                        Log.e("COLORS", cFrom + " " + cTo);
                        sleep(500);
                        Log.e("500",  "Sleep Over");
                        Boolean val = textProgressBar.modify(finalColorText[cFrom],finalColorText[cTo]);
                        Log.e("Post Animation",  val + "");
                        if(isCancelled()){
                            val = textProgressBar.modify(finalColorText[cTo],finalColorText[0]);
                            if (val)
                                break;
                        }
                        position = cTo;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private Boolean isCancelled(){
        return cancel;
    }

    public void cancel(Boolean cancel){
        this.cancel = cancel;
    }
}
