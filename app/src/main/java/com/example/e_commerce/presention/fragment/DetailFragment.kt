package com.example.e_commerce.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.e_commerce.MainActivity
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentDetailBinding
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.presention.viewModel.ProductViewModel
import com.example.little_lemon.utils.Helperss
import io.github.muddz.styleabletoast.StyleableToast


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var  detailMvvm: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailMvvm=(activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args:DetailFragmentArgs by navArgs()
        val productHome=args.productHome
        Glide.with(this)
            .load(productHome.thumbnail)
            .into(binding.imagebar)
        binding.collapsingToolbar.title=productHome.title
        binding.textBrand.text="${productHome.brand}"
        binding.textPrice.text="${productHome.price}$"
        binding.description.text=productHome.description
        binding.rating.text= productHome.rating.toString()
        binding.discountPercentage.text= productHome.discountPercentage.toString()+ "%"

        binding.btnAddFav.setOnClickListener {
            productHome.let {product->
                if (product != null) {
                    detailMvvm.upinsertProduct(product)
                    StyleableToast.makeText(requireActivity(),"product saved",R.style.exampleToast).show()
                    Helperss.supTotalPrice=0
                    Helperss.finalTotal=20

                }

            }

        }




    }

}