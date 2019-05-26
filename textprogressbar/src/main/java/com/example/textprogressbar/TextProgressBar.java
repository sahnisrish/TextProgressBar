package com.example.textprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

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

    private void init(AttributeSet attrs) {
        myTask = new MyTask(context,this);
        if(attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextProgressBar);
            theme = a.getString(R.styleable.TextProgressBar_theme);
            progress = a.getBoolean(R.styleable.TextProgressBar_progress, false);
            try {
                if (theme == null) {
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
            myTask.execute(theme);
        }
    }

    public void setTheme(String theme){
        this.theme = theme;
    }

    public void setProgress(Boolean progress){
        this.progress = progress;
        if(progress)
            myTask.execute(theme);
        else
            myTask.cancel(true);
    }

    public boolean inProgress(){
        return progress;
    }
}
