package com.example.e_commerce.presention.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.MainActivity
import com.example.e_commerce.data.remoteRetrofit.productItems.Items

import com.example.e_commerce.databinding.FragmentCartBinding
import com.example.e_commerce.presention.adapter.CartAdapter
import com.example.e_commerce.presention.viewModel.ProductViewModel
import com.example.little_lemon.utils.Helperss
import com.google.android.material.snackbar.Snackbar


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartMvvm: ProductViewModel
    private lateinit var cartAdapter: CartAdapter
    private var deliveryPrice: Int = 20
    var subPrice: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartMvvm = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product = cartAdapter.differ.currentList[position]
                cartMvvm.deleteProduct(product)
                Helperss.supTotalPrice=0
                Helperss.finalTotal=20

                Snackbar.make(view, "product deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo")
                    {
                        cartMvvm.upinsertProduct(product)



                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewSave)
        PrepareAdapter()
        observeCartLiveData()
        onProductItemClick()
        binding.Checkout.setOnClickListener {
            findNavController().navigate(com.example.e_commerce.R.id.action_cartFragment_to_detailsCardFragment2)

        }

    }


    private fun onProductItemClick() {
        cartAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putSerializable("productHome", it)
            }
            findNavController().navigate(
                com.example.e_commerce.R.id.action_cartFragment_to_detailFragment,
                bundle
            )
        }

    }

    private fun observeCartLiveData() {
        cartMvvm.observeFavoriteProductsLiveData()
            .observe(viewLifecycleOwner, Observer { cartList ->
                cartAdapter.differ.submitList(cartList)

            })
    }

    private fun PrepareAdapter() {
        cartAdapter = CartAdapter()
        binding.recyclerViewSave.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter


        }

        cartAdapter.onImagePlusCLickListener = object : CartAdapter.OnClickListener {
            override fun onItemClicked(item: Items?, position: Int) {
                //val selectItem=CartAdapter.differ.currentList[position].total_Price
                Helperss.supTotalPrice += item!!.price!!.toInt()
//                Helperss.supTotalPrice = subPrice
//                binding.priceSubTotal.text = subPrice.toString() + "\$"
//                binding.priceTotal.text = (subPrice + deliveryPrice).toString() + "\$"
                Helperss.finalTotal = Helperss.supTotalPrice + deliveryPrice
                binding.priceSubTotal.text =  Helperss.supTotalPrice.toString() + "\$"
                binding.priceTotal.text =  Helperss.finalTotal.toString() + "\$"

            }

        }


        cartAdapter.onImageMinusCLickListener = object : CartAdapter.OnClickListener {
            override fun onItemClicked(item: Items?, position: Int) {
                Helperss.supTotalPrice -= item!!.price!!.toInt()
//                Helperss.supTotalPrice = subPrice
//                binding.priceSubTotal.text = subPrice.toString() + "\$"
//                binding.priceTotal.text = (subPrice + deliveryPrice).toString() + "\$"
                Helperss.finalTotal = Helperss.supTotalPrice + deliveryPrice

                binding.priceSubTotal.text = Helperss.supTotalPrice.toString() + "\$"
                binding.priceTotal.text = Helperss.finalTotal.toString() + "\$"


            }
        }
    }

    override fun onStart() {
        super.onStart()
//        binding.priceSubTotal.text = Helperss.supTotalPrice.toString() + "\$"
//        binding.priceTotal.text = (Helperss.supTotalPrice + deliveryPrice).toString() + "\$"
//        Helperss.finalTotal = Helperss.supTotalPrice + deliveryPrice
//        binding.priceSubTotal.text = Helperss.supTotalPrice.toString() + "\$"
//        binding.priceTotal.text = Helperss.finalTotal.toString() + "\$"
        binding.priceSubTotal.text = Helperss.supTotalPrice.toString() + "\$"
        binding.priceTotal.text = Helperss.finalTotal.toString() + "\$"
    }
}
