package com.example.f1recyclerview_ergast

import com.google.gson.annotations.SerializedName

data class PitStopResponse(
    @SerializedName("MRData") val data: MRData
)

data class MRData(
    @SerializedName("RaceTable") val raceTable: RaceTable
)

data class RaceTable(
    @SerializedName("Races") val races: List<Race>
)

data class Race(
    @SerializedName("PitStops") val pitStops: List<PitStop>
)

data class PitStop(
    @SerializedName("driverId") val driverId: String,
    @SerializedName("stop") val stop: Int,
    @SerializedName("lap") val lap: Int,
    @SerializedName("time") val time: String
)
