package com.postnikov.epam.rentbike.domain.entity;

public class BikeType extends AbstractEntity {

	private static final long serialVersionUID = 5244979275339804966L;

	private String bikeType;

	public String getBikeType() {
		return bikeType;
	}

	public void setBikeType(String bikeType) {
		this.bikeType = bikeType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bikeType == null) ? 0 : bikeType.hashCode());
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
		BikeType other = (BikeType) obj;
		if (bikeType == null) {
			if (other.bikeType != null)
				return false;
		} else if (!bikeType.equals(other.bikeType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BikeType [bikeType=" + bikeType + "]";
	}

}
