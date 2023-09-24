package com.example.e_commerce.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.MainActivity
import com.example.e_commerce.databinding.FragmentSearchBinding
import com.example.e_commerce.presention.adapter.SearchAdapter
import com.example.e_commerce.presention.viewModel.ProductViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var  searchMvvm: ProductViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchMvvm=(activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PrepareAdapter()
        binding.arrowsearch.setOnClickListener {
            searchProduct()
        }

        observeSearchLiveData()

        var searchJob: Job?=null
        binding.boxsearch.addTextChangedListener{search->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(250)
                searchMvvm.getSearch(search.toString())
            }
        }

        onProductItemClick()

    }

    private fun onProductItemClick() {
        searchAdapter.onItemClick={
            val bundle=Bundle().apply {
                putSerializable("productHome",it)
            }
            findNavController().navigate(com.example.e_commerce.R.id.action_searchFragment_to_detailFragment,bundle)
        }

    }

    private fun observeSearchLiveData() {
        searchMvvm.observeSearchProductLiveData().observe(viewLifecycleOwner, Observer{
                productList->searchAdapter.differ.submitList(productList)
        })    }

    private fun searchProduct() {
        val search=binding.boxsearch.text.toString()
        if (search.isNotEmpty()){
            searchMvvm.getSearch(search)
        }    }

    private fun PrepareAdapter() {
        searchAdapter= SearchAdapter()
        binding.searchrecyclerView.apply {
            layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            adapter=searchAdapter
        }    }
}