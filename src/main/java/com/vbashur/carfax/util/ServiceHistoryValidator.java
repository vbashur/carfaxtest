package com.vbashur.carfax.util;

import com.vbashur.carfax.domain.ImmutableRecord;
import com.vbashur.carfax.domain.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServiceHistoryValidator {

    public static List<Record> checkOdometerRollback(List<Record> serviceRecords) {
        if (Objects.isNull(serviceRecords) || serviceRecords.size() <= 1) return serviceRecords;
        List<Record> orderedServiceRecords = serviceRecords.stream().sorted(Record.getHistoryOrderComparator()).collect(Collectors.toList());
        List<Record> checkedRecords = new LinkedList<>();
        checkedRecords.add(orderedServiceRecords.get(0));
        for (int iter = 1; iter < orderedServiceRecords.size(); ++iter) {
            ImmutableRecord.Builder recordBuilder = ImmutableRecord.builder().from(orderedServiceRecords.get(iter));
            if (orderedServiceRecords.get(iter).getOdometerReading() < orderedServiceRecords.get(iter - 1).getOdometerReading()) {
                recordBuilder.odometerRollback(true);
            }
            checkedRecords.add(recordBuilder.build());
        }
        return checkedRecords;
    }
}
