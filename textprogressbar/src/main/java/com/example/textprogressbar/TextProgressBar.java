package com.example.textprogressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

public class TextProgressBar extends AppCompatTextView {

    private String theme;
    private Boolean progress;
    private int speed;
    private Context context;
    private MyTask myTask;
    public TextProgressBar(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void init(AttributeSet attrs) {
        myTask = MyTask.open(context,this);
        if(attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextProgressBar);
            theme = a.getString(R.styleable.TextProgressBar_ptheme);
            progress = a.getBoolean(R.styleable.TextProgressBar_progress, false);
            speed = a.getInt(R.styleable.TextProgressBar_speed,1);
            if(speed>5)
                speed =5;
            if(speed<1)
                speed = 1;
            try {
                if (theme == null) {
                    theme = "light";
                }
                else if(!(theme.equalsIgnoreCase("light")||theme.equalsIgnoreCase("dark"))){
                    theme = "light";
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        else {
            theme = "light";
            progress = false;
        }
        if(progress){
            myTask.execute();
        }
        if(theme.equalsIgnoreCase("light")){
            this.setTextColor(R.color.light_base);
        }
        else {
            this.setTextColor(R.color.dark_base);
        }
    }

    public void setTheme(String theme){
        this.theme = theme;
    }

    public void setProgress(Boolean progress){
        this.progress = progress;
        if(progress) {
            myTask.execute();
        }
        else {
            myTask.cancel(true);
        }
    }

    public Boolean inProgress(){
        return progress;
    }

    public String getTheme() {
        return theme;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
