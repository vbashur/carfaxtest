package com.vbashur.carfax.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableRecords.class)
@JsonDeserialize(as = ImmutableRecords.class)
public interface Records {

    @JsonProperty("records")
    List<Record> getRecords();
}
