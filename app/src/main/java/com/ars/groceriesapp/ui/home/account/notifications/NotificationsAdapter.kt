package com.ars.groceriesapp.ui.home.account.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ars.groceriesapp.databinding.NotificationItemBinding

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.NotificationsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsHolder =
        NotificationsHolder(
            NotificationItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: NotificationsHolder, position: Int) {

    }

    override fun getItemCount(): Int = 7

    inner class NotificationsHolder(private val binding: NotificationItemBinding): ViewHolder(binding.root){

    }


}


