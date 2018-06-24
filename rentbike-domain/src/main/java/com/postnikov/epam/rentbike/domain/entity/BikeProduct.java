package com.postnikov.epam.rentbike.domain.entity;

import java.math.BigDecimal;

public class BikeProduct extends AbstractEntity {

	private static final long serialVersionUID = 1247825910790250970L;

	private Bike bike;
	private Parking parking;
	private BigDecimal value;
	private BigDecimal rentPrice;
	private BikeProductState state;
	private String stateDescription;
	private String registrationDate;

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}

	public BikeProductState getState() {
		return state;
	}

	public void setState(BikeProductState state) {
		this.state = state;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bike == null) ? 0 : bike.hashCode());
		result = prime * result + ((parking == null) ? 0 : parking.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((rentPrice == null) ? 0 : rentPrice.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((stateDescription == null) ? 0 : stateDescription.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		BikeProduct other = (BikeProduct) obj;
		if (bike == null) {
			if (other.bike != null)
				return false;
		} else if (!bike.equals(other.bike))
			return false;
		if (parking == null) {
			if (other.parking != null)
				return false;
		} else if (!parking.equals(other.parking))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (rentPrice == null) {
			if (other.rentPrice != null)
				return false;
		} else if (!rentPrice.equals(other.rentPrice))
			return false;
		if (state != other.state)
			return false;
		if (stateDescription == null) {
			if (other.stateDescription != null)
				return false;
		} else if (!stateDescription.equals(other.stateDescription))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BikeProduct [bike=" + bike + ", parking=" + parking + ", value=" + value + ", rentPrice=" + rentPrice
				+ ", state=" + state + ", stateDescription=" + stateDescription + ", registrationDate="
				+ registrationDate + "]";
	}

}
