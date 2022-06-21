package com.martin.simpleimagecaching.presentation.room_database_caching.component

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry
import com.martin.simpleimagecaching.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RoomAdapter: RecyclerView.Adapter<RoomAdapter.MyViewHolder>(){

    private var entry = emptyList<Entry>()

    private val _clickedEvent = MutableSharedFlow<Entry>()
    val clickedEvent : SharedFlow<Entry> = _clickedEvent.asSharedFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.firstName.text = entry[position].data1
        holder.lastName.text = entry[position].size
        holder.image.load(entry[position].image)
        holder.imageButton.setOnClickListener {
            Toast.makeText(holder.itemView.context,"image with ${entry[position].size} deleted" , Toast.LENGTH_SHORT ).show()
            CoroutineScope(Dispatchers.IO).launch {
                Log.i("Item" , entry[position].size)
                _clickedEvent.emit(Entry(entry[position].id,entry[position].image,entry[position].data1,entry[position].size))
            }

        }
    }

    override fun getItemCount(): Int {
        return entry.size
    }



    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.firstName_txt)
        val lastName: TextView = itemView.findViewById(R.id.lastName_txt)
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val imageButton: ImageButton = itemView.findViewById(R.id.imageButton)
    }

    fun setData(entry: List<Entry>){
       this.entry = entry
        notifyDataSetChanged()
    }
}