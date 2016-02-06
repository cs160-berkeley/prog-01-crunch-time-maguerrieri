package com.guerrieri.mario.crunchtime;

/**
 * Created by marioguerrieri on 2/4/16.
 */

public class Exercise {
    public final String name;
    public final String beforePast;
    public final String beforeFuture;
    public final int afterID;
    public final double cal;

    public Exercise(String name, String beforePast, String beforeFuture, int afterID, double cal) {
        this.name = name;
        this.beforePast = beforePast;
        this.beforeFuture = beforeFuture;
        this.afterID = afterID;
        this.cal = cal;
    }

    @Override
    public String toString() {
        return String.format("%sing burns %f calories per rep.", this.name, this.cal);
    }

    public int getReps(double calories) {
        return (int) (calories / this.cal);
    }
}
