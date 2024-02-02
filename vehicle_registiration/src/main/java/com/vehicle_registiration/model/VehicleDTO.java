package com.vehicle_registiration.model;

public interface VehicleDTO {

     //bu projede kullanmadım ancak genelde sorrgu sonuçlarını entity ile döndürmek yerine
     //işimize yarayan verileri içeren bir DTO objesi ile döndürmek daha verimli olduğu için tercih edilmelidir.

     String getLicensePlate();
     Long getcompanyId();

}
