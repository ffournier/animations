package com.animations.animations.lib;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Tools {

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int getAngle(int[] start, int[] target) {
        float angle = (float) Math.toDegrees(Math.atan2(target[1] - start[1], target[0] - start[0]));

        if(angle < 0){
            angle += 360;
        }

        return (int) angle;
    }

    public static int getDistance(int[] start, int[] target) {
        return (int) Math.hypot(start[0] - target[0], start[1] - target[1]);
    }

}
