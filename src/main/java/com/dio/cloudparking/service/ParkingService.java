package com.dio.cloudparking.service;

import com.dio.cloudparking.exception.ParkingNotFoundException;
import com.dio.cloudparking.model.Parking;
import com.dio.cloudparking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    //private static Map<String, Parking> parkingMap = new HashMap();
    private final ParkingRepository parkingRepository;

    static {
        var id = getUUID();
        var id1 = getUUID();
        //Parking parking = new Parking(id, "RBD-3234", "SP", "MUSTANG", "PRETO");
        //Parking parking1 = new Parking(id1, "RBD-9999", "RS", "GOL QUADRADO", "AZUL");
       // parkingMap.put(id, parking);
       // parkingMap.put(id1, parking1);
    }

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll(){
        // Pregando tudo que está no Map e transformando em uma List
        return parkingRepository.findAll();
        //return parkingMap.values().stream().collect(Collectors.toList());
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional(readOnly = true)
    public Parking findById(String id) {
        /*Parking parking = parkingMap.get(id);
        if(parking == null){
            throw new ParkingNotFoundException(id);
        }
        return parking;
        */
         return parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        //parkingMap.put(uuid, parkingCreate);
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {
        findById(id);
        //parkingMap.remove(id);
        parkingRepository.deleteById(id);
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());
        //parkingMap.replace(id, parking);
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public Parking checkOut(String id){
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);
        return parking;
    }
}
