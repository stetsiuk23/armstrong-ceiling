package com.example.myarmstrongapplication.ceiling.armstrong;

import com.example.myarmstrongapplication.ceiling.Ceiling;
import com.example.myarmstrongapplication.ceiling.armstrong.data.ArmstrongCeilingData;

public class Armstrong extends Ceiling {
    private static final int GUIDE_STICK_H = ArmstrongCeilingData.SticksData.GUIDE_STICK_HEIGHT;
    private static final int WALL_STICK_H = ArmstrongCeilingData.SticksData.WALL_STICK_HEIGHT;
    private static final int INTERM_STICK_120_H = ArmstrongCeilingData.SticksData.INTERMIDIATE_STICK_HEIGHT_120;
    private static final int INTERM_STICK_60_H = ArmstrongCeilingData.SticksData.INTERMIDIATE_STICK_HEIGHT_60;
    private static final int LENG_BETWEEN_HOOKS = 100;
    private static final int LENGTH_BETWEEN_SCREWS = 50;

    private final int hooksCount;
    private final int screwsCount;
    private final double widthStartLength;
    private double heightStartLength;

    public int getHooksCount() {
        return hooksCount;
    }

    public int getScrewsCount() {
        return screwsCount;
    }

    public double getWidthStartLength() {
        return widthStartLength;
    }

    public double getHeightStartLength() {
        return heightStartLength;
    }

    public Armstrong(double width, double height) {
        super(width, height);
        hooksCount = (int) getGuideStLength() / LENG_BETWEEN_HOOKS;
        screwsCount = (int) ((getWallStLength() / LENGTH_BETWEEN_SCREWS) + hooksCount);
        widthStartLength = (getWidth() % INTERM_STICK_60_H + 60) / 2;
        heightStartLength = (getHeight() % INTERM_STICK_60_H + 60) / 2;
        if (heightStartLength > 50) {
            heightStartLength -= 30;
        }
    }

    public double getOriginCount() {
        int res = (int) Math.ceil(getWidth() / INTERM_STICK_120_H);
        if (getWidth() % INTERM_STICK_120_H == 0) {
            res += 1;
        }
        return res;
    }

    public double getGuideStLength() {
        return Math.ceil(getHeight() * getOriginCount());
    }

    public double getGuideStCount() {
        return Math.ceil(getGuideStLength() / GUIDE_STICK_H);
    }

    public double getWallStLength() {
        return super.getPerimeter();
    }

    public double getWallStCount() {
        return Math.ceil(getWallStLength() / WALL_STICK_H);
    }

    public double getCountVertical120() {
        int val = (int) getHeight() / 60;
        if ((int) getHeight() % 60 > 40) {
            val = val + 1;
        }
        return val;
    }

    public double getCountHorisontal60() {
        double y = (getOriginCount() + 1) * 2;
        if (heightStartLength <= 30) {
            y = (getOriginCount() + 1);
        }
        return y;
    }

    public double getIntermSt120Count() {
        int x = 1;
        if (widthStartLength > 60) {
            x = 2;
        }
        return (getCountVertical120() * (getOriginCount() - 1)) + (getCountVertical120() * x);
    }

    public double getIntermSt60Count() {
        return ((getOriginCount() + 1) * ((int) (getCountVertical120()) - 1)) + getCountHorisontal60();
    }
}
