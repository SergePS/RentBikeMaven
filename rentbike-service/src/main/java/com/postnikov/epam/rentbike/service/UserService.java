package com.postnikov.epam.rentbike.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.BikeProduct;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.exception.ServiceException;

/**
 * @author Sergey Postnikov
 *
 */
public interface UserService {

	/**
	 * Validates incoming data and return {@link User} if login and password are
	 * valid or null if not.
	 * 
	 * @param requestParameters
	 *            - all parameters from request (without password).
	 * @param password
	 *            - char array object.
	 * @return {@link User}.
	 * @throws ServiceException
	 *             if any exceptions occurred.
	 */
	User login(Map<String, String> requestParameters, char[] password) throws ServiceException;

	/**
	 * Validate incoming data and register new User.
	 * 
	 * @param requestParameters
	 *            - all parameters from request (without password).
	 * @param password
	 *            - char array object.
	 * @return {@link User}.
	 * @throws ServiceException
	 *             if any exceptions occurred.
	 */
	void register(Map<String, String> requestParameters, char[] password) throws ServiceException;

	/**
	 * Create new bikeOrder {@link BikeOrder}
	 * 
	 * @param filled
	 *            object of the {@link User}
	 * @param filled
	 *            object of the BikeProduct
	 * @return filled object of the BikeOrder
	 * @throws ServiceException
	 */
	BikeOrder createOrder(User user, BikeProduct bikeProduct) throws ServiceException;

	/**
	 * Find an open BikeOrder by User
	 * 
	 * @param filled
	 *            object of {@link User}
	 * @return filled BikeOrder if opened order exist or null if not
	 * @throws ServiceException
	 */
	BikeOrder findOpenOrder(User user) throws ServiceException;

	/**
	 * Validates incoming data and returns {@link BikeOrder}. If bike order wasn't
	 * found - returns <code>null</code>.
	 * 
	 * @param Stirng
	 *            orderId.
	 * @return {@link BikeOrder}.
	 * @throws ServiceException
	 *             if any Exception occurred.
	 */
	BikeOrder findOpenOrderById(String orderId) throws ServiceException;

	/**
	 * Close an open order
	 * 
	 * @return empty String if the action succeeds or String message of the
	 *         MessagePage if something wrong
	 * @throws ServiceException
	 */
	BigDecimal closeOrder(Map<String, String> requestParameters) throws ServiceException;

	/**
	 * Return all bike orders.
	 * 
	 * @return List<BikeOrder>
	 * @throws ServiceException
	 *             if any Exception occurred.
	 */
	List<BikeOrder> takeAllUsers() throws ServiceException;

	/**
	 * Validates incoming data and updates user personal data except password.
	 * 
	 * @param requestParameters
	 *            - all parameters from request (without password).
	 * @param user
	 *            {@link User} with current personal data.
	 * @return {@link User}
	 * @throws ServiceException
	 *             if any Exception occurred.
	 */
	User userUpdate(Map<String, String> requestParameters, User user) throws ServiceException;

	/**
	 * Validates incoming data, checks old password and updates it.
	 * 
	 * @param currentPassword
	 *            - current user password.
	 * @param currentPassword
	 *            - new password.
	 * @param user
	 *            {@link User}.
	 * @throws ServiceException
	 *             if any Exception occurred.
	 */
	void updatePassword(char[] currentPassword, char[] password, User user) throws ServiceException;

	/**
	 * Returns a list of user orders.
	 * 
	 * @param user
	 *            {@link User}.
	 * @return List<BikeOrder>.
	 * @throws ServiceException
	 *             if any Exception occurred.
	 */
	List<BikeOrder> findAllOrderByUser(User user) throws ServiceException;


	/**
	 * Validates incoming parameters and finds orders according received parameters.
	 * @param requestParameters - - all parameters from request.
	 * @param pageInfo - object with pagination parameters.
	 * @return List<BikeOrder>.
	 * @throws ServiceException if any Exception occurred.
	 */
	List<BikeOrder> findOrder(Map<String, String> requestParameters, PageInfo pageInfo) throws ServiceException;

}
