package com.example.myarmstrongapplication.ceiling.armstrong;

import android.util.Log;

import com.example.myarmstrongapplication.ceiling.Ceiling;
import com.example.myarmstrongapplication.ceiling.armstrong.data.ArmstrongCeilingData;

public class Armstrong extends Ceiling {
    //відомі константи які описують властивості Armstrong стелі
    //Константа довжини і ширини(вори рівні)
    private static final int SQUARE_W_H = ArmstrongCeilingData.SquareData.SQUARE_WIDTH_HEIGHT;
    //Константа довжини направляючого профілю
    private static final int GUIDE_STICK_H =  ArmstrongCeilingData.SticksData.GUIDE_STICK_HEIGHT;
    //Константа довжини пристінного профілю
    private static final int WALL_STICK_H =  ArmstrongCeilingData.SticksData.WALL_STICK_HEIGHT;
    //Константа довжини проміжного профіля довжиною 120см
    private static final int INTERM_STICK_120_H = ArmstrongCeilingData.SticksData.INTERMIDIATE_STICK_HEIGHT_120;
    //Константа довжини проміжного профіля довжино 60см
    private static final int INTERM_STICK_60_H =  ArmstrongCeilingData.SticksData.INTERMIDIATE_STICK_HEIGHT_60;

    private static final int LENG_BETWEEN_HOOKS = 100;
    private static final int LENGTH_BETWEEN_SCREWS = 50;

    //Кількість гаків підтримувачів(Розрахуємо під час створення обєктта з даних width i height)
    private int hooksCount;
    //Загальна кількість шурупів потрібних для кріплення гаків і пристінного профіля
    private int screwsCount;

    public int getHooksCount() {
        return hooksCount;
    }

    public int getScrewsCount() {
        return screwsCount;
    }

    //Константи довжин і ширин почітків
    private double widthStartLength;
    private double heightStartLength;

    public double getWidthStartLength() {
        return widthStartLength;
    }

    public double getHeightStartLength() {
        return heightStartLength;
    }

    //Перевірка на нульове значення при модулі
    private boolean isNullModeleWidth;
    private boolean isNullModeleHeight;

    //Кількість пальць 120
    //Кількість палиць 60
    public Armstrong(double width, double height) {
        super(width, height);
        Log.d("myLog", "insideConsrtuctor(width = "+width+", height = "+height+")");

        hooksCount = (int) getGuideStLength()/LENG_BETWEEN_HOOKS;
        screwsCount = (int) ((getWallStLength()/LENGTH_BETWEEN_SCREWS)+hooksCount);
        Log.d("myLog", "hooksCount = "+hooksCount+", screwCount = "+screwsCount);

        widthStartLength = (getWidth()%INTERM_STICK_60_H+60)/2;
        heightStartLength = (getHeight()%INTERM_STICK_60_H+60)/2;
        if(heightStartLength>50){
            heightStartLength-=30;
        }
        Log.d("myLog", "WidthStartLength = "+widthStartLength+", HeightStartLength = "+heightStartLength);

        isNullModeleWidth = getWidth()%60==0;
        isNullModeleHeight = getHeight()%60==0;
        Log.d("myLog", "isNullModuleWidth = "+isNullModeleWidth+", isNullModuleHeight = "+isNullModeleHeight);
    }

    //Метод визначення кількості проходжень направляючого профіля:
    public double getOriginCount() {
        Log.d("myLog", "Кількість проходів направляючого: "+ Math.ceil(getWidth()/INTERM_STICK_120_H));
        int res = (int)Math.ceil(getWidth()/INTERM_STICK_120_H);
        if(getWidth()%INTERM_STICK_120_H==0){
            res+=1;
        }
        return res;
    }
    //Знаходимо загальну довжину направляючого профіля
    public double getGuideStLength() {
        Log.d("myLog", "Загальна довжина направляючого: "+Math.ceil(getHeight() * getOriginCount()));
        return Math.ceil(getHeight() * getOriginCount());
    }
    //знаходимо загальну кількість направляючиш в шт
    public double getGuideStCount(){
        Log.d("myLog", "Кількість направляючого шт: "+Math.ceil(getGuideStLength()/GUIDE_STICK_H));
        return Math.ceil(getGuideStLength()/GUIDE_STICK_H);
    }

    //знаходимо довжину пристінного профіля
    public double getWallStLength(){
        Log.d("myLog", "Загальна довжина пристінного профіля: "+super.getPerimeter());
        return super.getPerimeter();
    }
    //знаходимо кількість пристінного
    public double getWallStCount(){
        Log.d("myLog", "Кількість пристінного шт:" +Math.ceil(getWallStLength()/WALL_STICK_H));
        return Math.ceil(getWallStLength()/WALL_STICK_H);
    }

    public double getCountVertical120(){
        int val = (int) getHeight()/60;
        if((int) getHeight()%60>40){
            val=val+1;
        }
        return val;
    }

    public double getCountHorisontal60(){
        double y = (getOriginCount()+1)*2;
        if(heightStartLength<=30){
            y=(getOriginCount()+1);
        }
        return y;
    }
    //Знаходження кількості палиць 120

    public double getIntermSt120Count(){
        int x = 1;
        if(widthStartLength>60){
            x=2;
        }
        Log.d("myLog", "x = "+x);
        Log.d("myLog", "Res 120: "+((getCountVertical120()*(getOriginCount()-1))+(getCountVertical120()*x)));
        return (getCountVertical120()*(getOriginCount()-1))+(getCountVertical120()*x);
    }

    //Знаходимо кількість палиць 60
    public double getIntermSt60Count(){
        Log.d("myLog", "Res 60: "+((getOriginCount()+1)*((int) (getHeight()/60)-1))+getCountHorisontal60());
        return ((getOriginCount()+1)*((int) (getCountVertical120())-1))+getCountHorisontal60();
    }
}
