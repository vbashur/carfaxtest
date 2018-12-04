package com.vbashur.carfax.service;

import com.vbashur.carfax.domain.ImmutableRecords;
import com.vbashur.carfax.domain.Records;
import com.vbashur.carfax.util.ServiceHistoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class VehicleHistoryValidationService {

    private Logger logger = LoggerFactory.getLogger(VehicleHistoryValidationService.class);

    public final static String VIN_VALIDATION_ENDPOINT_URL = "https://s3-eu-west-1.amazonaws.com/coding-challenge.carfax.eu/";

    @Autowired
    private RestTemplate restTemplate;

    public Records getValidatedServiceHistory(String vin) {
        if (StringUtils.isEmpty(vin)) {
            logger.error("Empty VIN value");
            return null;
        }
        /*
        Another option is to use restTemplate.getForEntity - it'd help to handle response codes.
        As long as there are no requirements regarding error handling and for the sake of simplicity
        DefaultResponseErrorHandler is applied
         */
        Records fetchedVehicleRecords = restTemplate.getForObject(VIN_VALIDATION_ENDPOINT_URL + vin, Records.class);
        if (Objects.isNull(fetchedVehicleRecords)) {
            logger.error("No records found for VIN: " + vin);
            return null;
        }
        logger.debug(String.format("Fetched service history for VIN %s: %s", vin, fetchedVehicleRecords.toString()));
        return ImmutableRecords.builder()
                .from(fetchedVehicleRecords)
                .records(ServiceHistoryValidator.checkOdometerRollback(fetchedVehicleRecords.getRecords()))
                .build();
    }
}
