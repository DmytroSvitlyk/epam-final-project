package com.delivery.delivery.model;

public class Tariff {

    public static boolean initialized;
    public static double perKmPrice;
    public static double belowTwoKgPrice;
    public static double belowFiveKgPrice;
    public static double belowTenKgPrice;
    public static double belowTwentyKgPrice;
    public static double belowThirtyKgPrice;
    public static double aboveThirtyKgPrice;
    public static double uploadingPricePerKg;
    public static double unloadingPricePerKg;
    public static double toAddressPrice;
    public static double commission;
    public static double defaultPrice;

    public static void initTariff(double[] tariffArray) {
        if(tariffArray.length != 12)
            throw new IllegalStateException("Bad count of tariffArray elements");
        perKmPrice = tariffArray[0];
        belowTwoKgPrice = tariffArray[1];
        belowFiveKgPrice = tariffArray[2];
        belowTenKgPrice = tariffArray[3];
        belowTwentyKgPrice = tariffArray[4];
        belowThirtyKgPrice = tariffArray[5];
        aboveThirtyKgPrice = tariffArray[6];
        uploadingPricePerKg = tariffArray[7];
        unloadingPricePerKg = tariffArray[8];
        toAddressPrice = tariffArray[9];
        commission = tariffArray[10];
        defaultPrice = tariffArray[11];
        initialized = true;
    }

}
