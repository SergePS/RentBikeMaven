package com.postnikov.epam.rentbike.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.dao.DAOFactory;
import com.postnikov.epam.rentbike.dao.ParkingDAO;
import com.postnikov.epam.rentbike.dao.UserDAO;
import com.postnikov.epam.rentbike.domain.ApplicationProperty;
import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.RequestParameter;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.DAOException;
import com.postnikov.epam.rentbike.exception.ServiceException;
import com.postnikov.epam.rentbike.service.DateFormatter;
import com.postnikov.epam.rentbike.service.UserService;
import com.postnikov.epam.rentbike.validator.UserParameterValidator;

public class UserServiceImpl implements UserService {

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private final static String FREE_RENT_TIME_PROP_KEY = "free_rent_time";
	private final static int FREE_RENT_TIME_PERIOD_PER_MINUTE = Integer
			.parseInt(ApplicationProperty.takeProperty().getProperty(FREE_RENT_TIME_PROP_KEY));

	public UserServiceImpl() {
	}
	
	@Override
	public User login(Map<String, String> requestParameters, char[] password) throws ServiceException {
		
		logger.log(Level.DEBUG, "Try to get user from DAO");		//TODO delete
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		logger.log(Level.DEBUG, "We got daoFactory");		//TODO delete
		
		UserDAO userDAO = daoFactory.getUserDAO();
			
		String login = requestParameters.get(RequestParameter.LOGIN.parameter());
		logger.log(Level.DEBUG, "login user - " + login);		//TODO delete
		if (!UserParameterValidator.loginValidate(login)) {
			logger.log(Level.DEBUG, "login validatation error, login = " + login);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		if (!UserParameterValidator.passwordValidate(password)) {
			logger.log(Level.DEBUG, "password validatation error");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		try {
			return userDAO.login(login, password);
		} catch (DAOException e) {
			throw new ServiceException("An exeption occured in the layer Service while getting user data", e);
		}
	}

	@Override
	public void register(Map<String, String> requestParameters, char[] password) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();
		
		User user = new User();

		String login = requestParameters.get(RequestParameter.LOGIN.parameter());
		if (!UserParameterValidator.loginValidate(login)) {
			logger.log(Level.DEBUG, "login validatation error, login = " + login);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setLogin(login);

		if (!UserParameterValidator.passwordValidate(password)) {
			logger.log(Level.DEBUG, "password validatation error");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		String name = requestParameters.get(RequestParameter.NAME.parameter());
		if (!UserParameterValidator.nameValidate(name)) {
			logger.log(Level.DEBUG, "name validatation error, name = " + name);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setName(name);

		String surname = requestParameters.get(RequestParameter.SURNAME.parameter());
		if (!UserParameterValidator.nameValidate(surname)) {
			logger.log(Level.DEBUG, "surname validatation error, surname = " + surname);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setSurname(surname);

		String email = requestParameters.get(RequestParameter.EMAIL.parameter());
		if (!UserParameterValidator.emailValidate(email)) {
			logger.log(Level.DEBUG, "email validatation error, email = " + email);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setEmail(email);

		String birthday = requestParameters.get(RequestParameter.BIRTHDAY.parameter());
		if (!UserParameterValidator.birthdayValidate(birthday)) {
			throw new ServiceException(ExceptionMessage.USER_IS_TOO_YOUNG.toString());
		}
		user.setBirthday(DateFormatter.modifyDateToDB(birthday));

		user.setRegistrationDate(DateFormatter.takeCurrentDateTimeToDB());

		String creditCard = requestParameters.get(RequestParameter.CRADIT_CARD.parameter());
		if (!UserParameterValidator.craditcardValidate(creditCard)) {
			logger.log(Level.DEBUG, "creditCard validatation error, creditCard = " + creditCard);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setCreditCard(creditCard);

		try {
			userDAO.register(user, password);
		} catch (DAOException e) {
			throw new ServiceException("Add user to DB error", e);
		}

	}

	@Override
	public BikeOrder createOrder(User user, BikeProduct bikeProduct) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		BikeOrder bikeOrder = new BikeOrder();
		bikeOrder.setUser(user);
		bikeOrder.setBikeProductId(bikeProduct.getId());
		bikeOrder.setBike(bikeProduct.getBike());
		bikeOrder.setStartParking(bikeProduct.getParking());
		bikeOrder.setStartTime(DateFormatter.takeCurrentDateTimeToDB());
		bikeOrder.setBikeValue(bikeProduct.getValue());
		bikeOrder.setRentPrice(bikeProduct.getRentPrice());

		try {
			userDAO.createOrder(bikeOrder);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return bikeOrder;
	}

	@Override
	public BikeOrder findOpenOrder(User user) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			return userDAO.findOpenOrder(user);
		} catch (DAOException e) {
			throw new ServiceException("Find open order error", e);
		}

	}

	@Override
	public BikeOrder findOpenOrderById(String orderIdString) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		if (!UserParameterValidator.idValidate(orderIdString)) {
			logger.log(Level.DEBUG, "Id validatation error, id = " + orderIdString);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long orderId = Long.parseLong(orderIdString);

		try {
			return userDAO.findOpenOrderById(orderId);
		} catch (DAOException e) {
			throw new ServiceException("Find open order by id error", e);
		}
	}

	@Override
	public BigDecimal closeOrder(Map<String, String> requestParameters) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();
		ParkingDAO parkingDAO = daoFactory.getParkingDAO();

		// orderId validation
		String orderIdString = requestParameters.get(RequestParameter.ORDER_ID.parameter());
		if (!UserParameterValidator.idValidate(orderIdString)) {
			logger.log(Level.DEBUG, "orderId = " + orderIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long orderId = Long.parseLong(orderIdString);

		// checking the existence of bikeOrder
		BikeOrder bikeOrder = null;
		try {
			bikeOrder = userDAO.findOpenOrderById(orderId);
		} catch (DAOException e) {
			throw new ServiceException("Find open order by id error", e);
		}
		if (bikeOrder == null) {
			logger.log(Level.DEBUG, "order not exist");
			throw new ServiceException(ExceptionMessage.ORDER_NOT_EXIST.toString());
		}

		// parkingId validation
		String finishParkingIdString = requestParameters.get(RequestParameter.FINISH_PARKING_ID.parameter());
		if (!UserParameterValidator.idValidate(finishParkingIdString)) {
			logger.log(Level.DEBUG, "parkingId  = " + finishParkingIdString + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		long finishParkingId = Long.parseLong(finishParkingIdString);

		Parking parking = null;
		try {
			parking = parkingDAO.findParkingById(finishParkingId);
		} catch (DAOException e) {
			throw new ServiceException("Find parking by id error", e);
		}
		if (parking == null) {
			logger.log(Level.DEBUG, "parking validation error: parking not exist");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		// finish time validation
		String startTime = requestParameters.get(RequestParameter.START_TIME.parameter());
		String finishTime = DateFormatter.takeCurrentDateTimeToDB();

		long rentMinutes = DateFormatter.takeMinutesBetweenDates(startTime, finishTime);

		if (rentMinutes < 0) {
			logger.log(Level.DEBUG, "start time - " + startTime + " is wrong");
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		// calculate payment end close order
		BigDecimal rentPrice = new BigDecimal(requestParameters.get(RequestParameter.RENT_PRICE.parameter()));
		BigDecimal payment;

		if (rentMinutes > FREE_RENT_TIME_PERIOD_PER_MINUTE) {
			payment = rentPrice.multiply(new BigDecimal(rentMinutes));
		} else {
			payment = BigDecimal.ZERO;
		}

		try {
			userDAO.closeOrder(orderId, finishParkingId, finishTime, payment);
		} catch (DAOException e) {
			throw new ServiceException("Close order error", e);
		}

		return BigDecimal.ZERO.equals(payment) ? BigDecimal.ZERO : payment;

	}

	@Override
	public List<BikeOrder> takeAllUsers() throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			return userDAO.takeAllUsers();
		} catch (DAOException e) {
			throw new ServiceException("take all user error", e);
		}
	}

	@Override
	public User userUpdate(Map<String, String> requestParameters, User user) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		String login = requestParameters.get(RequestParameter.LOGIN.parameter());
		if (!UserParameterValidator.loginValidate(login)) {
			logger.log(Level.DEBUG, "login validatation error, login = " + login);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setLogin(login);

		String name = requestParameters.get(RequestParameter.NAME.parameter());
		if (!UserParameterValidator.nameValidate(name)) {
			logger.log(Level.DEBUG, "name validatation error, name = " + name);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setName(name);

		String surname = requestParameters.get(RequestParameter.SURNAME.parameter());
		if (!UserParameterValidator.nameValidate(surname)) {
			logger.log(Level.DEBUG, "surname validatation error, surname = " + surname);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setSurname(surname);

		String email = requestParameters.get(RequestParameter.EMAIL.parameter());
		if (!UserParameterValidator.emailValidate(email)) {
			logger.log(Level.DEBUG, "email validatation error, email = " + email);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setEmail(email);

		String birthday = requestParameters.get(RequestParameter.BIRTHDAY.parameter());
		if (!UserParameterValidator.birthdayValidate(birthday)) {
			logger.log(Level.DEBUG, "birthday validatation error, birthday = " + birthday);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setBirthday(birthday);

		String creditCard = requestParameters.get(RequestParameter.CRADIT_CARD.parameter());
		if (!UserParameterValidator.craditcardValidate(creditCard)) {
			logger.log(Level.DEBUG, "creditCard validatation error, creditCard = " + creditCard);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}
		user.setCreditCard(creditCard);

		try {
			return userDAO.updateUser(user);
		} catch (DAOException e) {
			throw new ServiceException("Update user to DB error", e);
		}

	}

	@Override
	public void updatePassword(char[] currentPassword, char[] password, User currentUser) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		if (!UserParameterValidator.passwordValidate(password)) {
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		try {
			User user = userDAO.login(currentUser.getLogin(), currentPassword);
			if (user == null) {
				throw new ServiceException(ExceptionMessage.CURRENT_PASSW_WRONG.toString());
			}

			if (!UserParameterValidator.passwordValidate(password)) {
				throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
			}

			userDAO.updatePassword(password, user);

		} catch (DAOException e) {
			throw new ServiceException("An exeption occured in the layer Service while update password of user", e);
		}
	}

	@Override
	public List<BikeOrder> findAllOrderByUser(User user) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			List<BikeOrder> bikeOrderList = userDAO.findAllOrderByUser(user.getId());
			for (BikeOrder bikeOrder : bikeOrderList) {
				bikeOrder.setStartTime(DateFormatter.modifyDateTimeToView(bikeOrder.getStartTime()));
				if (bikeOrder.getFinishTime() != null) {
					bikeOrder.setFinishTime(DateFormatter.modifyDateTimeToView(bikeOrder.getFinishTime()));
				}
			}
			return bikeOrderList;

		} catch (DAOException e) {
			throw new ServiceException("An exception was thrown when searching for all users", e);
		}
	}

	@Override
	public List<BikeOrder> findOrder(Map<String, String> requestParameters, PageInfo pageInfo) throws ServiceException {
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		String surname = requestParameters.get(RequestParameter.SURNAME.parameter());
		if (!surname.isEmpty() && !UserParameterValidator.nameValidate(surname)) {
			logger.log(Level.DEBUG, "surname validatation error, surname = " + surname);
			throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
		}

		String fromDate = requestParameters.get(RequestParameter.FROM_DATE.parameter());
		if(!fromDate.isEmpty()) {
			if (!UserParameterValidator.dateValidate(fromDate)) {
				logger.log(Level.DEBUG, "fromDate validatation error, fromDate = " + fromDate);
				throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
			}
			fromDate = DateFormatter.modifyDateToDB(fromDate);
		}

		String toDate = requestParameters.get(RequestParameter.TO_DATE.parameter());
		if(!toDate.isEmpty()) {
			if (!UserParameterValidator.dateValidate(toDate)) {
				logger.log(Level.DEBUG, "toDate validatation error, toDate = " + toDate);
				throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
			}
			toDate = DateFormatter.modifyDateToDB(toDate);
		}
		
		try {
			return userDAO.findOrder(surname, fromDate, toDate, pageInfo);
		} catch (DAOException e) {
			throw new ServiceException("An exception was thrown when searching for orders", e);
		}

	}

}
