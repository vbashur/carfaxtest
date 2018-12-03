package com.vbashur.carfax.controller;


import com.vbashur.carfax.domain.Records;
import com.vbashur.carfax.service.VehicleHistoryValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicle/")
public class VehicleController extends AbstractBaseController {

    @Autowired
    private VehicleHistoryValidationService vehicleHistoryValidationService;

    @RequestMapping(value = "/history/{vin}", method = RequestMethod.GET)
    public Records getVehicleHistory(@PathVariable String vin) {
        return vehicleHistoryValidationService.getValidatedServiceHistory(vin);
    }
}
