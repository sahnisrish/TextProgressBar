package com.example.textprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextProgressBar extends AppCompatTextView {

    private String theme;
    private Boolean progress;
    private int speed;
    private Context context;
    private Thread thread;
    private int[] colorText;
    public TextProgressBar(Context context) {
        super(context);
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
            speed = 5;
        }
        set();
        execute();
    }

    private void set() {
        if(theme.equalsIgnoreCase("light")){
            this.setTextColor(getResources().getColor(R.color.light_base));
            colorText = context.getResources().getIntArray(R.array.light);
        }
        else {
            this.setTextColor(getResources().getColor(R.color.dark_base));
            colorText = context.getResources().getIntArray(R.array.dark);
        }
    }

    private void execute() {
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    int position = 0;
                    while (true){
                        if(progress) {
                            int cFrom = position;
                            int cTo = (position + 1) % colorText.length;
                            for (int i = 0; i < 20; i++) {
                                sleep(speed *10);
                                modify(colorText[cFrom], colorText[cTo], 0.05f * i);
                            }
                            position = cTo;
                        }
                        if(progress && position!=0){
                            sleep(speed*10);
                            modify(colorText[position],colorText[0],0.5f);
                            sleep(speed*10);
                            modify(colorText[position],colorText[0],1.0f);
                            position = 0;
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void modify(int colorFrom, int colorTo, float shade) {
                int diffR = Color.red(colorTo) - Color.red(colorFrom);
                int diffG = Color.green(colorTo) - Color.green(colorFrom);
                int diffB = Color.blue(colorTo) - Color.blue(colorFrom);

                int shadeR = (int) (Color.red(colorFrom) + diffR*(shade));
                int shadeG = (int) (Color.green(colorFrom) + diffG*(shade));
                int shadeB = (int) (Color.blue(colorFrom) + diffB*(shade));

                setTextColor(Color.rgb(shadeR,shadeG,shadeB));
            }
        };
        thread.start();
    }

    public void setTheme(String theme){
        this.theme = theme;
        set();
    }

    public void setProgress(Boolean progress){
        this.progress = progress;
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
