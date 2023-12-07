package com.example.interstellatstickhero;
public class Pillar implements serializable{
    private int width;
    private int positionX;
    private int bonusLength;
    private int bonusPositionX;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getBonusLength() {
        return bonusLength;
    }

    public void setBonusLength(int bonusLength) {
        this.bonusLength = bonusLength;
    }

    public int getBonusPositionX() {
        return bonusPositionX;
    }

    public void setBonusPositionX(int bonusPositionX) {
        this.bonusPositionX = bonusPositionX;
    }
}
