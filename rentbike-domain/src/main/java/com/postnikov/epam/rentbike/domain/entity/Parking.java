package com.postnikov.epam.rentbike.domain.entity;

public class Parking extends AbstractEntity{

	private static final long serialVersionUID = -7697884665372871007L;
	
		private String address;
		private int capacity;
		private int bikeCount;
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
		
		public int getBikeCount() {
			return bikeCount;
		}
		public void setBikeCount(int bikeCount) {
			this.bikeCount = bikeCount;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((address == null) ? 0 : address.hashCode());
			result = prime * result + bikeCount;
			result = prime * result + capacity;
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
			Parking other = (Parking) obj;
			if (address == null) {
				if (other.address != null)
					return false;
			} else if (!address.equals(other.address))
				return false;
			if (bikeCount != other.bikeCount)
				return false;
			if (capacity != other.capacity)
				return false;
			return true;
		}
		
}
