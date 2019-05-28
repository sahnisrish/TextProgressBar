package com.example.textprogressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class MyTask {
    private TextProgressBar textProgressBar;
    private Boolean cancel = false;
    private Thread thread;
    private String theme;
    private int speed;
    private int[] colorText;
    private Context context;
    private MyTask(Context context, TextProgressBar textProgressBar){
        this.textProgressBar = textProgressBar;
        this.speed = 6 - textProgressBar.getSpeed();
        this.theme = textProgressBar.getTheme();
        this.context = context;
        setColors(theme);
    }

    private void setColors(String theme) {
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

    public void execute() {
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    int position = 0;
                    while (true){
                        if(!isCancelled()) {
                            int cFrom = position;
                            int cTo = (position + 1) % colorText.length;
                            for (int i = 0; i < 200; i++) {
                                sleep(3 * speed);
                                modify(colorText[cFrom], colorText[cTo], 0.005f * i);
                            }
                            position = cTo;
                        }
                        if(isCancelled() && position!=0){
                            sleep(10*speed);
                            modify(colorText[position],colorText[0],0.5f);
                            sleep(10*speed);
                            modify(colorText[position],colorText[0],1.0f);
                            position = 0;
                        }
                    }
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

    @Override
    protected void finalize() throws Throwable {
        thread.interrupt();
        super.finalize();
    }

    public void setTheme(String theme) {
        this.theme = theme;
        setColors(theme);
        if(isCancelled()){
            textProgressBar.setTextColor(colorText[0]);
        }
    }

    public void setSpeed(int speed) {
        this.speed = 6 - speed;
    }
}
