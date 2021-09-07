package com.delivery.delivery.service;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DAOFactory;
import com.delivery.delivery.dao.OrderDAO;
import com.delivery.delivery.dao.UserDAO;
import com.delivery.delivery.model.Order;
import com.delivery.delivery.util.OrderPriceCalculator;
import com.delivery.delivery.util.UtilException;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class OrderService {

    private static Logger logger = Logger.getLogger(OrderService.class);

    private static OrderService instance;

    private OrderService() {
    }

    public static synchronized OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    private OrderDAO getOrderDAO() {
        return DAOFactory.getDAOFactory().getOrderDao();
    }

    private UserDAO getUserDAO() {
        return DAOFactory.getDAOFactory().getUserDao();
    }

    public Order makeOrder(Order order) {
        OrderDAO orderDAO = getOrderDAO();
        try {
            if(!order.isFromAddressRequired()) {
                order.getAdditional().setAddressFrom(order.getDirection().getDepotFrom().getAddress());
            }
            if(!order.isToAddressRequired()) {
                order.getAdditional().setAddressTo(order.getDirection().getDepotTo().getAddress());
            }
            order.setPrice(OrderPriceCalculator.calculatePrice(order));
            return orderDAO.insertOrder(order);
        } catch (DAOException e) {
            logger.warn("Error from database while trying to register order from with id: " + order.getId());
            throw new ServiceException(e);
        } catch (UtilException e) {
            logger.warn("Error from util class while trying to register order from with id: " + order.getId());
            throw new ServiceException(e);
        }
    }

    public Order getOrderById(int id) {
        OrderDAO orderDAO = getOrderDAO();
        Order order = null;
        try {
            order = orderDAO.getById(id);
            fillOrderDirectionAndUser(order);
        } catch (DAOException e) {
            logger.warn("Error while trying to get order by id: " + id);
            throw new ServiceException(e);
        }
        return order;
    }

    public List<Order> getOrderByUserId(int id) {
        OrderDAO orderDAO = getOrderDAO();
        List<Order> orders = null;
        try {
            orders = orderDAO.getByUserId(id);
            for(Order order : orders) {
                fillOrderDirectionAndUser(order);
            }
        } catch (DAOException e) {
            logger.warn("Error while trying to get orders by user id: " + id);
            throw new ServiceException(e);
        }
        return orders;
    }

    public List<Order> getOrdersByStatusId(int id) {
        OrderDAO orderDAO = getOrderDAO();
        List<Order> orders = null;
        try {
            orders = orderDAO.getByStatusId(id);
            for(Order order : orders) {
                fillOrderDirectionAndUser(order);
            }
        } catch (DAOException e) {
            logger.warn("Error while trying to get orders by status id: " + id);
            throw new ServiceException(e);
        }
        return orders;
    }

    public List<Order> getOrdersByDirectionId(int id) {
        OrderDAO orderDAO = getOrderDAO();
        List<Order> orders = null;
        try {
            orders = orderDAO.getByDirectionId(id);
            for(Order order : orders) {
                fillOrderDirectionAndUser(order);
            }
        } catch (DAOException e) {
            logger.warn("Error while trying to get orders by direction id: " + id);
            throw new ServiceException(e);
        }
        return orders;
    }

    public List<Order> getOrdersByGoalDate(LocalDate date) {
        OrderDAO orderDAO = getOrderDAO();
        List<Order> orders = null;
        try {
            orders = orderDAO.getByGoalDate(date);
            for(Order order : orders) {
                fillOrderDirectionAndUser(order);
            }
        } catch (DAOException e) {
            logger.warn("Error while trying to get orders by goal date: " + date);
            throw new ServiceException(e);
        }
        return orders;
    }

    private void fillOrderDirectionAndUser(Order order) {
        DirectionService directionService = DirectionService.getInstance();
        UserDAO userDAO = getUserDAO();
        order.setDirection(directionService.getDirectionById(order.getDirection().getId()));
        order.setUser(userDAO.getById(order.getUser().getId()));
    }

}
