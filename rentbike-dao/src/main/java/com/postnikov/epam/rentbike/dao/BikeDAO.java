package com.postnikov.epam.rentbike.dao;

import java.util.List;

import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.BikeProductState;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.exception.DAOException;

public interface BikeDAO {

	/**
	 * Add new Bike to DB.
	 * 
	 * @param bike
	 *            BIke object that should be added to database.
	 * @return bike object with id.
	 * @throws DAOException
	 *             if occurred SQL exception. If bike with such combination of the
	 *             brand and model already exist exception message will be -
	 *             bikeDublicateError.
	 */
	Bike addBike(Bike bike) throws DAOException;

	/**
	 * Add Brand to DB.
	 * 
	 * @param brand
	 *            Brand object that should be added to database.
	 * @throws DAOException
	 *             if occurred SQL exception.
	 */
	void addBrand(Brand brand) throws DAOException;

	/**
	 * Add BikeType to DB.
	 * 
	 * @param bikeType
	 *            BikeType object that should be added to DB.
	 * @throws DAOException
	 *             if occurred SQL exception.
	 */
	void addBikeType(BikeType bikeType) throws DAOException;

	/**
	 * Add BikeProducts to DB. Add DB auto generated ID to every BikeProduct if they
	 * was added.
	 * 
	 * @param bikeProductList.
	 * @return bikeProductList with id.
	 * @throws DAOException
	 *             if occurred SQL exception.
	 */
	List<BikeProduct> addBikeProduct(List<BikeProduct> bikeProductList) throws DAOException;

	/**
	 * Get All BikeType from DB
	 * 
	 * @return List<BikeType> bikeTypeList
	 * @throws DAOException
	 *             if occurred SQL exception
	 */
	List<BikeType> takeAllBikeType() throws DAOException;

	/**
	 * Get all Brands from DB
	 * 
	 * @return List<Brand> brand
	 * @throws DAOException
	 *             if occurred SQL exception
	 */
	List<Brand> takeAllBrand() throws DAOException;

	/**
	 * Returns bike object if it was found in DB.
	 * 
	 * @param bikeId
	 * @throws DAOException
	 *             if occurred SQL exception
	 */
	Bike takeBikeById(long bikeId) throws DAOException;

	/**
	 * Looks for a bikes from the received parameters.
	 * 
	 * @param brandId
	 * @param bikeTypeId
	 * @param model
	 * @param minSpeedCount
	 * @param maxSpeedCount
	 * @param pageInfo
	 *            of the PageInfo with parameters for the LIMIT operator
	 * @throws DAOException
	 *             if occurred SQL exception
	 */
	List<Bike> findBike(long brandId, long bikeTypeId, String model, int minSpeedCount, int maxSpeedCount,
			PageInfo pageInfo) throws DAOException;

	/**
	 * Looks for a bike products according to the received parameters.
	 * 
	 * @param parkingId.
	 * @param brandId.
	 * @param bikeTypeId.
	 * @param model.
	 * @param state.
	 * @param pageInfo
	 *            of the PageInfo with parameters for the LIMIT operator.
	 * @throws DAOException
	 *             if occurred SQL exception.
	 */
	List<BikeProduct> findBikeProduct(long parkingId, long brandId, long bikeTypeId, String model,
			BikeProductState state, PageInfo pageInfo) throws DAOException;

	/**
	 * Looks for bike product by received id.
	 * 
	 * @param bikeId.
	 * @return BikeProduct object.
	 * @throws DAOException
	 *             if occurred SQL exception.
	 */
	BikeProduct takeBikeProductById(long bikeId) throws DAOException;

	/**
	 * Updates bike.
	 * 
	 * @param bike.
	 * @return updated bike object.
	 * @throws DAOException
	 *             if occurred SQL exception.
	 */
	Bike updateBike(Bike bike) throws DAOException;

}
