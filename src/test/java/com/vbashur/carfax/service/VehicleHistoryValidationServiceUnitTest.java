package com.vbashur.carfax.service;

import com.vbashur.carfax.domain.ImmutableRecord;
import com.vbashur.carfax.domain.ImmutableRecords;
import com.vbashur.carfax.domain.Record;
import com.vbashur.carfax.domain.Records;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class VehicleHistoryValidationServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VehicleHistoryValidationService validationService;

    @Test
    public void testEmptyVin() {
        Assert.assertNull(validationService.getValidatedServiceHistory(null));
    }

    @Test
    public void testNoRecordFound() {
        String vin = UUID.randomUUID().toString();
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Records.class.getClass()))).thenReturn(null);
        Assert.assertNull(validationService.getValidatedServiceHistory(vin));
    }

    @Test
    public void testValidRecordsFound() {
        Record record2018 = ImmutableRecord.builder()
                .odometerReading(5000)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2018-01-01")
                .addServiceDetails("2018")
                .build();
        Records expectedRecords = ImmutableRecords.builder()
                .addRecords(record2018)
                .build();
        String vin = UUID.randomUUID().toString();
        String externalServiceUrl = VehicleHistoryValidationService.VIN_VALIDATION_ENDPOINT_URL + vin;
        Mockito.when(restTemplate.getForObject(externalServiceUrl, Records.class)).thenReturn(expectedRecords);
        Records validatedRecords = validationService.getValidatedServiceHistory(vin);
        Assert.assertNotNull(validatedRecords);
        Assert.assertEquals(expectedRecords, validatedRecords);
    }
}
