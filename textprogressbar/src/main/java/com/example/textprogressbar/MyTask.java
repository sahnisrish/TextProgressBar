package com.example.textprogressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Void, String> {
    private Context context;
    private TextProgressBar textProgressBar;
    public MyTask(Context context, TextProgressBar textProgressBar){
        this.context = context;
        this.textProgressBar = textProgressBar;
    }
    @Override
    protected String doInBackground(String... strings) {
        String theme = strings[0];
        return theme;
    }

    @Override
    protected void onPostExecute(String theme) {
        int colorText[] = new int[8];
        int position = 0;
        if(theme.equalsIgnoreCase("light")){
            colorText = context.getResources().getIntArray(R.array.light);
        }
        else if(theme.equalsIgnoreCase("dark")){
            colorText = context.getResources().getIntArray(R.array.dark);
        }
        while (true){
            int cFrom = position;
            int cTo = (position+1)%colorText.length;
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorText[cFrom], colorText[cTo]);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    textProgressBar.setTextColor((Integer)animator.getAnimatedValue());
                }
            });
            try {
                synchronized (this) {
                    wait(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            colorAnimation.start();
            if(isCancelled()){
                ValueAnimator colorReset = ValueAnimator.ofObject(new ArgbEvaluator(), colorText[cTo], colorText[0]);
                colorReset.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        textProgressBar.setTextColor((Integer)animator.getAnimatedValue());
                    }
                });
                break;
            }
            position = cTo;
        }
        super.onPostExecute(theme);
    }
}
