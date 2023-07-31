package com.example.pendaftaranpondokpesantrenapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pendaftaranpondokpesantrenapp.R
import com.example.pendaftaranpondokpesantrenapp.model.Registration

class RegistrationListAdapter(
    private val onItemClickListener: (Registration) -> Unit
): ListAdapter<Registration, RegistrationListAdapter.RegistrationViewHolder>(WORDS_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        return RegistrationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        val registration = getItem(position)
        holder.bind(registration)
        holder.itemView.setOnClickListener {
            onItemClickListener(registration)
        }
    }

    class RegistrationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        private val parentTextView: TextView = itemView.findViewById(R.id.parentTextView)


        fun bind(registration: Registration) {
            nameTextView.text = registration?.name
            addressTextView.text = registration?.address
            phoneTextView.text = registration?.phone
            parentTextView.text = registration?.parent

        }

        companion object {
            fun create(parent: ViewGroup): RegistrationListAdapter.RegistrationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_registration, parent, false)
                return RegistrationViewHolder(view)
            }
        }
    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Registration>(){
            override fun areItemsTheSame(oldItem: Registration, newItem: Registration): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Registration, newItem: Registration): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}