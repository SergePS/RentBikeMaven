package com.postnikov.epam.rentbike.domain.entity;

import java.math.BigDecimal;

public class BikeOrder extends AbstractEntity {

	static final long serialVersionUID = 8413856522109838514L;

	private User user;
	private long bikeProductId;
	private Bike bike;
	private Parking startParking;
	private String startTime;
	private BigDecimal bikeValue;
	private BigDecimal rentPrice;
	private Parking finishParking;
	private String finishTime;
	private BigDecimal payment;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getBikeProductId() {
		return bikeProductId;
	}

	public void setBikeProductId(long bikeProductId) {
		this.bikeProductId = bikeProductId;
	}

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	public Parking getStartParking() {
		return startParking;
	}

	public void setStartParking(Parking startParking) {
		this.startParking = startParking;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getBikeValue() {
		return bikeValue;
	}

	public void setBikeValue(BigDecimal bikeValue) {
		this.bikeValue = bikeValue;
	}

	public BigDecimal getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}

	public Parking getFinishParking() {
		return finishParking;
	}

	public void setFinishParking(Parking finishParking) {
		this.finishParking = finishParking;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bike == null) ? 0 : bike.hashCode());
		result = prime * result + (int) (bikeProductId ^ (bikeProductId >>> 32));
		result = prime * result + ((bikeValue == null) ? 0 : bikeValue.hashCode());
		result = prime * result + ((finishParking == null) ? 0 : finishParking.hashCode());
		result = prime * result + ((finishTime == null) ? 0 : finishTime.hashCode());
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((rentPrice == null) ? 0 : rentPrice.hashCode());
		result = prime * result + ((startParking == null) ? 0 : startParking.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		BikeOrder other = (BikeOrder) obj;
		if (bike == null) {
			if (other.bike != null)
				return false;
		} else if (!bike.equals(other.bike))
			return false;
		if (bikeProductId != other.bikeProductId)
			return false;
		if (bikeValue == null) {
			if (other.bikeValue != null)
				return false;
		} else if (!bikeValue.equals(other.bikeValue))
			return false;
		if (finishParking == null) {
			if (other.finishParking != null)
				return false;
		} else if (!finishParking.equals(other.finishParking))
			return false;
		if (finishTime == null) {
			if (other.finishTime != null)
				return false;
		} else if (!finishTime.equals(other.finishTime))
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (rentPrice == null) {
			if (other.rentPrice != null)
				return false;
		} else if (!rentPrice.equals(other.rentPrice))
			return false;
		if (startParking == null) {
			if (other.startParking != null)
				return false;
		} else if (!startParking.equals(other.startParking))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BikeOrder [user=" + user + ", bikeProductId=" + bikeProductId + ", bike=" + bike + ", startParking="
				+ startParking + ", startTime=" + startTime + ", bikeValue=" + bikeValue + ", rentPrice=" + rentPrice
				+ ", finishParking=" + finishParking + ", finishTime=" + finishTime + ", payment=" + payment + "]";
	}

}
