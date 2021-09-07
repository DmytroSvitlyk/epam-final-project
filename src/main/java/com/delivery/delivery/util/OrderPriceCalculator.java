package com.delivery.delivery.util;

import com.delivery.delivery.model.Order;
import com.delivery.delivery.model.Tariff;

public class OrderPriceCalculator {

    public static double calculatePrice(Order order) {
        if (!Tariff.initialized)
            throw new UtilException("Cant calculate order if Tariff is not initialized");
        double price = Tariff.defaultPrice;
        price += getPriceByWeight(order.getCargo().getCalcWeight());
        price += order.getDirection().getRange() * Tariff.perKmPrice;
        price += getPriceByAdditionalService(order);
        price += getCommission(price);
        return price;
    }

    private static double getPriceByWeight(double weight) {
        if (weight <= 0) {
            throw new UtilException("Cargo weight cant be negative or null");
        }
        if (weight <= 2) {
            return Tariff.belowTwoKgPrice;
        }
        if (weight <= 5) {
            return Tariff.belowFiveKgPrice;
        }
        if (weight <= 10) {
            return Tariff.belowTenKgPrice;
        }
        if (weight <= 20) {
            return Tariff.belowTwentyKgPrice;
        }
        if (weight <= 30) {
            return Tariff.belowThirtyKgPrice;
        }
        return Tariff.aboveThirtyKgPrice;
    }

    private static double getPriceByAdditionalService(Order order) {
        double addPrice = 0;
        addPrice += order.isFromAddressRequired() ? Tariff.toAddressPrice : 0;
        addPrice += order.isToAddressRequired() ? Tariff.toAddressPrice : 0;
        addPrice += order.isUnloadingRequired() ? Tariff.unloadingPricePerKg * order.getCargo().getWeight() : 0;
        addPrice += order.isUploadingRequired() ? Tariff.uploadingPricePerKg * order.getCargo().getWeight() : 0;
        return addPrice;
    }

    private static double getCommission(double price) {
        return price < 200 ? 1 : price * Tariff.commission;
    }

}
