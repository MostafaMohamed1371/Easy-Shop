package com.example.e_commerce.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentCheckOutBinding
import com.example.e_commerce.presention.payment.MainViewModel


class CheckOutFragment : Fragment() {

    lateinit var binding: FragmentCheckOutBinding
    val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCheckOutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Button1.setOnClickListener { getToken() }

        mainViewModel.move.observe(requireActivity())
        {
            if(it == true)
            {
                //startActivity(Intent(this,IframeActivity::class.java))
                findNavController().navigate(R.id.action_checkOutFragment_to_detailsCardFragment)
            }
        }
    }
    private fun getToken() {
        mainViewModel.getToken()
    }
}