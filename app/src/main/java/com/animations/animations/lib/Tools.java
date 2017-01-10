package com.animations.animations.lib;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Class Tools
 */
public class Tools {

    /**
     * convert Dip to Pixels
     * @param context
     * @param dipValue
     * @return
     */
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    /**
     * get angle between two Points
     * @param start
     * @param target
     * @return
     */
    public static int getAngle(int[] start, int[] target) {
        float angle = (float) Math.toDegrees(Math.atan2(target[1] - start[1], target[0] - start[0]));

        if(angle < 0){
            angle += 360;
        }

        return (int) angle;
    }

    /**
     * Get angle between two Points
     * @param start
     * @param target
     * @return
     */
    public static int getAngle(Point start, Point target) {
        int[] pstart = new int[2];
        int[] ptarget = new int[2];


        pstart[0] = start.x;
        pstart[1] = start.y;
        ptarget[0] = target.x;
        ptarget[1] = target.y;

        return getAngle(pstart, ptarget);
    }

    /**
     * Get distance between two Points
     * @param start
     * @param target
     * @return
     */
    public static int getDistance(int[] start, int[] target) {
        return (int) Math.hypot(start[0] - target[0], start[1] - target[1]);
    }

    /**
     * Get distance between two Points
     * @param start
     * @param target
     * @return
     */
    public static int getDistance(Point start, Point target) {
        int[] pstart = new int[2];
        int[] ptarget = new int[2];


        pstart[0] = start.x;
        pstart[1] = start.y;
        ptarget[0] = target.x;
        ptarget[1] = target.y;

        return getDistance(pstart, ptarget);
    }


}
