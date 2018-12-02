package com.vbashur.carfax;

import com.vbashur.carfax.domain.ImmutableRecord;
import com.vbashur.carfax.domain.ImmutableRecords;
import com.vbashur.carfax.domain.Record;
import com.vbashur.carfax.domain.Records;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Record r1 = ImmutableRecord.builder()
                .vin("vin")
                .date("Date")
                .dataProviderId(1)
                .odometerReading(1)
                .addServiceDetails("s1")
                .build();


        Record r2 = ImmutableRecord.builder()
                .vin("vin")
                .date("Date")
                .dataProviderId(1)
                .odometerReading(1)
                .addServiceDetails("s2","s3")
                .odometerRollback(true)
                .build();
        System.out.println( "r1: " + r1 );
        System.out.println( "r2: " + r2 );
        System.out.println( "r2.equals(r1): " + r2.equals(r1) );
        System.out.println( "r1 hash: " + r1.hashCode() );
        System.out.println( "r2 hash: " + r2.hashCode() );

        Records rs1 = ImmutableRecords.builder()
                .addRecords(r1)
                //.records(r1)
                .build();
        Records rs2 = ImmutableRecords.builder()
                //.records(null)
                .addRecords(r2)
                .build();
        System.out.println( "rs1: " + rs1 );
        System.out.println( "rs2: " + rs2 );
    }
}
