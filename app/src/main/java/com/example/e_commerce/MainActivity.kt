package com.example.e_commerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_commerce.databinding.ActivityMainBinding
import com.example.e_commerce.presention.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: ProductViewModel by lazy {
        ViewModelProvider(this)[ProductViewModel::class.java]

    }
    private val navController by lazy {
        Navigation.findNavController(this,R.id.sub_fragment)
    }

//     val viewModel by viewModels<ProductViewModel>()


    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navcontroller=findNavController(R.id.sub_fragment)
        binding.bottomNav.setupWithNavController(navcontroller)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.splashFragment||destination.id == R.id.loginFragment||destination.id == R.id.registerFragment||destination.id == R.id.forgetPasswordFragment) {
                binding.bottomNav.visibility= View.GONE
            }else{
                binding.bottomNav.visibility= View.VISIBLE
            }
        }
    }



}