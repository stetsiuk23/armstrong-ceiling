package com.example.myarmstrongapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myarmstrongapplication.ceiling.armstrong.Armstrong;

public class CeilPicture extends Fragment {
    private LinearLayout linLForCanvas;
    private Armstrong armstrong;
    private Paint p;
    private static final String ARM_PARAM_WIDTH = "armstrong_width", ARM_PARAM_HEIGHT = "armstrong_height";
    private double armstr_width, armstr_height;

    public static CeilPicture newInstance(double a_width, double a_height) {
        CeilPicture fragment = new CeilPicture();
        Bundle args = new Bundle();
        args.putDouble(ARM_PARAM_WIDTH, a_width);
        args.putDouble(ARM_PARAM_HEIGHT, a_height);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            armstr_width = getArguments().getDouble(ARM_PARAM_WIDTH);
            armstr_height = getArguments().getDouble(ARM_PARAM_HEIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ceil_picture, null);
        ((TextView) view.findViewById(R.id.cred)).setTextColor(Color.RED);
        ((TextView) view.findViewById(R.id.cblue)).setTextColor(Color.BLUE);
        ((TextView) view.findViewById(R.id.cgreen)).setTextColor(Color.GREEN);
        ((TextView) view.findViewById(R.id.cgray)).setTextColor(Color.GRAY);
        linLForCanvas = view.findViewById(R.id.linLForCanvas);
        MyPictureView myPictureView = new MyPictureView(getContext());
        linLForCanvas.addView(myPictureView);
        armstrong = new Armstrong(armstr_width, armstr_height);
        LinearLayout linLForInfo = view.findViewById(R.id.linLForInfo);
        TextView ceilingInfo = new TextView(getContext());
        ceilingInfo.setText(getTextInfo(armstrong));
        ceilingInfo.setBackgroundColor(getResources().getColor(R.color.bkgInfoColor));
        ceilingInfo.setTextSize(20);
        linLForInfo.addView(ceilingInfo);
        return view;
    }

    private String getTextInfo(Armstrong armstrong) {
        return ("Ширина - " + armstr_width + ", Довжина: " + armstr_height + ";\n" +
                "Направляючий профіль - " + armstrong.getGuideStLength() + "см., " + (int) armstrong.getGuideStCount() + "шт.;\n" +
                "Пристінний профіль - " + armstrong.getWallStLength() + "см., " + (int) armstrong.getWallStCount() + "шт.;\n" +
                "Профіль 120 - " + (int) armstrong.getIntermSt120Count() + "шт.;\n" +
                "Профіль 60 - " + (int) armstrong.getIntermSt60Count() + "шт.;\n" +
                "Гаки - " + (int) armstrong.getHooksCount() + "шт.;\n" +
                "Шурупи(Дюбелі) - " + (int) armstrong.getScrewsCount() + "шт.\n");
    }

    public class MyPictureView extends View {

        public MyPictureView(Context context) {
            super(context);
            p = new Paint();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        }

        private int getCoefWd(double val) {
            return (int) (val * (linLForCanvas.getWidth() / armstrong.getWidth()));
        }

        private int getCoefHg(double val) {
            return (int) (val * (linLForCanvas.getHeight() / armstrong.getHeight()));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            p.setStrokeWidth(3);
            p.setColor(Color.GREEN);
            double x = (int) armstrong.getWidthStartLength();
            canvas.drawLine(getCoefWd(x), 0, getCoefWd(x), getCoefHg(armstrong.getHeight()), p);
            for (int i = 0; i < (armstrong.getCountHorisontal60()); i++) {
                x += 60;
                canvas.drawLine(getCoefWd(x), 0, getCoefWd(x), getCoefHg(armstrong.getHeight()), p);
            }
            p.setStrokeWidth(3);
            p.setColor(Color.BLUE);
            double y = (int) armstrong.getHeightStartLength();
            canvas.drawLine(0, getCoefHg(y), getCoefWd(armstrong.getWidth()), getCoefHg(y), p);
            for (int i = 0; i < (armstrong.getCountVertical120() - 1); i++) {
                y += 60;
                canvas.drawLine(0, getCoefHg(y), getCoefWd(armstrong.getWidth()), getCoefHg(y), p);
            }
            p.setColor(Color.RED);
            p.setStrokeWidth(5);
            double z = (int) (armstrong.getWidthStartLength() + 60);
            canvas.drawLine(getCoefWd(z), 0, getCoefWd(z), getCoefHg(armstrong.getHeight()), p);
            for (int i = 0; i < (armstrong.getOriginCount() - 1); i++) {
                if ((armstrong.getWidth() - z) < 120) {
                    break;
                }
                z += 120;
                if ((armstrong.getWidth() - z) < 60) {
                    z -= 60;
                    canvas.drawLine(getCoefWd(z), 0, getCoefWd(z), getCoefHg(armstrong.getHeight()), p);
                    break;
                }
                canvas.drawLine(getCoefWd(z), 0, getCoefWd(z), getCoefHg(armstrong.getHeight()), p);
            }
            p.setColor(Color.DKGRAY);
            p.setStrokeWidth(4);
            canvas.drawLine(0, 0, getCoefWd(armstrong.getWidth()), 0, p);
            canvas.drawLine(getCoefWd(armstrong.getWidth()), 0, getCoefWd(armstrong.getWidth()), getCoefHg(armstrong.getHeight()), p);
            canvas.drawLine(getCoefWd(armstrong.getWidth()), getCoefHg(armstrong.getHeight()), 0, getCoefHg(armstrong.getHeight()), p);
            canvas.drawLine(0, getCoefHg(armstrong.getHeight()), 0, 0, p);
        }
    }
}
