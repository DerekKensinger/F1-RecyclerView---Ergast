package com.example.f1recyclerview_ergast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f1recyclerview_ergast.adapters.ItemAdapter
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PitStopService {
    @GET("f1/{season}/pitstops.json")
    suspend fun getPitStops(@Path("season") season: String): PitStopResponse
}

/**
 * Create a Retrofit instance and use it to create an instance of the interface.
 */
val retrofit = Retrofit.Builder()
    .baseUrl("http://ergast.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

@OptIn(DelicateCoroutinesApi::class)
fun fetchPitStops(adapter: ItemAdapter, service: PitStopService) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = service.getPitStops("2021")
            val pitStops = response.data.raceTable.races.first().pitStops
            launch(Dispatchers.Main) {
                adapter.updateData(pitStops)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
