package com.example.myarmstrongapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myarmstrongapplication.ceiling.armstrong.Armstrong;


public class CeilPicture extends Fragment {

    private LinearLayout linLForCanvas, linLForInfo;
    private TextView ceilingInfo;
    private Armstrong armstrong;

    private Paint p;

    private static final String ARM_PARAM_WIDTH = "armstrong_width", ARM_PARAM_HEIGHT = "armstrong_height";

    private double armstr_width, armstr_height;


    public static CeilPicture newInstance(double a_width, double a_height){
        CeilPicture fragment = new CeilPicture();
        Bundle args = new Bundle();
        args.putDouble(ARM_PARAM_WIDTH, a_width);
        args.putDouble(ARM_PARAM_HEIGHT, a_height);
        fragment.setArguments(args);
        return fragment;
    }

    public CeilPicture() {
        Log.d("myLoginf", "CeilPicture()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("myLoginf", "onCreate() savedInstanceState ");
        if (getArguments() != null) {
            armstr_width = getArguments().getDouble(ARM_PARAM_WIDTH);
            armstr_height = getArguments().getDouble(ARM_PARAM_HEIGHT);
        }
        Log.d("myLoginf", "Fragment OnCreate :  width = "+armstr_width+", height = "+armstr_height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("myLoginf", "onCreateView()");
        //Знаходимо наш основний Layout фрагмента і роздуваємо його
        View view = inflater.inflate(R.layout.fragment_ceil_picture, null);
        //Знаходимо 4 TextView за допомогою яких вказуємо якого кольору яка дуталь і заливаємо текст цим кольором
        ((TextView)view.findViewById(R.id.cred)).setTextColor(Color.RED);
        ((TextView)view.findViewById(R.id.cblue)).setTextColor(Color.BLUE);
        ((TextView)view.findViewById(R.id.cgreen)).setTextColor(Color.GREEN);
        ((TextView)view.findViewById(R.id.cgray)).setTextColor(Color.GRAY);

        //
        linLForCanvas = view.findViewById(R.id.linLForCanvas);
        MyPictureView myPictureView = new MyPictureView(getContext());
        linLForCanvas.addView(myPictureView);

        armstrong = new Armstrong(armstr_width, armstr_height);

        linLForInfo = view.findViewById(R.id.linLForInfo);
        ceilingInfo = new TextView(getContext());
        ceilingInfo.setText(getTextInfo(armstrong));
        ceilingInfo.setBackgroundColor(getResources().getColor(R.color.bkgInfoColor));
        ceilingInfo.setTextSize(20);
        linLForInfo.addView(ceilingInfo);
        Log.d("myLoginf", "before return view");
        return view;
    }

    //Допоміжний метод для отримання тексту з всією інформацією розрахованих деталей для стелі
    private String getTextInfo(Armstrong armstrong){
        return ("Ширина - "+armstr_width+", Довжина: "+armstr_height+";\n" +
                "Направляючий профіль - "+armstrong.getGuideStLength()+"см., "+(int)armstrong.getGuideStCount()+"шт.;\n" +
                "Пристінний профіль - "+armstrong.getWallStLength()+"см., "+(int)armstrong.getWallStCount()+"шт.;\n" +
                "Профіль 120 - "+(int)armstrong.getIntermSt120Count()+"шт.;\n" +
                "Профіль 60 - "+(int)armstrong.getIntermSt60Count()+"шт.;\n" +
                "Гаки - "+(int)armstrong.getHooksCount()+"шт.;\n" +
                "Шурупи(Дюбелі) - "+(int)armstrong.getScrewsCount()+"шт.\n");
    }
    public class MyPictureView extends View {
        public MyPictureView(Context context) {
            super(context);
            p = new Paint();
        }
        @Override
        //Задаємо розміри нішого Canvas і даємо можливість scrolling
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        }
        //знаходження значень деталей відносно ширини Layout
        private int getCoefWd(double val) {
            Log.d("myLog", "" + (int) (val * (linLForCanvas.getWidth() / armstrong.getWidth())));
            return (int) (val*(linLForCanvas.getWidth()/armstrong.getWidth()));
        }
        //знаходження значень деталей стелі відносно довжини Layout
        private int getCoefHg(double val) {
            Log.d("myLog", "" + (int) (val * (linLForCanvas.getHeight() / armstrong.getHeight())));
            return (int) (val*( linLForCanvas.getHeight()/armstrong.getHeight()));
        }
        @Override
        //В цьому методі малюємо нашу стелю на Canvas кожні деталі різним кольором
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.d("myLog", "OnDraw");
            canvas.drawColor(Color.WHITE);
            //Малюємо профіль 60
            p.setStrokeWidth(3);
            p.setColor(Color.GREEN);
            double x = (int) armstrong.getWidthStartLength();
            canvas.drawLine(getCoefWd(x), 0, getCoefWd(x), getCoefHg(armstrong.getHeight()), p);
            for (int i = 0; i < (armstrong.getCountHorisontal60()); i++) {
                x += 60;
                canvas.drawLine(getCoefWd(x), 0, getCoefWd(x), getCoefHg(armstrong.getHeight()), p);
            }
            x = 0;
            //Малюємо профіль 120
            p.setStrokeWidth(3);
            p.setColor(Color.BLUE);
            double y = (int) armstrong.getHeightStartLength();
            canvas.drawLine(0, getCoefHg(y), getCoefWd(armstrong.getWidth()), getCoefHg(y), p);
            for (int i = 0; i < (armstrong.getCountVertical120() - 1); i++) {
                y += 60;
                canvas.drawLine(0, getCoefHg(y), getCoefWd(armstrong.getWidth()), getCoefHg(y), p);
            }
            y=0;
            //Малюємо направляючі профілі
            p.setColor(Color.RED);
            p.setStrokeWidth(5);
            double z = (int) (armstrong.getWidthStartLength() + 60);
            canvas.drawLine(getCoefWd(z), 0, getCoefWd(z), getCoefHg(armstrong.getHeight()), p);
            Log.d("myLogg",  "z = "+z+"armsrtong width = "+armstrong.getWidth()+"origin count = "+armstrong.getOriginCount());
            for (int i = 0; i < (armstrong.getOriginCount()-1); i++) {
                if((armstrong.getWidth()-z)<120){
                    break;
                }
                z += 120;
                if((armstrong.getWidth()-z)<60){
                    z-=60;
                    canvas.drawLine(getCoefWd(z), 0, getCoefWd(z), getCoefHg(armstrong.getHeight()), p);
                    break;
                }
                Log.d("myLogg", "i = "+i+", z = "+z+"armsrtong width = "+armstrong.getWidth());
                canvas.drawLine(getCoefWd(z), 0, getCoefWd(z), getCoefHg(armstrong.getHeight()), p);
            }
            z=0;
            p.setColor(Color.DKGRAY);
            p.setStrokeWidth(4);
            //Малюємо контури
            canvas.drawLine(0, 0, getCoefWd(armstrong.getWidth()), 0, p);
            canvas.drawLine(getCoefWd(armstrong.getWidth()), 0, getCoefWd(armstrong.getWidth()), getCoefHg(armstrong.getHeight()), p);
            canvas.drawLine(getCoefWd(armstrong.getWidth()), getCoefHg(armstrong.getHeight()), 0, getCoefHg(armstrong.getHeight()), p);
            canvas.drawLine(0, getCoefHg(armstrong.getHeight()), 0, 0, p);
        }
    }
}
