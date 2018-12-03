package com.vbashur.carfax.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

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
    String getVin();

    @JsonProperty("date")
    @JsonPropertyDescription("Date following format 'YYYY-MM-DD'")
    String getDate();

    @JsonProperty("data_provider_id")
    @JsonPropertyDescription("Data provider id")
    Integer getDataProviderId();

    @JsonProperty("odometer_reading")
    @JsonPropertyDescription("Odometer reading in KM")
    Integer getOdometerReading();

    @JsonProperty("service_details")
    @JsonPropertyDescription("List of service details e.g Oil changed, Tires rotated, etc")
    List<String> getServiceDetails();

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("odometer_rollback")
    @JsonPropertyDescription("Odometer no longer grows in an ascending manner")
    Boolean getOdometerRollback();

    static Comparator<Record> getHistoryOrderComparator() {
        return (Record record1, Record record2) -> {
            DateFormat df = new SimpleDateFormat(RECORD_DATE_FORMAT);
            try {
                Date rec1Date = df.parse(record1.getDate());
                Date rec2Date = df.parse(record2.getDate());
                return rec1Date.compareTo(rec2Date);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse record date", e);
            }
        };
    }
}
