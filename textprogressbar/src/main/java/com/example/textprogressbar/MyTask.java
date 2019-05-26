package com.example.textprogressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class MyTask {
    private Context context;
    private TextProgressBar textProgressBar;
    private Boolean cancel = false;
    private Thread thread;
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
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    int position = 0;
                    while (!isCancelled()){
                        int cFrom = position;
                        int cTo = (position+1)% finalColorText.length;
//                        Log.e("COLORS", cFrom + " " + cTo);
                        sleep(125);
                        modify(finalColorText[cFrom],finalColorText[cTo],0.25f);
                        sleep(125);
                        modify(finalColorText[cFrom],finalColorText[cTo],0.50f);
                        sleep(125);
                        modify(finalColorText[cFrom],finalColorText[cTo],0.75f);
                        sleep(125);
                        modify(finalColorText[cFrom],finalColorText[cTo],1.0f);
                        sleep(125);
                        position = cTo;
                    }
                    modify(finalColorText[position],finalColorText[0],0.5f);
                    sleep(100);
                    modify(finalColorText[position],finalColorText[0],1.0f);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void modify(int colorFrom, int colorTo, float shade) {
        int diffR = Color.red(colorTo) - Color.red(colorFrom);
        int diffG = Color.green(colorTo) - Color.green(colorFrom);
        int diffB = Color.blue(colorTo) - Color.blue(colorFrom);

        int shadeR = (int) (Color.red(colorFrom) + diffR*(shade));
        int shadeG = (int) (Color.green(colorFrom) + diffG*(shade));
        int shadeB = (int) (Color.blue(colorFrom) + diffB*(shade));

        textProgressBar.setTextColor(Color.rgb(shadeR,shadeG,shadeB));
    }

    private Boolean isCancelled(){
        return cancel;
    }

    public void cancel(Boolean cancel){
        this.cancel = cancel;
        thread.stop();
    }
}
