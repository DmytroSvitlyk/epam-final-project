package com.delivery.delivery.model;

public class Cargo {

    private double weight;
    private double length;
    private double width;
    private double height;
	private double volume;

	public Cargo(double weight, double volume) {
		this(weight, -1, -1, -1, volume);
	}

	public Cargo(double weight, double length, double width, double height) {
        this(weight, length, weight, height, -1);
    }
	
    public Cargo(double weight, double length, double width, double height, double volume) {
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
		this.volume = volume;
    }

    public Cargo() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

	public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
		if(volume != -1) {
			return volume;
		}
        return length*weight*height;
    }

    public double getCalcWeight() {
        return Math.max(weight, getVolume()/4000);
    }

}
