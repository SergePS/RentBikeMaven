package com.postnikov.epam.rentbike.dao.impl;

import java.math.BigDecimal;
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
import com.postnikov.epam.rentbike.dao.UserDAO;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.Bike;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.BikeType;
import com.postnikov.epam.rentbike.domain.entity.Brand;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.domain.entity.UserRole;
import com.postnikov.epam.rentbike.domain.entity.UserState;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.DAOException;

public class SqlUserDAO implements UserDAO {

	private final static String FIND_USER_BY_LOGIN_PASSW = "SELECT id, login, name, surname, email, DATE_FORMAT(birthday, '%d.%m.%Y') as birthday, DATE_FORMAT(registrationDate, '%d.%m.%Y') as registrationDate, role, state, creditCard FROM users WHERE login = ? and password = sha1(?)";

	private final static String CREATE_USER_CU = "INSERT INTO users (login, password, name, surname, email, birthday, registrationDate, creditCard)  VALUES (?, sha1(?), ?, ?, ?, ?, ?, ?)";
	private final static int LOGIN_CU = 1; // CU - Create User
	private final static int PASSWORD_CU = 2;
	private final static int NAME_CU = 3;
	private final static int SURNAME_CU = 4;
	private final static int EMAIL_CU = 5;
	private final static int BIRTHDAY_CU = 6;
	private final static int REGISTRATION_DATE_CU = 7;
	private final static int CREDIT_CARD_CU = 8;

	private final static String CREATE_ORDER_CO = "INSERT INTO orders (userId, bikeProductId, startParkingId, startTime, bikeValue, rentPrice) VALUES (?, ?, ?, ?, ?, ?)";
	private final static int USER_ID_CO = 1; // CO - Create Order
	private final static int BIKE_PRODUCT_ID_CO = 2;
	private final static int START_PARKING_ID_CO = 3;
	private final static int START_TIME_CO = 4;
	private final static int BIKE_VALUE_CO = 5;
	private final static int RENT_PRICE_CO = 6;

	private final static String FIND_OPEN_ORDER_FOO = "SELECT o.id, o.bikeProductId, bk.id AS bikeId, bk.brandId, br.brand, bk.model, bk.wheelSize, bk.speedCount, bk.picture, bk.bikeTypeId, bt.type, pk.id AS parkingId, pk.address, o.startTime, o.bikeValue, o.rentPrice FROM orders o LEFT JOIN bikes bk ON (SELECT bikeId FROM bikeproduct WHERE bikeproduct.id=o.bikeProductId)=bk.id LEFT JOIN brands br ON bk.brandId = br.id LEFT JOIN biketype bt ON bk.bikeTypeId = bt.id LEFT JOIN parkings pk ON o.startParkingId=pk.id WHERE userId = ? AND finishTime IS NULL";
	private final static int USER_ID_FOO = 1;

	private final static String FIND_OPEN_ORDER_BY_ID_FOOBI = "SELECT o.id, o.bikeProductId, o.userId, bk.id AS bikeId, bk.brandId, br.brand, bk.model, bk.wheelSize, bk.speedCount, bk.picture, bk.bikeTypeId, bt.type, pk.id AS parkingId, pk.address, o.startTime, o.bikeValue, o.rentPrice FROM orders o LEFT JOIN bikes bk ON (SELECT bikeId FROM bikeproduct WHERE bikeproduct.id=o.bikeProductId)=bk.id LEFT JOIN brands br ON bk.brandId = br.id LEFT JOIN biketype bt ON bk.bikeTypeId = bt.id LEFT JOIN parkings pk ON o.startParkingId=pk.id WHERE o.id = ? AND finishTime IS NULL";
	private final static int FOOBI_ORDER_ID = 1; // FOOBI = Find Open Order By Id

	private final static String ID = "id";
	private final static String BIKE_PRODUCT_ID = "bikeProductId";
	private final static String USER_ID = "userId";
	private final static String BIKE_ID = "bikeId";
	private final static String BRAND_ID = "brandId";
	private final static String BRAND = "brand";
	private final static String MODEL = "model";
	private final static String WHEELSIZE = "wheelSize";
	private final static String SPEEDCOUNT = "speedCount";
	private final static String PICTURE = "picture";
	private final static String BIKETYPE_ID = "bikeTypeId";
	private final static String BIKE_TYPE = "type";
	private final static String PARKING_ID = "parkingId";
	private final static String ADDRESS = "address";
	private final static String BIKE_VALUE = "bikeValue";
	private final static String RENT_PRICE = "rentPrice";
	private final static String START_TIME = "startTime";

	private final static String CLOSE_ORDER_CLO = "UPDATE orders SET finishParkingId = ?, finishTime = ?, payment = ? WHERE id = ?";
	private final static int FINISH_PARKING_CLO = 1; // CLO - CLose Order
	private final static int FINISH_TIME_CLO = 2;
	private final static int PAYMENT_CLO = 3;
	private final static int ID_CLO = 4;

	private final static String CHANGE_BIKE_STATE_TO_RESERVED_CBSTR = "UPDATE bikeproduct SET state = 'reserved' WHERE id = (SELECT bikeProductId FROM orders WHERE orders.id = ?)";
	private final static int BIKE_ORDER_ID_CBSTR = 1; // CBSTR - Change Bike State To Reserved

	private final static String CHANGE_BIKE_STATE_TO_AVAILABLE_CBSTA = "UPDATE bikeproduct SET state = 'available', parkingId = ? WHERE id = (SELECT bikeProductId FROM orders WHERE orders.id = ?)";
	private final static int PARKING_ID_CBSTA = 1; // CBSTA - Change Bike State To Available
	private final static int BIKE_ORDER_ID_CBSTA = 2;

	private final static String SELECT_ALL_USERS = "SELECT u.id, u.login, u.name, u.surname, u.email, u.birthday, u.registrationDate, u.role, u.state, u.creditCard, o.id AS orderId FROM users u LEFT JOIN (SELECT id, userId FROM orders WHERE finishTime IS NULL) o ON u.id= o.userId";
	private final static String LOGIN = "login";
	private final static String NAME = "name";
	private final static String SURNAME = "surname";
	private final static String EMAIL = "email";
	private final static String BIRTHDAY = "birthday";
	private final static String REGISTRATION_DATE = "registrationDate";
	private final static String ROLE = "role";
	private final static String STATE = "state";
	private final static String CREDIT_CARD = "creditCard";
	private final static String ORDER_ID = "orderId";

	private final static String UPDATE_USER_UU = "UPDATE users SET login = ?, name = ?, surname = ?, email = ?, birthday = str_to_date(?, '%d.%m.%Y'), creditCard = ? WHERE id = ?";
	private final static int LOGIN_UU = 1; // UU - Update User
	private final static int NAME_UU = 2;
	private final static int SURNAME_UU = 3;
	private final static int EMAIL_UU = 4;
	private final static int BIRTHDAY_UU = 5;
	private final static int CREDIT_CARD_UU = 6;
	private final static int ID_UU = 7;

	private final static String UPDATE_PASSWORD_UP = "UPDATE users SET password = sha1(?) where id = ?";
	private final static int PASSWORD_UP = 1; // UP - Update Password
	private final static int ID_UP = 2;

	private final static String TAKE_ALL_USER_ORDERS_TAUO = "SELECT o.id, br.brand, bk.model, o.startTime, sp.address as startParking, o.finishTime, fp.address as finishParking, o.rentPrice, TIMESTAMPDIFF(MINUTE, startTime, finishTime) as minute, payment FROM orders o LEFT JOIN parkings sp ON o.startParkingId = sp.id LEFT JOIN parkings fp ON o.finishParkingId = fp.id LEFT JOIN bikeproduct bp ON o.bikeProductId = bp.id LEFT JOIN bikes bk ON bp.bikeId = bk.id LEFT JOIN brands br ON bk.brandId = br.id WHERE userId = ? ORDER by ID desc";
	private final static int TAUO_USER_ID = 1;
	private final static String FINISH_TIME = "finishTime";
	private final static String PAYMENT = "payment";
	private final static String START_PARKING = "startParking";
	private final static String FINISH_PARKING = "finishParking";
	
	private static final String FIND_ORDER_FO = "{call findOrdersProcedure(?,?,?,?,?)}";
	private static final int SURNAME_FO = 1;  //FO - Find Order 
	private static final int FROM_DATE_FO = 2;
	private static final int TO_DATE_FO = 3;
	private static final int FROM_ORDER_ID_FO = 4;
	private static final int ELEMENT_COUNT_FO = 5;
	
	private final static String START_PARKING_ID = "startParkingId";
	private final static String FINISH_PARKING_ID = "finishParkingId";
	
	@Override
	public void register(User user, char[] password) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(CREATE_USER_CU);

		try {
			preparedStatement.setString(LOGIN_CU, user.getLogin());
			preparedStatement.setString(PASSWORD_CU, String.valueOf(password));
			preparedStatement.setString(NAME_CU, user.getName());
			preparedStatement.setString(SURNAME_CU, user.getSurname());
			preparedStatement.setString(EMAIL_CU, user.getEmail());
			preparedStatement.setString(BIRTHDAY_CU, user.getBirthday());
			preparedStatement.setString(REGISTRATION_DATE_CU, user.getRegistrationDate());
			preparedStatement.setString(CREDIT_CARD_CU, user.getCreditCard());

			preparedStatement.executeUpdate();

		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.USER_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while user adding to the DB", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public User login(String login, char[] password) throws DAOException {
		
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(FIND_USER_BY_LOGIN_PASSW);
		
		try {
			preparedStatement.setString(LOGIN_CU, login);
			preparedStatement.setString(PASSWORD_CU, String.valueOf(password));

			ResultSet resultSet = preparedStatement.executeQuery();

			User user = null;
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong(ID));
				user.setLogin(resultSet.getString(LOGIN));
				user.setName(resultSet.getString(NAME));
				user.setSurname(resultSet.getString(SURNAME));
				user.setEmail(resultSet.getString(EMAIL));
				user.setBirthday(resultSet.getString(BIRTHDAY));
				user.setRegistrationDate(resultSet.getString(REGISTRATION_DATE));
				user.setRole(UserRole.valueOf(resultSet.getString(ROLE).toUpperCase()));
				user.setState(UserState.valueOf(resultSet.getString(STATE).toUpperCase()));
				user.setCreditCard(resultSet.getString(CREDIT_CARD));
			}

			return user;
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while reading user data from DB", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

	}

	@Override
	public User updateUser(User user) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(UPDATE_USER_UU);

		try {
			preparedStatement.setString(LOGIN_UU, user.getLogin());
			preparedStatement.setString(NAME_UU, user.getName());
			preparedStatement.setString(SURNAME_UU, user.getSurname());
			preparedStatement.setString(EMAIL_UU, user.getEmail());
			preparedStatement.setString(BIRTHDAY_UU, user.getBirthday());
			preparedStatement.setString(CREDIT_CARD_UU, user.getCreditCard());
			preparedStatement.setLong(ID_UU, user.getId());

			preparedStatement.executeUpdate();
			
			return user;
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.USER_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while updating the user", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public BikeOrder createOrder(BikeOrder order) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(CREATE_ORDER_CO);

		try {
			wrapperConnection.setAutoCommit(false);
			preparedStatement.setLong(USER_ID_CO, order.getUser().getId());
			preparedStatement.setLong(BIKE_PRODUCT_ID_CO, order.getBikeProductId());
			preparedStatement.setLong(START_PARKING_ID_CO, order.getStartParking().getId());
			preparedStatement.setString(START_TIME_CO, order.getStartTime());
			preparedStatement.setBigDecimal(BIKE_VALUE_CO, order.getBikeValue());
			preparedStatement.setBigDecimal(RENT_PRICE_CO, order.getRentPrice());
			preparedStatement.executeUpdate();

			long orderId = ((com.mysql.jdbc.PreparedStatement) preparedStatement).getLastInsertID();
			order.setId(orderId);

			wrapperConnection.closeStatement(preparedStatement);

			preparedStatement = wrapperConnection.getPreparedStatement(CHANGE_BIKE_STATE_TO_RESERVED_CBSTR);
			preparedStatement.setLong(BIKE_ORDER_ID_CBSTR, orderId);
			preparedStatement.executeUpdate();

			wrapperConnection.commit();

		} catch (SQLException e) {
			wrapperConnection.rollback();
			throw new DAOException("An exeption occured in the layer DAO while adding the order to the DB", e);
		} finally {
			wrapperConnection.setAutoCommit(true);
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

		return order;
	}

	@Override
	public BikeOrder findOpenOrder(User user) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(FIND_OPEN_ORDER_FOO);

		BikeOrder bikeOrder = null;

		try {
			preparedStatement.setLong(USER_ID_FOO, user.getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				bikeOrder = new BikeOrder();
				bikeOrder.setId(resultSet.getLong(ID));
				bikeOrder.setBikeProductId(resultSet.getLong(BIKE_PRODUCT_ID));

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
				bikeType.setId(resultSet.getLong(BIKETYPE_ID));
				bikeType.setBikeType(resultSet.getString(BIKE_TYPE));
				bike.setBikeType(bikeType);
				bikeOrder.setBike(bike);

				Parking parking = new Parking();
				parking.setId(resultSet.getLong(PARKING_ID));
				parking.setAddress(resultSet.getString(ADDRESS));
				bikeOrder.setStartParking(parking);

				bikeOrder.setBikeValue(resultSet.getBigDecimal(BIKE_VALUE));
				bikeOrder.setRentPrice(resultSet.getBigDecimal(RENT_PRICE));

				bikeOrder.setStartTime(resultSet.getString(START_TIME));

				bikeOrder.setUser(user);

			}
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while finding open order", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

		return bikeOrder;

	}

	@Override
	public BikeOrder findOpenOrderById(long orderId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(FIND_OPEN_ORDER_BY_ID_FOOBI);

		try {
			preparedStatement.setLong(FOOBI_ORDER_ID, orderId);
			ResultSet resultSet = preparedStatement.executeQuery();

			BikeOrder bikeOrder = null;
			if (resultSet.next()) {
				bikeOrder = new BikeOrder();
				bikeOrder.setId(orderId);
				bikeOrder.setBikeProductId(resultSet.getLong(BIKE_PRODUCT_ID));

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
				bikeType.setId(resultSet.getLong(BIKETYPE_ID));
				bikeType.setBikeType(resultSet.getString(BIKE_TYPE));
				bike.setBikeType(bikeType);
				bikeOrder.setBike(bike);

				Parking parking = new Parking();
				parking.setId(resultSet.getLong(PARKING_ID));
				parking.setAddress(resultSet.getString(ADDRESS));
				bikeOrder.setStartParking(parking);

				bikeOrder.setBikeValue(resultSet.getBigDecimal(BIKE_VALUE));
				bikeOrder.setRentPrice(resultSet.getBigDecimal(RENT_PRICE));

				bikeOrder.setStartTime(resultSet.getString(START_TIME));

				User user = new User();
				user.setId(resultSet.getLong(USER_ID));
			}
			
			return bikeOrder;

		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while finding open order", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public void closeOrder(long orderId, long finishParkingId, String finishTime, BigDecimal payment)
			throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(CLOSE_ORDER_CLO);

		try {
			wrapperConnection.setAutoCommit(false);
			preparedStatement.setLong(FINISH_PARKING_CLO, finishParkingId);
			preparedStatement.setString(FINISH_TIME_CLO, finishTime);
			preparedStatement.setBigDecimal(PAYMENT_CLO, payment);
			preparedStatement.setLong(ID_CLO, orderId);
			preparedStatement.executeUpdate();

			wrapperConnection.closeStatement(preparedStatement);

			preparedStatement = wrapperConnection.getPreparedStatement(CHANGE_BIKE_STATE_TO_AVAILABLE_CBSTA);
			preparedStatement.setLong(PARKING_ID_CBSTA, finishParkingId);
			preparedStatement.setLong(BIKE_ORDER_ID_CBSTA, orderId);
			preparedStatement.executeUpdate();

			wrapperConnection.commit();

		} catch (SQLException e) {
			wrapperConnection.rollback();
			throw new DAOException("An exeption occured in the layer DAO while closing order", e);
		} finally {
			wrapperConnection.setAutoCommit(true);
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public List<BikeOrder> takeAllUsers() throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		Statement statement = wrapperConnection.getStatement();

		List<BikeOrder> orderList = null;

		try {
			
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);

			while (resultSet.next()) {
				if (orderList == null) {
					orderList = new ArrayList<>();
				}
				BikeOrder bikeOrder = new BikeOrder();
				User user = new User();
				user.setId(resultSet.getLong(ID));
				user.setLogin(resultSet.getString(LOGIN));
				user.setName(resultSet.getString(NAME));
				user.setSurname(resultSet.getString(SURNAME));
				user.setEmail(resultSet.getString(EMAIL));
				user.setBirthday(resultSet.getString(BIRTHDAY));
				user.setRegistrationDate(resultSet.getString(REGISTRATION_DATE));
				user.setRole(UserRole.valueOf(resultSet.getString(ROLE).toUpperCase()));
				user.setCreditCard(resultSet.getString(CREDIT_CARD));
				user.setState(UserState.valueOf(resultSet.getString(STATE).toUpperCase()));
				bikeOrder.setUser(user);

				bikeOrder.setId(resultSet.getLong(ORDER_ID));

				orderList.add(bikeOrder);
			}

			return orderList;
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while getting all users", e);
		} finally {
			wrapperConnection.closeStatement(statement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public void updatePassword(char[] password, User user) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(UPDATE_PASSWORD_UP);

		try {
			preparedStatement.setString(PASSWORD_UP, String.valueOf(password));
			preparedStatement.setLong(ID_UP, user.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while updating password", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

	}

	@Override
	public List<BikeOrder> findAllOrderByUser(long userId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(TAKE_ALL_USER_ORDERS_TAUO);

		List<BikeOrder> bikeOrderList = new ArrayList<>();

		try {
			preparedStatement.setLong(TAUO_USER_ID, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				BikeOrder bikeOrder = new BikeOrder();

				bikeOrder.setId(resultSet.getLong(ID));
				bikeOrder.setStartTime(resultSet.getString(START_TIME));
				bikeOrder.setFinishTime(resultSet.getString(FINISH_TIME));
				bikeOrder.setRentPrice(resultSet.getBigDecimal(RENT_PRICE));
				bikeOrder.setPayment(resultSet.getBigDecimal(PAYMENT));

				Bike bike = new Bike();
				Brand brand = new Brand();
				brand.setBrand(resultSet.getString(BRAND));
				bike.setBrand(brand);
				bike.setModel(resultSet.getString(MODEL));
				bikeOrder.setBike(bike);

				Parking startParking = new Parking();
				startParking.setAddress(resultSet.getString(START_PARKING));
				bikeOrder.setStartParking(startParking);

				Parking finishParking = new Parking();
				finishParking.setAddress(resultSet.getString(FINISH_PARKING));
				bikeOrder.setFinishParking(finishParking);

				bikeOrderList.add(bikeOrder);
			}

		} catch (SQLException e) {
			throw new DAOException("An exeption occured in the layer DAO while finding all orders", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

		return bikeOrderList;
	}

	@Override
	public List<BikeOrder> findOrder(String surname, String fromDate, String toDate, PageInfo pageInfo)
			throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		CallableStatement callableStatement =  wrapperConnection.getCallableStatement(FIND_ORDER_FO);
		
		List<BikeOrder> bikeOrderList = new ArrayList<>();
		
		try {
			callableStatement.setString(SURNAME_FO, surname);
			callableStatement.setString(FROM_DATE_FO, fromDate);
			callableStatement.setString(TO_DATE_FO, toDate);
			callableStatement.setLong(FROM_ORDER_ID_FO, pageInfo.getLastPagePoint());
			callableStatement.setInt(ELEMENT_COUNT_FO, pageInfo.getDefaultElementOnPage());
			
			ResultSet resultSet = callableStatement.executeQuery();
			
			while(resultSet.next()) {
				BikeOrder bikeOrder = new BikeOrder();
				bikeOrder.setId(resultSet.getLong(ID));
				bikeOrder.setStartTime(resultSet.getString(START_TIME));
				bikeOrder.setFinishTime(resultSet.getString(FINISH_TIME));
				bikeOrder.setRentPrice(resultSet.getBigDecimal(RENT_PRICE));
				bikeOrder.setPayment(resultSet.getBigDecimal(PAYMENT));
				
				User user = new User();
				user.setId(resultSet.getLong(ID));
				user.setName(resultSet.getString(NAME));
				user.setSurname(resultSet.getString(SURNAME));
				bikeOrder.setUser(user);
				
				Parking startParking = new Parking();
				startParking.setId(resultSet.getLong(START_PARKING_ID));
				startParking.setAddress(resultSet.getString(START_PARKING));
				bikeOrder.setStartParking(startParking);

				Parking finishParking = new Parking();
				finishParking.setId(resultSet.getLong(FINISH_PARKING_ID));
				finishParking.setAddress(resultSet.getString(FINISH_PARKING));
				bikeOrder.setFinishParking(finishParking);
				
				bikeOrderList.add(bikeOrder);
			}
			
		} catch (SQLException e) {
			throw new DAOException("Exception was threw during find order in DB", e);
		}finally {
			wrapperConnection.closeStatement(callableStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
		
		return bikeOrderList;
	}

}
