package com.postnikov.epam.rentbike.dao;

import java.math.BigDecimal;
import java.util.List;

import com.postnikov.epam.rentbike.domain.PageInfo;
import com.postnikov.epam.rentbike.domain.entity.BikeOrder;
import com.postnikov.epam.rentbike.domain.entity.User;
import com.postnikov.epam.rentbike.exception.DAOException;

/**
 * Data access interface that work with {@link User} entity
 *
 * @author Serge Postnikov
 */

public interface UserDAO {

	/**
	 * Searches user in DB. 
	 *
	 * @param login String.
	 * @param password - password, which must be compared with the password from the database.
	 * @return {@link User}.
	 * @throws DAOException if occurred SQL exception.
	 */
	User login(String login, char[] password) throws DAOException;

	/**
	 * Adds User to DB.
	 *
	 * @param user - User object that should be added in database.
	 * @param password as char[].
	 * @throws DAOException if occurred SQL exception.
	 */
	void register(User user, char[] password) throws DAOException;

	/**
	 * Creates bike order in DB.
	 *
	 * @param order - BikeOrder object with empty id field, that should be added to database.
	 * @return BikeOrder object from DB with auto generated bikeOrder ID.
	 * @throws DAOException if occurred SQL exception.
	 */
	BikeOrder createOrder(BikeOrder order) throws DAOException;
	
	
	/**
	 * Updates bike order (close order) in DB.
	 *
	 * @param orderId - order id, which will be closed.
	 * @param finishParkingId - finish parking id.
	 * @param finishTime - order closing time.
	 * @param payment - BigDecimal order's payment.
	 * @throws DAOException if occurred SQL exception.
	 */	
	void closeOrder(long orderId, long finishParkingId, String finishTime, BigDecimal payment) throws DAOException;
	
	
	/**
	 * Finds open user's order in DB.
	 *
	 * @param user - User whose order will be searched.
	 * @return object of BikeOrder, that was found for current user.
	 * @throws DAOException if occurred SQL exception.
	 */	
	BikeOrder findOpenOrder(User user) throws DAOException;
	
	
	/**
	 * Searches for an open order by order id.
	 * 
	 * @param orderId.
	 * @return {@link BikeOrder} if order was found and null if not.
	 * @throws DAOException if occurred SQL exception.
	 */
	BikeOrder findOpenOrderById(long orderId) throws DAOException; 
	
	/**
	 * Returns a list of all users.
	 * 
	 * @return List<UserOrder> orderUserList
	 * @throws DAOException if occurred SQL exception
	 */
	List<BikeOrder> takeAllUsers() throws DAOException;
	
	/**
	 * Updates user personal data except password.
	 * 
	 * @param user - {@link User} with new personal data.
	 * @return {@link User}
	 * @throws DAOException if occurred SQL exception
	 */
	User updateUser (User user) throws DAOException;
	
	/**
	 * Updates user password.
	 * 
	 * @param password.
	 * @param user {@link User}.
	 * @throws DAOException if occurred SQL exception
	 */
	void updatePassword(char[] password, User user) throws DAOException;
	
	
	/**
	 * Returns all orders of the user.
	 * 
	 * @param userId.
	 * @return List<BikeOrder> bikeOrderList.
	 * @throws DAOException if occurred SQL exception.
	 */
	List<BikeOrder> findAllOrderByUser(long userId) throws DAOException;
	
	
	/**
	 * Finds orders according received parameters.
	 * 
	 * @param surname
	 * @param fromDate {@link String} format: yyyy-MM-dd
	 * @param toDate {@link String} format: yyyy-MM-dd
	 * @param pageInfo {@link PageInfo}
	 * @return List<BikeOrder>
	 * @throws DAOException if occurred SQL exception.
	 */
	List<BikeOrder> findOrder(String surname, String fromDate, String toDate, PageInfo pageInfo) throws DAOException;
	
}
