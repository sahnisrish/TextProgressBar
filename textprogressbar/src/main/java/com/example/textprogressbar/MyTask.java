package com.example.textprogressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Void, Void> {
    private Context context;
    private TextProgressBar textProgressBar;
    public MyTask(Context context, TextProgressBar textProgressBar){
        this.context = context;
        this.textProgressBar = textProgressBar;
    }
    @Override
    protected Void doInBackground(String... strings) {
        String theme = strings[0];
        int colorText[] = new int[8];
        int position = 0;
        if(theme.equals("light")){
            colorText = context.getResources().getIntArray(R.array.light);
        }
        else if(theme.equals("dark")){
            colorText = context.getResources().getIntArray(R.array.dark);
        }
        while (true){
            int cFrom = position;
            int cTo = (position+1)%colorText.length;
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), cFrom, cTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    textProgressBar.setTextColor((Integer)animator.getAnimatedValue());
                }
            });
            try {
                wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            colorAnimation.start();
            if(isCancelled()){
                textProgressBar.setTextColor(colorText[0]);
                break;
            }
            position = cTo;
        }
        return null;
    }
}
