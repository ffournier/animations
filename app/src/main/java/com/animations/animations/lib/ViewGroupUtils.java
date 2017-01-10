package com.animations.animations.lib;


import android.view.View;
import android.view.ViewGroup;

import com.animations.animations.lib.view.ParticleView;


/**
 * Class Utils to manage View to add, replace and remove
 */
public class ViewGroupUtils {

    /**
     * Get Parent
     * @param view
     * @return
     */
    public static ViewGroup getParent(View view) {
        if (view != null)
            return (ViewGroup)view.getParent();
        return null;
    }

    /**
     * Remove View
     * @param view
     */
    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    /**
     * Replace View
     * @param currentView
     * @param newView
     */
    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);

        newView.setLayoutParams(currentView.getLayoutParams());

        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }

    /**
     * Add View
     * @param parent
     * @param newView
     */
    public static void addView(ViewGroup parent, View newView) {
        if(parent == null) {
            return;
        }
        removeView(newView);
        parent.addView(newView);
    }

    /**
     * Test if parent has view
     * @param parent
     * @param view
     * @return
     */
    public static boolean hasView(ViewGroup parent, View view) {
        if(parent == null) {
            return false;
        }

        return parent.indexOfChild(view) != -1;
    }
}