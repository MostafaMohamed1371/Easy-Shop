package com.example.e_commerce.presention.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.data.remoteRetrofit.productItems.Items
import com.example.e_commerce.databinding.ItemProductBinding


class SearchAdapter (): RecyclerView.Adapter<SearchAdapter.MyviewHolder>(){

    lateinit var onItemClick:((Items)->Unit)
    private val diffUtil=object : DiffUtil.ItemCallback<Items>(){

        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {

            return oldItem.id==newItem.id

        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        return MyviewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(differ.currentList[position].thumbnail)
            .into(holder.binding.imageMeal)
        holder.binding.brand.text=differ.currentList[position].brand
        holder.binding.title.text=differ.currentList[position].title
        holder.binding.description.text=differ.currentList[position].description
        holder.binding.price.text= differ.currentList[position].price.toString() + "\$"
        holder.binding.rating.text= differ.currentList[position].rating.toString()
        holder.binding.discountPercentage.text= differ.currentList[position].discountPercentage.toString()+ "%"
        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class  MyviewHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

}


