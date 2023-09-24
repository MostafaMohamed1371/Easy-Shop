package com.example.e_commerce.presention.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.e_commerce.MainActivity
import com.example.e_commerce.data.remoteRetrofit.productCategory.CategoryList
import com.example.e_commerce.data.remoteRetrofit.productItems.Items
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.presention.adapter.ItemProductAdapter
import com.example.e_commerce.presention.viewModel.ProductViewModel
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var  homeMvvm: ProductViewModel
    private lateinit var itemsAdapter: ItemProductAdapter





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm=(activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getItems()
        observeItemOne()
        homeMvvm.getCategories()
        observeCategory()
        onTabSelected()
        prepareItemsRecyclerView()
        binding.imageSearch.setOnClickListener {
            findNavController().navigate(com.example.e_commerce.R.id.action_homeFragment_to_searchFragment)

        }

        onProductItemClick()
        var modeTheme=true
        binding.modeTheme.setOnClickListener {
            if (modeTheme){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                modeTheme=!modeTheme
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }

        }


    }


    private fun onProductItemClick() {
        itemsAdapter.onItemClickMeal={
            val bundle=Bundle().apply {
                putSerializable("productHome",it)
            }
            findNavController().navigate(com.example.e_commerce.R.id.action_homeFragment_to_detailFragment,bundle)
        }

    }


    private fun observeCategory() {
        homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner,object : Observer<CategoryList?> {
            override fun onChanged(t: CategoryList?) {
                addSourceTab(t!!)


            }

        })
    }

    private fun observeCategoryItems() {
        homeMvvm.observeCategoryItemsLiveData().observe(viewLifecycleOwner,object : Observer<List<Items?>> {
            override fun onChanged(t: List<Items?>) {

                itemsAdapter.setItems(t as ArrayList<Items>)


            }

        })
    }


    private fun prepareItemsRecyclerView() {
        itemsAdapter= ItemProductAdapter()
        binding.itemProduct.apply {
            layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            adapter=itemsAdapter
        }
    }




    private fun observeItem() {
        homeMvvm.observeitemsLiveData().observe(viewLifecycleOwner,object : Observer<List<Items?>> {
            override fun onChanged(t: List<Items?>) {
             itemsAdapter.setItems(t as ArrayList<Items>)

            }

        })
    }



    private fun observeItemOne() {
        homeMvvm.observeitemsLiveData().observe(viewLifecycleOwner,object : Observer<List<Items?>> {
            override fun onChanged(t: List<Items?>) {

                Glide.with(this@HomeFragment)
                    .load(t!!.get(5)!!.thumbnail)
                    .into(binding.imageItem)
                binding.title.text=t.get(5)!!.title
                binding.description.text=t.get(5)!!.description
                binding.category.text=t.get(5)!!.category

            }

        })
    }



    // when tab selected what will show
    private fun onTabSelected() {
        binding.tableLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    homeMvvm.getCategoriesItems(tab!!.text.toString())
                    observeCategoryItems()



                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    observeItem()
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    homeMvvm.getCategoriesItems(tab!!.text.toString())
                    observeCategoryItems()



                }
            }
        )


    }

    // add tab in tab Layout
    private fun addSourceTab(sources: CategoryList) {
        sources?.forEach { source ->
            val tab = binding.tableLayout.newTab()
            tab.text = source
            tab.tag = source
            binding.tableLayout.addTab(tab)


        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }




}