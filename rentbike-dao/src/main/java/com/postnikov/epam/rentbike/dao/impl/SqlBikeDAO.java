package com.postnikov.epam.rentbike.dao.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.postnikov.epam.rentbike.connection.ConnectionPool;
import com.postnikov.epam.rentbike.connection.WrapperConnection;
import com.postnikov.epam.rentbike.dao.BikeDAO;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.BikeProductState;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.DAOException;

public class SqlBikeDAO implements BikeDAO {

	public final static int PARAMETER_1 = 1;

	private final static String TAKE_ALL_BIKE_TYPE = "SELECT id, type FROM biketype";

	private final static String TAKE_ALL_BRAND = "SELECT id, brand FROM brands";

	private final static String ADD_BIKE_AB = "INSERT INTO bikes (brandId, model, wheelSize, speedCount, bikeTypeId, picture) VALUES (?, ?, ?, ?, ?, ?)";
	private final static int BRAND_ID_AB = 1; //AB - Add Bike
	private final static int MODEL_AB = 2;
	private final static int WHEEL_SIZE_AB = 3;
	private final static int SPEED_COUNT_AB = 4;
	private final static int BIKE_TYPE_ID_AB = 5;
	private final static int PICTURE_AB = 6;

	private final static String ADD_BRAND = "INSERT INTO brands (brand) VALUES (?)";

	private final static String ADD_BIKE_TYPE_ABT = "INSERT INTO biketype (type) VALUES (?)";
	private final static int BIKE_TYPE_ABT = 1;  //ABT - Add Bike Type

	private static final String FIND_BIKE_FB = "{call findBikeProcedure(?,?,?,?,?,?,?)}";
	private static final int BRAND_ID_FB = 1; // FB - Find Bike
	private static final int MODEL_FB = 2;
	private static final int BIKE_TYPE_ID_FB = 3;
	private static final int SPEED_COUNT_MIN_FB = 4;
	private static final int SPEED_COUNT_MAX_FB = 5;
	private static final int START_ID_FB = 6;
	private static final int COUNT_ELEMENT_ON_PAGE_FB = 7;

	private final static String FIND_BIKE_BY_ID = "SELECT bk.id, bk.brandId, br.brand, bk.model, bk.wheelSize, bk.speedCount, bk.picture, bk.bikeTypeId, bt.type FROM bikes bk LEFT JOIN brands br ON bk.brandId = br.id LEFT JOIN biketype bt ON bk.bikeTypeId = bt.id WHERE bk.id = ?";
	private final static int ID_PARAM = 1;

	private final static String ADD_BIKE_PRODUCT_ABP = "INSERT INTO bikeproduct (bikeId, purchaseDate, value, rentPrice, parkingId) VALUES (?, ?, ?, ?, ?)";
	private final static int BIKE_ID_ABP = 1;  //ABP - Add Bike Product
	private final static int PURCHASE_DATE_ABP = 2;
	private final static int VALUE_ABP = 3;
	private final static int RENT_PRICE_ABP = 4;
	private final static int PARKING_ID_ABP = 5;

	private final static String FIND_BIKE_PRODUCT_FBP = "{call findBikeProductProcedure(?,?,?,?,?,?,?)}";
	private static final int PRODUCT_STATE_FBP = 1; // FBP - Find Bike Product
	private static final int PARKING_ID_FBP = 2;
	private static final int BRAND_ID_FBP = 3;
	private static final int BIKE_TYPE_ID_FBP = 4;
	private static final int MODEL_FBP = 5;
	private static final int START_ID_FBP = 6;
	private static final int COUNT_ELEMENT_ON_PAGE_FBP = 7;

	private final static String FIND_BIKE_PRODUCT_BY_ID = "SELECT bp.id, bk.id AS bikeId, bk.brandId, br.brand, bk.model, bk.wheelSize, bk.speedCount, bk.picture, bk.bikeTypeId, bt.type, pk.id AS parkingId, pk.address, bp.value, bp.rentPrice, bp.state FROM bikeproduct bp LEFT JOIN bikes bk ON bp.bikeId=bk.id LEFT JOIN brands br ON bk.brandId = br.id LEFT JOIN bikeType bt ON bk.bikeTypeId = bt.id LEFT JOIN parkings pk ON bp.parkingId=pk.id WHERE bp.id = ?";

	private final static String UPDATE_BIKE_UB = "UPDATE bikes SET brandId = ?, model = ?, wheelSize = ?, speedCount = ?, bikeTypeId = ?, picture = ? WHERE id = ?";
	private final static int BIKE_ID_UB = 7; //UB - Update Bike

	private final static String ID = "id";
	private final static String TYPE = "type";
	private final static String BRAND = "brand";
	private final static String BRAND_ID = "brandId";
	private final static String MODEL = "model";
	private final static String WHEELSIZE = "wheelSize";
	private final static String SPEEDCOUNT = "speedCount";
	private final static String PICTURE = "picture";
	private final static String BIKE_TYPE_ID = "biketypeId";
	private final static String VALUE = "value";
	private final static String RENT_PRICE = "rentPrice";
	private final static String PARKING_ID = "parkingId";
	private final static String ADDRESS = "address";
	private final static String STATE = "state";
	private final static String BIKE_ID = "bikeId";

	@Override
	public List<BikeType> takeAllBikeType() throws DAOException {

		List<BikeType> bikeTypeList = new ArrayList<>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		Statement statement = wrapperConnection.getStatement();

		try {
			ResultSet resultSet = statement.executeQuery(TAKE_ALL_BIKE_TYPE);

			while (resultSet.next()) {
				BikeType bikeType = new BikeType();
				bikeType.setId(resultSet.getInt(ID));
				bikeType.setBikeType(resultSet.getString(TYPE));
				bikeTypeList.add(bikeType);
			}

		} catch (SQLException e) {
			throw new DAOException("Get bike type error", e);
		} finally {
			wrapperConnection.closeStatement(statement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

		return bikeTypeList;
	}

	@Override
	public List<Brand> takeAllBrand() throws DAOException {

		Brand brand;
		List<Brand> brandList = new ArrayList<>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		Statement statement = wrapperConnection.getStatement();

		try {
			ResultSet resultSet = statement.executeQuery(TAKE_ALL_BRAND);

			while (resultSet.next()) {
				brand = new Brand();
				brand.setId(resultSet.getInt(ID));
				brand.setBrand(resultSet.getString(BRAND));
				brandList.add(brand);
			}

		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while getting all brands from the DB", e);
		} finally {
			wrapperConnection.closeStatement(statement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

		return brandList;
	}

	@Override
	public Bike addBike(Bike bike) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(ADD_BIKE_AB);

		try {
			preparedStatement.setLong(BRAND_ID_AB, bike.getBrand().getId());
			preparedStatement.setString(MODEL_AB, bike.getModel());
			preparedStatement.setInt(WHEEL_SIZE_AB, bike.getWheelSize());
			preparedStatement.setInt(SPEED_COUNT_AB, bike.getSpeedCount());
			preparedStatement.setLong(BIKE_TYPE_ID_AB, bike.getBikeType().getId());
			preparedStatement.setString(PICTURE_AB, bike.getPicturePath());

			preparedStatement.executeUpdate();

			long lastId = ((com.mysql.jdbc.PreparedStatement) preparedStatement).getLastInsertID();
			bike.setId(lastId);

			return bike;

		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.BIKE_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("Add bike error", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

	}

	@Override
	public void addBrand(Brand brand) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(ADD_BRAND);

		try {
			preparedStatement.setString(BRAND_ID_AB, brand.getBrand());
			preparedStatement.executeUpdate();

		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.BRAND_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while adding brand to the DB", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public void addBikeType(BikeType bikeType) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(ADD_BIKE_TYPE_ABT);

		try {
			preparedStatement.setString(BIKE_TYPE_ABT, bikeType.getBikeType());
			preparedStatement.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.BIKE_TYPE_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("Exception occured during add bike type to DB", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public List<Bike> findBike(long brandId, long bikeTypeId, String model, int minSpeedCount, int maxSpeedCount,
			PageInfo pageInfo) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		CallableStatement callableStatement = wrapperConnection.getCallableStatement(FIND_BIKE_FB);

		try {
			callableStatement.setLong(BRAND_ID_FB, brandId);
			callableStatement.setString(MODEL_FB, model);
			callableStatement.setLong(BIKE_TYPE_ID_FB, bikeTypeId);
			callableStatement.setInt(SPEED_COUNT_MIN_FB, minSpeedCount);
			callableStatement.setInt(SPEED_COUNT_MAX_FB, maxSpeedCount);
			callableStatement.setLong(START_ID_FB, pageInfo.getLastPagePoint());
			callableStatement.setInt(COUNT_ELEMENT_ON_PAGE_FB, pageInfo.getDefaultElementOnPage());

			ResultSet resultSet = callableStatement.executeQuery();

			List<Bike> bikeList = new ArrayList<>();

			while (resultSet.next()) {
				Bike bike = new Bike();
				bike.setId(resultSet.getLong(ID));

				Brand brand = new Brand();
				brand.setId(resultSet.getInt(BRAND_ID));
				brand.setBrand(resultSet.getString(BRAND));
				bike.setBrand(brand);

				bike.setModel(resultSet.getString(MODEL));

				bike.setWheelSize(resultSet.getInt(WHEELSIZE));
				bike.setSpeedCount(resultSet.getInt(SPEEDCOUNT));
				bike.setPicturePath(resultSet.getString(PICTURE));

				BikeType bikeType = new BikeType();
				bikeType.setId(resultSet.getInt(BIKE_TYPE_ID));
				bikeType.setBikeType(resultSet.getString(TYPE));
				bike.setBikeType(bikeType);

				bikeList.add(bike);
			}

			return bikeList;

		} catch (SQLException e) {
			throw new DAOException("Exception occured during find bike in DB", e);
		} finally {
			wrapperConnection.closeStatement(callableStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public Bike takeBikeById(long bikeId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(FIND_BIKE_BY_ID);

		try {
			preparedStatement.setLong(ID_PARAM, bikeId);

			ResultSet resultSet = preparedStatement.executeQuery();

			Bike bike = null;
			if (resultSet.next()) {
				bike = new Bike();
				bike.setId(resultSet.getLong(ID));

				Brand brand = new Brand();
				brand.setId(resultSet.getInt(BRAND_ID));
				brand.setBrand(resultSet.getString(BRAND));
				bike.setBrand(brand);

				bike.setModel(resultSet.getString(MODEL));

				bike.setWheelSize(resultSet.getInt(WHEELSIZE));
				bike.setSpeedCount(resultSet.getInt(SPEEDCOUNT));
				bike.setPicturePath(resultSet.getString(PICTURE));

				BikeType bikeType = new BikeType();
				bikeType.setId(resultSet.getInt(BIKE_TYPE_ID));
				bikeType.setBikeType(resultSet.getString(TYPE));
				bike.setBikeType(bikeType);
			}

			return bike;
		} catch (SQLException e) {
			throw new DAOException("Get bike by id error", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public List<BikeProduct> addBikeProduct(List<BikeProduct> bikeProductList) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(ADD_BIKE_PRODUCT_ABP);

		try {
			for (BikeProduct bikeProduct : bikeProductList) {
				preparedStatement.setLong(BIKE_ID_ABP, bikeProduct.getBike().getId());
				preparedStatement.setString(PURCHASE_DATE_ABP, bikeProduct.getRegistrationDate());
				preparedStatement.setBigDecimal(VALUE_ABP, bikeProduct.getValue());
				preparedStatement.setBigDecimal(RENT_PRICE_ABP, bikeProduct.getRentPrice());
				preparedStatement.setLong(PARKING_ID_ABP, bikeProduct.getParking().getId());
				preparedStatement.executeUpdate();

				long lastId = ((com.mysql.jdbc.PreparedStatement) preparedStatement).getLastInsertID();
				bikeProduct.setId(lastId);
			}
			return bikeProductList;
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while adding bike product to the DB", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

	}

	@Override
	public List<BikeProduct> findBikeProduct(long parkingId, long brandId, long bikeTypeId, String model,
			BikeProductState state, PageInfo pageInfo) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		CallableStatement callableStatement = wrapperConnection.getCallableStatement(FIND_BIKE_PRODUCT_FBP);

		try {
			callableStatement.setString(PRODUCT_STATE_FBP, state.toString());
			callableStatement.setLong(PARKING_ID_FBP, parkingId);
			callableStatement.setLong(BRAND_ID_FBP, brandId);
			callableStatement.setLong(BIKE_TYPE_ID_FBP, bikeTypeId);
			callableStatement.setString(MODEL_FBP, model);
			callableStatement.setLong(START_ID_FBP, pageInfo.getLastPagePoint());
			callableStatement.setInt(COUNT_ELEMENT_ON_PAGE_FBP, pageInfo.getDefaultElementOnPage());

			ResultSet resultSet = callableStatement.executeQuery();

			List<BikeProduct> bikeProductList = new ArrayList<>();
			
			while (resultSet.next()) {
				BikeProduct bikeProduct = new BikeProduct();

				bikeProduct.setId(resultSet.getLong(ID));

				Bike bike = new Bike();
				bike.setId(resultSet.getLong(BIKE_ID));

				Brand brand = new Brand();
				brand.setId(resultSet.getLong(BRAND_ID));
				brand.setBrand(resultSet.getString(BRAND));
				bike.setBrand(brand);

				bike.setModel(resultSet.getString(MODEL));

				bike.setWheelSize(resultSet.getInt(WHEELSIZE));
				bike.setSpeedCount(resultSet.getInt(SPEEDCOUNT));
				bike.setPicturePath(resultSet.getString(PICTURE));

				BikeType bikeType = new BikeType();
				bikeType.setId(resultSet.getInt(BIKE_TYPE_ID));
				bikeType.setBikeType(resultSet.getString(TYPE));
				bike.setBikeType(bikeType);

				bikeProduct.setBike(bike);
				bikeProduct.setValue(resultSet.getBigDecimal(VALUE));
				bikeProduct.setRentPrice(resultSet.getBigDecimal(RENT_PRICE));

				Parking parking = new Parking();
				parking.setId(resultSet.getLong(PARKING_ID));
				parking.setAddress(resultSet.getString(ADDRESS));
				bikeProduct.setParking(parking);

				bikeProductList.add(bikeProduct);
			}
			
			return bikeProductList;

		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while finding bikeProduct", e);
		} finally {
			wrapperConnection.closeStatement(callableStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

	}

	@Override
	public BikeProduct takeBikeProductById(long bikeId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(FIND_BIKE_PRODUCT_BY_ID);

		try {
			preparedStatement.setLong(PARAMETER_1, bikeId);
			ResultSet resultSet = preparedStatement.executeQuery();

			BikeProduct bikeProduct = null;
			
			if (resultSet.next()) {
				bikeProduct = new BikeProduct();
				bikeProduct.setId(resultSet.getLong(ID));

				Bike bike = new Bike();
				bike.setId(resultSet.getLong(BIKE_ID));

				Brand brand = new Brand();
				brand.setId(resultSet.getLong(BRAND_ID));
				brand.setBrand(resultSet.getString(BRAND));
				bike.setBrand(brand);

				bike.setModel(resultSet.getString(MODEL));

				bike.setWheelSize(resultSet.getInt(WHEELSIZE));
				bike.setSpeedCount(resultSet.getInt(SPEEDCOUNT));
				bike.setPicturePath(resultSet.getString(PICTURE));

				BikeType bikeType = new BikeType();
				bikeType.setId(resultSet.getInt(BIKE_TYPE_ID));
				bikeType.setBikeType(resultSet.getString(TYPE));
				bike.setBikeType(bikeType);

				bikeProduct.setBike(bike);
				bikeProduct.setValue(resultSet.getBigDecimal(VALUE));
				bikeProduct.setRentPrice(resultSet.getBigDecimal(RENT_PRICE));
				bikeProduct.setState(BikeProductState.valueOf(resultSet.getString(STATE).toUpperCase()));

				Parking parking = new Parking();
				parking.setId(resultSet.getLong(PARKING_ID));
				parking.setAddress(resultSet.getString(ADDRESS));
				bikeProduct.setParking(parking);

			}
			
			return bikeProduct;
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while getting bike product by id", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public Bike updateBike(Bike bike) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(UPDATE_BIKE_UB);

		try {
			preparedStatement.setLong(BRAND_ID_AB, bike.getBrand().getId());
			preparedStatement.setString(MODEL_AB, bike.getModel());
			preparedStatement.setInt(WHEEL_SIZE_AB, bike.getWheelSize());
			preparedStatement.setInt(SPEED_COUNT_AB, bike.getSpeedCount());
			preparedStatement.setLong(BIKE_TYPE_ID_AB, bike.getBikeType().getId());
			preparedStatement.setString(PICTURE_AB, bike.getPicturePath());
			preparedStatement.setLong(BIKE_ID_UB, bike.getId());

			preparedStatement.executeUpdate();

			return bike;

		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.BIKE_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while updating bike", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

	}

}
