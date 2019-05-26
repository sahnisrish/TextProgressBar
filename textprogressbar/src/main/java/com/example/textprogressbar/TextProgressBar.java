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
        myTask = null;
        if(attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextProgressBar);
            theme = a.getString(R.styleable.TextProgressBar_ptheme);
            progress = a.getBoolean(R.styleable.TextProgressBar_progress, false);
            try {
                if (theme == null || !(theme.equalsIgnoreCase("light")||theme.equalsIgnoreCase("dark"))) {
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
            myTask = MyTask.open(context,this);
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
        myTask.cancel(true);
        myTask = MyTask.close(myTask);
        if(progress) {
            myTask = MyTask.open(context,this);
            myTask.execute();
        }
    }

    public Boolean inProgress(){
        return progress;
    }

    public String getTheme() {
        return theme;
    }
}
