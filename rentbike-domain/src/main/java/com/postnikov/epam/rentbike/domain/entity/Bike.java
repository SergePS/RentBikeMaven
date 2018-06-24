package com.postnikov.epam.rentbike.domain.entity;

public class Bike extends AbstractEntity {

	private static final long serialVersionUID = 7013889430932529992L;

	private Brand brand;
	private String model;
	private int wheelSize;
	private int speedCount;
	private String picturePath;
	private BikeType bikeType;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getWheelSize() {
		return wheelSize;
	}

	public void setWheelSize(int wheelSize) {
		this.wheelSize = wheelSize;
	}

	public int getSpeedCount() {
		return speedCount;
	}

	public void setSpeedCount(int speedCount) {
		this.speedCount = speedCount;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public BikeType getBikeType() {
		return bikeType;
	}

	public void setBikeType(BikeType bikeType) {
		this.bikeType = bikeType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bikeType == null) ? 0 : bikeType.hashCode());
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((picturePath == null) ? 0 : picturePath.hashCode());
		result = prime * result + speedCount;
		result = prime * result + wheelSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bike other = (Bike) obj;
		if (bikeType == null) {
			if (other.bikeType != null)
				return false;
		} else if (!bikeType.equals(other.bikeType))
			return false;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (picturePath == null) {
			if (other.picturePath != null)
				return false;
		} else if (!picturePath.equals(other.picturePath))
			return false;
		if (speedCount != other.speedCount)
			return false;
		if (wheelSize != other.wheelSize)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bike [brand=" + brand + ", model=" + model + ", wheelSize=" + wheelSize + ", speedCount=" + speedCount
				+ ", picturePath=" + picturePath + ", bikeType=" + bikeType + "]";
	}



}
