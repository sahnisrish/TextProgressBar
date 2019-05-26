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
    private String theme;
    private int[] colorText;
    private MyTask(Context context, TextProgressBar textProgressBar){
        this.context = context;
        this.textProgressBar = textProgressBar;
        this.theme = textProgressBar.getTheme();
        if(theme.equalsIgnoreCase("light")){
            colorText = context.getResources().getIntArray(R.array.light);
        }
        else {
            colorText = context.getResources().getIntArray(R.array.dark);
        }
    }

    public static MyTask open(Context context, TextProgressBar textProgressBar) {
        return new MyTask(context, textProgressBar);
    }

    public static MyTask close(MyTask myTask) {
        myTask.thread.stop();
        return null;
    }

    public void execute() {
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    int position = 0;
                    while (!isCancelled()){
                        int cFrom = position;
                        int cTo = (position+1)% colorText.length;
//                        Log.e("COLORS", cFrom + " " + cTo);
                        sleep(125);
                        modify(colorText[cFrom],colorText[cTo],0.25f);
                        sleep(125);
                        modify(colorText[cFrom],colorText[cTo],0.50f);
                        sleep(125);
                        modify(colorText[cFrom],colorText[cTo],0.75f);
                        sleep(125);
                        modify(colorText[cFrom],colorText[cTo],1.0f);
                        sleep(125);
                        position = cTo;
                    }
                    modify(colorText[position],colorText[0],0.5f);
                    sleep(100);
                    modify(colorText[position],colorText[0],1.0f);
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
    }
}
