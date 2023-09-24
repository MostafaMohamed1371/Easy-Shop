package com.example.e_commerce.presention.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.data.remoteRetrofit.productItems.Items
import com.example.e_commerce.databinding.OrderItemBinding
import com.example.little_lemon.utils.Helperss


class CartAdapter (): RecyclerView.Adapter<CartAdapter.MyviewHolder>(){

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
        return MyviewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        var currentItem=differ.currentList[position]
        Glide.with(holder.itemView)
            .load(differ.currentList[position].thumbnail)
            .into(holder.binding.imageMeal)
        holder.binding.brand.text=differ.currentList[position].brand
        holder.binding.title.text=differ.currentList[position].title
        holder.binding.description.text=differ.currentList[position].description
        holder.binding.price.text= differ.currentList[position].price.toString() + "\$"
        holder.binding.totalPrice.text="Total price : "+differ.currentList[position].price+"\$"
        holder.binding.Number.text= differ.currentList[position].counter.toString()
        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }



        if (onImagePlusCLickListener!=null){

            holder.binding.imagePlus.setOnClickListener {


                differ.currentList[position].counter = differ.currentList[position].counter!! + 1
                holder.binding.Number.text = differ.currentList[position].counter.toString()
                differ.currentList[position].total_Price =
                    differ.currentList[position].counter!! * differ.currentList[position].price!!.toInt()
                holder.binding.totalPrice.text =
                    "Total price : " + differ.currentList[position].total_Price + "\$"
                Log.e("aaaa", differ.currentList[position].total_Price.toString())
                // supTotalPrice+= differ.currentList[position].total_Price


                onImagePlusCLickListener.onItemClicked(currentItem, position)

            }


        }

        if (onImageMinusCLickListener!=null){
            holder.binding.imageMinus.setOnClickListener {
                //  onItemClickMinus.invoke(differ.currentList[position])
                if (differ.currentList[position].counter!! > 1) {
                    differ.currentList[position].counter = differ.currentList[position].counter!! - 1
                    holder.binding.Number.text = differ.currentList[position].counter.toString()
                    differ.currentList[position].total_Price =
                        differ.currentList[position].counter!! * differ.currentList[position].price!!.toInt()
                    holder.binding.totalPrice.text =
                        "Total price : " + differ.currentList[position].total_Price + "\$"

                    onImageMinusCLickListener.onItemClicked(currentItem, position)
                }

            }
        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class  MyviewHolder(var binding:OrderItemBinding ) : RecyclerView.ViewHolder(binding.root)


    lateinit var onImagePlusCLickListener: OnClickListener
    lateinit var onImageMinusCLickListener: OnClickListener
    interface OnClickListener{
        fun onItemClicked(item: Items?,position: Int)
    }

}


