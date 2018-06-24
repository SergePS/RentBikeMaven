package com.postnikov.epam.rentbike.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.postnikov.epam.rentbike.connection.ConnectionPool;
import com.postnikov.epam.rentbike.connection.WrapperConnection;
import com.postnikov.epam.rentbike.dao.ParkingDAO;
import com.postnikov.epam.rentbike.domain.entity.Parking;
import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;
import com.postnikov.epam.rentbike.domain.exception.ExceptionMessage;
import com.postnikov.epam.rentbike.exception.DAOException;

public class SqlParkingDAO implements ParkingDAO {

	private static Logger logger = LogManager.getLogger(SqlParkingDAO.class);

	private final static String ADD_PARKING_AP = "INSERT INTO parkings (address, capacity) VALUES(?, ?)";
	private final static int AP_ADDRESS = 1; //AP - Add Parking
	private final static int AP_PARKING_CAPACITY = 2;

	private final static String TAKE_ALL_PARKING_TAP = "SELECT pk.id, pk.address, pk.capacity, bp.bikeCount FROM parkings pk LEFT JOIN (SELECT parkingId, COUNT(id) as bikeCount FROM bikeProduct WHERE state = 'available' GROUP BY parkingId) bp ON pk.id = bp.parkingId";
	private final static String ID = "id";
	private final static String ADDRESS = "address";
	private final static String CAPACITY_PARKING = "capacity";
	private final static String BIKE_COUNT = "bikeCount";

	private final static String FIND_PARKING_BY_ID_FPBI = "SELECT pk.id, pk.address, pk.capacity, bp.bikeCount FROM parkings pk LEFT JOIN (SELECT parkingId, COUNT(id) as bikeCount FROM bikeProduct WHERE state = 'available' GROUP BY parkingId) bp ON pk.id = bp.parkingId WHERE id=?";
	private final static int FPBI_ID = 1; //FPBI - Find Parking By Id
	
	private final static String UPDATE_PARKING_UP = "UPDATE parkings SET address = ?, capacity = ? WHERE id = ?";
	private final static int UP_ADDRESS = 1; //UP - Update Parking
	private final static int UP_CAPACITY = 2;
	private final static int UP_ID = 3;

	public SqlParkingDAO() {

	}

	@Override
	public void addParking(Parking parking) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(ADD_PARKING_AP);

		try { 
			preparedStatement.setString(AP_ADDRESS, parking.getAddress());
			preparedStatement.setInt(AP_PARKING_CAPACITY, parking.getCapacity());
			preparedStatement.executeUpdate();
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.PARKING_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("An error occurred while adding the parking", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}
	
	public void updateParking(Parking parking) throws DAOException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = connectionPool.getWrapperConnection();
		PreparedStatement preparedStatement = wrapperConnection.getPreparedStatement(UPDATE_PARKING_UP);
		
		try { 
			preparedStatement.setString(UP_ADDRESS, parking.getAddress());
			preparedStatement.setInt(UP_CAPACITY, parking.getCapacity());
			preparedStatement.setLong(UP_ID, parking.getId());
			preparedStatement.executeUpdate();
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new DAOException(ExceptionMessage.PARKING_DUBLICATE_ERROR.toString());
		} catch (SQLException e) {
			throw new DAOException("An error occurred while updating the parking", e);
		} finally {
			wrapperConnection.closeStatement(preparedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public Parking findParkingById(long parkingId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = null;
		PreparedStatement prearedStatement = null;
		
		Parking parking = null;

		try {
			wrapperConnection = connectionPool.getWrapperConnection();
			prearedStatement = wrapperConnection
					.getPreparedStatement(FIND_PARKING_BY_ID_FPBI);

			prearedStatement.setLong(FPBI_ID, parkingId);

			ResultSet resultSet = prearedStatement.executeQuery();
				
			if (resultSet.next()) {
				parking = new Parking();
				parking.setId(resultSet.getLong(ID));
				parking.setAddress(resultSet.getString(ADDRESS));
				parking.setCapacity(resultSet.getInt(CAPACITY_PARKING));
			}
			return parking;

		} catch (SQLException e) {
			throw new DAOException("Find parking by id error", e);
		} finally {
			wrapperConnection.closeStatement(prearedStatement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}
	}

	@Override
	public List<Parking> takeAllParking() throws DAOException {
		List<Parking> parkingList = new ArrayList<>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection wrapperConnection = null;
		Statement statement = null;

		try {
			wrapperConnection = connectionPool.getWrapperConnection();
			statement = wrapperConnection.getStatement();

			ResultSet resultSet = statement.executeQuery(TAKE_ALL_PARKING_TAP);

			while (resultSet.next()) {
				Parking parking = new Parking();
				parking.setId(resultSet.getLong(FPBI_ID));
				parking.setAddress(resultSet.getString(ADDRESS));
				parking.setCapacity(resultSet.getInt(CAPACITY_PARKING));
				parking.setBikeCount(resultSet.getInt(BIKE_COUNT));
				parkingList.add(parking);
			}

		} catch (SQLException e) {
			logger.log(Level.ERROR, "Get all parkings error, " + ConvertPrintStackTraceToString.convert(e));
		} finally {
			wrapperConnection.closeStatement(statement);
			connectionPool.returnWrapperConnection(wrapperConnection);
		}

		return parkingList;
	}
	

}
