package com.example.f1recyclerview_ergast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f1recyclerview_ergast.adapters.ItemAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)

        // Adapter class is initialized and list is passed in as the parameter.
        var itemAdapter = ItemAdapter(this, getItemsList())

        // Adapter instance is set to the RecyclerView to inflate the items.
        recycler_view_items.adapter = itemAdapter

        recyclerView = findViewById(R.id.recycler_view_items)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemAdapter = ItemAdapter(this, ArrayList())
        recyclerView.adapter = itemAdapter

        fetchPitStopsData()

    }

    /**
     * Pulls the data from the API and also hands errors and updates the user
     */
    private fun fetchPitStopsData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://ergast.com/api/f1/current/pitstops.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Error fetching data!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body()?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(responseString, PitStopResponse::class.java)
                val pitStops = data.data.raceTable.races.first().pitStops
                runOnUiThread {
                    itemAdapter.updateData(pitStops)
                }
            }
        })
    }

    /**
     *  Function is used to get the Items List which is added in the List
     */
    private fun getItemsList() : ArrayList<PitStop> {
        val list = ArrayList<PitStop>()

        /**
         * list.add(DataModel("Item 1"))
         */

        return list
    }
}