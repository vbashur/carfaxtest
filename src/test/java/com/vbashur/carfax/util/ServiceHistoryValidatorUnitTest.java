package com.vbashur.carfax.util;

import com.vbashur.carfax.domain.ImmutableRecord;
import com.vbashur.carfax.domain.Record;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.vbashur.carfax.util.ServiceHistoryValidator.*;

public class ServiceHistoryValidatorUnitTest {

    @Test
    public void emptyServiceHistoryTest() {
        List<Record> noRecords = Collections.EMPTY_LIST;
        List<Record> checkedRecords = checkOdometerRollback(noRecords);
        Assert.assertNotNull(checkedRecords);
        Assert.assertTrue(checkedRecords.size() == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongRecordDateFormatTest() {
        List<Record> records = new LinkedList<>();
        Record wrongDateRecord1 = ImmutableRecord.builder()
                .dataProviderId(0)
                .date(UUID.randomUUID().toString())
                .odometerReading(0)
                .vin(UUID.randomUUID().toString())
                .build();
        Record wrongDateRecord2 = ImmutableRecord.builder()
                .dataProviderId(0)
                .date(UUID.randomUUID().toString())
                .odometerReading(0)
                .vin(UUID.randomUUID().toString())
                .build();
        records.add(wrongDateRecord1);
        records.add(wrongDateRecord2);
        List<Record> checkedRecords = checkOdometerRollback(records);
        Assert.assertNull(checkedRecords);
    }

    @Test
    public void odometerRollbackTest() {
        List<Record> records = new LinkedList<>();
        Record record2018 = ImmutableRecord.builder()
                .odometerReading(5000)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2018-01-01")
                .addServiceDetails("2018")
                .build();
        Record record2014 = ImmutableRecord.builder()
                .odometerReading(1000)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2014-01-01")
                .addServiceDetails("2014")
                .build();
        Record record2016 = ImmutableRecord.builder()
                .odometerReading(9999)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2016-01-01")
                .addServiceDetails("2016")
                .build();
        records.add(record2018);
        records.add(record2014);
        records.add(record2016);
        List<Record> checkedRecords = checkOdometerRollback(records);
        Assert.assertNotNull(checkedRecords);
        Assert.assertEquals(3, checkedRecords.size());
        Assert.assertEquals(record2014, checkedRecords.get(0));
        Assert.assertNull(checkedRecords.get(0).odometerRollback());
        Assert.assertEquals(record2016, checkedRecords.get(1));
        Assert.assertNull(checkedRecords.get(1).odometerRollback());
        Assert.assertTrue(checkedRecords.get(2).odometerRollback());
    }

    @Test
    public void validOdometerTest() {
        List<Record> records = new LinkedList<>();
        Record record2018 = ImmutableRecord.builder()
                .odometerReading(9999)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2018-01-01")
                .addServiceDetails("2018")
                .build();
        Record record2014 = ImmutableRecord.builder()
                .odometerReading(1000)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2014-01-01")
                .addServiceDetails("2014")
                .build();
        Record record2016 = ImmutableRecord.builder()
                .odometerReading(5000)
                .dataProviderId(0)
                .vin(UUID.randomUUID().toString())
                .date("2016-01-01")
                .addServiceDetails("2016")
                .build();
        records.add(record2018);
        records.add(record2014);
        records.add(record2016);
        List<Record> checkedRecords = checkOdometerRollback(records);
        Assert.assertNotNull(checkedRecords);
        Assert.assertEquals(3, checkedRecords.size());
        Assert.assertEquals(record2014, checkedRecords.get(0));
        Assert.assertNull(checkedRecords.get(0).odometerRollback());
        Assert.assertEquals(record2016, checkedRecords.get(1));
        Assert.assertNull(checkedRecords.get(1).odometerRollback());
        Assert.assertEquals(record2018, checkedRecords.get(2));
        Assert.assertNull(checkedRecords.get(2).odometerRollback());
    }
}

