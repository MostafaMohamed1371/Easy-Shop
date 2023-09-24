package com.example.e_commerce.presention.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.data.remoteRetrofit.productItems.Items
import com.example.e_commerce.databinding.ItemProductBinding

class ItemProductAdapter : RecyclerView.Adapter<ItemProductAdapter.MyviewHolder>(){
    private var itemsList=ArrayList<Items>()
    lateinit var onItemClickMeal:((Items)->Unit)
    lateinit var context: Context
    fun setItems(productList:ArrayList<Items>){
        this.itemsList=productList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        return MyviewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(itemsList[position].thumbnail)
            .into(holder.binding.imageMeal)
        holder.binding.brand.text=itemsList[position].brand
        holder.binding.title.text=itemsList[position].title
        holder.binding.description.text=itemsList[position].description
        holder.binding.price.text= itemsList[position].price.toString() + "\$"
        holder.binding.rating.text= itemsList[position].rating.toString()
        holder.binding.discountPercentage.text= itemsList[position].discountPercentage.toString()+ "%"


//        holder.itemView.setOnClickListener {
//            onItemClick.invoke(mealsList[position])
//        }

        holder.itemView.setOnClickListener {
            onItemClickMeal.invoke(itemsList[position])
        }


    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    class  MyviewHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

}




