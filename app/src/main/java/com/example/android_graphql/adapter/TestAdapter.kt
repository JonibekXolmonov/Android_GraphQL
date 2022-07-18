package com.example.android_graphql.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_graphql.UsersListQuery
import com.example.android_graphql.databinding.LayoutItemBinding

class TestAdapter(
    val list: List<UsersListQuery.User>?,
    val onClick: ((Any) -> Unit)
) :
    RecyclerView.Adapter<TestAdapter.VH>() {

    inner class VH(private val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersListQuery.User) {
            binding.apply {
                tvName.text = user.name
                tvRocket.text = user.rocket
                tvTime.text = user.timestamp.toString()
                tvTwitter.text = user.twitter

                linear.setOnClickListener {
                    onClick.invoke(user.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(list!![position])

    override fun getItemCount(): Int = list!!.size

}