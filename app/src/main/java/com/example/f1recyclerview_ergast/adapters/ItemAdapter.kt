package com.example.f1recyclerview_ergast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.f1recyclerview_ergast.DataModel
import com.example.f1recyclerview_ergast.PitStop
import com.example.f1recyclerview_ergast.R
import kotlinx.android.synthetic.main.item_custom_row.view.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ItemAdapter(val context: Context, val items: ArrayList<PitStop>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_custom_row,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items of the given type.
     * You can either create a new View manually or inflate it from an XML layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = items[position]

        if (holder is ViewHolder) {
            holder.tvItem.text = item.driverId

            //Updating the background color according to the odd/even positions in list.
            if (position % 2 == 0) {
                holder.cardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorLightGray
                    )
                )
            } else {
                holder.cardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorWhite
                    )
                )
            }
        }
    }

    /**
     *  override fun getItemViewType(position: Int): Int{
    return items[position].viewType
    }
     */

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Holds the TextView that we will add each item to
        val tvItem = view.tv_item_name
        val cardViewItem = view.card_view_item
    }

    /**
     * Updates the adapter data with new items
     */
    fun updateData(newItems: List<PitStop>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    fun loadData() {
        val client = OkHttpClient()
        val url = "http://ergast.com/api/f1/pitstops/2021/1.json"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure to retrieve data
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()
                // Parse JSON and update RecyclerView
            }
        })
    }
}
