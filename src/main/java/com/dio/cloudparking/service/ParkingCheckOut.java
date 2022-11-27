package com.dio.cloudparking.service;

import com.dio.cloudparking.model.Parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingCheckOut {

    public static final int ONE_HOUR = 60;
    public static final int TWENTY_FOUR_HOUR = 24 * ONE_HOUR;
    public static final double ONE_HOUR_VALUE = 5.00;
    public static final double ADDITIONAL_PER_HOUR_VALUE = 2.00;
    public static final double DAY_VALUE = 20.00;

    public static Double getBill(Parking parking){
        return getBill(parking.getEntryDate(), parking.getExitDate());
    }

    private static Double getBill(LocalDateTime entryDate, LocalDateTime exitDate) {
        long minutes = entryDate.until(exitDate, ChronoUnit.MINUTES);
        Double bill = 0.0;
        // Cobrando valor minimo
        if (minutes <= ONE_HOUR){
            return ONE_HOUR_VALUE;
        }
        // Cobrando o acréscimo de hora
        if (minutes <= TWENTY_FOUR_HOUR) {
            bill = ONE_HOUR_VALUE;
            int hours = (int) (minutes / ONE_HOUR);
            // Valor adicional de hora
            for (int i=0; i<hours; i++){
                bill += ADDITIONAL_PER_HOUR_VALUE;
            }
            return bill;
        }
        // Cobrando em caso de diária
        int days = (int) (minutes / TWENTY_FOUR_HOUR);
        for (int i=0; i<days; i++){
            bill += DAY_VALUE;
        }
        return bill;
    }


}
