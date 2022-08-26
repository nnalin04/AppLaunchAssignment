package com.example.applaunchassignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.applaunchassignment.R
import com.example.applaunchassignment.data.model.User
import com.example.applaunchassignment.databinding.UserViewBinding
import com.example.applaunchassignment.ui.RecyclerViewClickListener

class UserAdapter constructor(
    var data : List<User>,
    var listener : RecyclerViewClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate<UserViewBinding>(
                LayoutInflater.from(parent.context),
                R.layout.user_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        with(holder) {
            with(data[position]) {
                userViewBinding.name.text = this.firstName + " " + this.lastName
                userViewBinding.email.text = this.email
                userViewBinding.itemCard.setOnClickListener{
                    listener.onRecyclerViewItemClick(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun loadData(data: List<User>) {
        this.data = data
        notifyDataSetChanged()
    }

    class UserViewHolder constructor(
        val userViewBinding: UserViewBinding
    ) : RecyclerView.ViewHolder(userViewBinding.root){

    }
}