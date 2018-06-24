package com.postnikov.epam.rentbike.service;

import java.util.List;
import java.util.Map;

import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.exception.ServiceException;

/**
 * @author Sergey Postnikov
 *
 */
public interface BikeService {
	
	/**
	 * Validates incoming parameters and adds bike.
	 * 
	 * @param requestParameters - all parameters from request
	 * @return bike.
	 * @throws ServiceException if any exception occurred.
	 */
	Bike addBike(Map<String, String> requestParameters) throws ServiceException;

	/**
	 * Validates incoming parameters and adds brand.
	 * 
	 * @param brandName - brand name.
	 * @throws ServiceException if any exception occurred.
	 */
	void addBrand(String brandName) throws ServiceException;

	/**
	 * Validates incoming parameters and adds bike type.
	 * 
	 * @param bikeType - bike type.
	 * @throws ServiceException if any exception occurred.
	 */
	void addBikeType(String bikeType) throws ServiceException;
		
	/**
	 * Validates incoming parameters and adds bike products.
	 * 
	 * @param requestParameters - all parameters from request.
	 * @return bikeProductList
	 * @throws ServiceException if any exception occurred.
	 */
	List<BikeProduct> addBikeProduct(Map<String, String> requestParameters) throws ServiceException;
	
	/**
	 * Returns all bike types.
	 * 
	 * @return list with bike types.
	 * @throws ServiceException if any exception occurred.
	 */
	List<BikeType> takeAllBikeType() throws ServiceException;

	/**
	 * Returns all brands.
	 * 
	 * @return list with brands.
	 * @throws ServiceException if any exception occurred.
	 */
	List<Brand> takeAllBrand() throws ServiceException;
	
	/**
	 * Validates incoming parameters and looks for a bike according to the received parameters.
	 * 
	 * @param requestParameters - all parameters from request.
	 * @param pageInfo - object with pagination parameters.
	 * @return bikeList.
	 * @throws ServiceException if any exception occurred.
	 */
	List<Bike> findBike(Map<String, String> requestParameters, PageInfo pageInfo) throws ServiceException;
	
	/**
	 * Validates incoming parameter and takes bike by id.
	 * 
	 * @param bikeId - as a String.
	 * @return bike object.
	 * @throws ServiceException if any exception occurred.
	 */
	Bike takeBikeByID(String bikeId) throws ServiceException;
	
	/**
	 * Validates incoming parameters and looks for a bike products according to the received parameters.
	 * 
	 * @param requestParameters - all parameters from request.
	 * @param pageInfo - object with pagination parameters.
	 * @return bike product list.
	 * @throws ServiceException if any exception occurred.
	 */
	List<BikeProduct> findBikeProduct(Map<String, String> requestParameters, PageInfo pageInfo) throws ServiceException;

	/**
	 * Validates incoming parameter and gets bike product by id.
	 * 
	 * @param bikeId as a String.
	 * @return bikeProduct object.
	 * @throws ServiceException if any exception occurred.
	 */
	BikeProduct takeBikeProductById(String bikeId) throws ServiceException;
	
	/**
	 * Validates incoming parameters and updates bike.
	 * 
	 * @param requestParameters - all parameters from request.
	 * @return bike object.
	 * @throws ServiceException if any exception occurred.
	 */
	Bike updateBike(Map<String, String> requestParameters) throws ServiceException;
	
}
