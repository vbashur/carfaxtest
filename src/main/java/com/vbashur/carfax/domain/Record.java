package com.vbashur.carfax.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.Nullable;
import org.immutables.value.Value;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableRecord.class)
@JsonDeserialize(as = ImmutableRecord.class)
public interface Record {

    String RECORD_DATE_FORMAT = "YYYY-MM-DD";

    @JsonProperty("vin")
    @JsonPropertyDescription("Vehicle identification number")
    String vin();

    @JsonProperty("date")
    @JsonPropertyDescription("Date following format 'YYYY-MM-DD'")
    String date();

    @JsonProperty("data_provider_id")
    @JsonPropertyDescription("Data provider id")
    Integer dataProviderId();

    @JsonProperty("odometer_reading")
    @JsonPropertyDescription("Odometer reading in KM")
    Integer odometerReading();

    @JsonProperty("service_details")
    @JsonPropertyDescription("List of service details e.g Oil changed, Tires rotated, etc")
    List<String> serviceDetails();

    @Nullable
    @JsonProperty("odometer_rollback")
    @JsonPropertyDescription("Odometer no longer grows in an ascending manner")
    Boolean odometerRollback();

    static Comparator<Record> getHistoryOrderComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record record1, Record record2) {
                DateFormat df = new SimpleDateFormat(RECORD_DATE_FORMAT);
                try {
                    Date rec1Date = df.parse(record1.date());
                    Date rec2Date = df.parse(record2.date());
                    return rec1Date.compareTo(rec2Date);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Unable to parse record date", e);
                }
            }
        };
    }
}
