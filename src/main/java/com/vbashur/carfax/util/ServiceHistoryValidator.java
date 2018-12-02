package com.vbashur.carfax.util;

import com.vbashur.carfax.domain.ImmutableRecord;
import com.vbashur.carfax.domain.Record;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ServiceHistoryValidator {

    public List<Record> checkOdometerRollback(List<Record> serviceRecords) {
        if (Objects.isNull(serviceRecords) || serviceRecords.size() <= 1) return serviceRecords;
        Collections.sort(serviceRecords, Record.getHistoryOrderComparator());
        List<Record> checkedRecords = new LinkedList<>();
        checkedRecords.add(serviceRecords.get(0));
        for (int iter = 1; iter < serviceRecords.size(); ++iter) {
            ImmutableRecord.Builder recordBuilder = ImmutableRecord.builder().from(serviceRecords.get(iter));
            if (serviceRecords.get(iter).odometerReading() < serviceRecords.get(iter - 1).odometerReading()) {
                recordBuilder.odometerRollback(true);
            }
            checkedRecords.add(recordBuilder.build());
        }
        return checkedRecords;
    }
}
