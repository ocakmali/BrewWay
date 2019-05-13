package com.ocakmali.brewway.addeditrecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ocakmali.brewway.R
import com.ocakmali.brewway.datamodel.TimestampView
import java.util.concurrent.TimeUnit

class AddEditTimestampAdapter(private val actionListner: ActionListener)
    : ListAdapter<TimestampView, AddEditTimestampAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface ActionListener {
        fun onTextClick(timestamp: TimestampView)
        fun onDeleteClick(item: TimestampView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context)
                .inflate(R.layout.item_timestamp, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timestampView = getItem(position) ?: return
        holder.bind(timestampView, actionListner)
    }

    override fun submitList(list: List<TimestampView>?) {
        super.submitList(list?.let { ArrayList(list) })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTextView: TextView = view.findViewById(R.id.tv_note)
        private val delete: ImageButton = view.findViewById(R.id.btn_delete)
        fun bind(timestampView: TimestampView, actionListener: ActionListener) {
            val formattedText = itemView.context
                    .getString(R.string.timestamp_formatted,
                            timestampView.time.minuteAndSecond(),
                            timestampView.note
                    )
            noteTextView.text = formattedText
            noteTextView.setOnClickListener { actionListener.onTextClick(timestampView) }
            delete.setOnClickListener {
                actionListener.onDeleteClick(timestampView)
            }
        }

        private fun Long.minuteAndSecond(): String {
            val minuteLong =  TimeUnit.MILLISECONDS.toMinutes(this)
            val secondLong = TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(minuteLong)
            return "$minuteLong:$secondLong"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TimestampView>() {
            override fun areItemsTheSame(oldItem: TimestampView, newItem: TimestampView): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TimestampView, newItem: TimestampView): Boolean {
                return oldItem == newItem
            }
        }
    }
}


