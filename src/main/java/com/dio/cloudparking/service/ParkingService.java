package com.dio.cloudparking.service;

import com.dio.cloudparking.model.Parking;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap();

    static {
        var id = getUUID();
        Parking parking = new Parking(id, "RBD-3234", "SP", "MUSTANG", "PRETO");
        parkingMap.put(id, parking);
    }

    public List<Parking> findAll(){
        // Pregando tudo que está no Map e transformando em uma List
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
