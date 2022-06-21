package com.martin.simpleimagecaching.presentation.internal_storage_caching.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.martin.simpleimagecaching.R
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry
import com.martin.simpleimagecaching.feature_entries.domain.util.InternalStoragePhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class InternalStorageCachingAdapter: RecyclerView.Adapter<InternalStorageCachingAdapter.MyViewHolder>(){

    private var entry : List<InternalStoragePhoto> = emptyList()

    private val _clickedEvent = MutableSharedFlow<String>()
    val clickedEvent : SharedFlow<String> = _clickedEvent.asSharedFlow()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout_internal_storage, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.name.text = entry[position].name
            holder.path.text = entry[position].path
            holder.image.load(entry[position].bmp)
            holder.imageButton.setOnClickListener {
                Toast.makeText(holder.itemView.context,"image deleted" , Toast.LENGTH_SHORT ).show()
                if (position == 0) {
                    deleteItem(position , holder)
                }
                CoroutineScope(Dispatchers.IO).launch {
                    _clickedEvent.emit(entry[position].name)
                }

        }


    }


    override fun getItemCount(): Int {
        return entry.size
    }



    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.firstName_txt)
        val path: TextView = itemView.findViewById(R.id.lastName_txt)
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val imageButton : ImageButton = itemView.findViewById(R.id.imageButton)
    }

    fun setData(entry: List<InternalStoragePhoto>){
        this.entry = entry
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int , holder : RecyclerView.ViewHolder) {
        holder.itemView.visibility = View.GONE
        notifyItemRemoved(position)
    }
}