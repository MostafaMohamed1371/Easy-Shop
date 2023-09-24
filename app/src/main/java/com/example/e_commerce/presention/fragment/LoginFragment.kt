package com.example.e_commerce.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    @Inject
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener{_->

            signIN()
        }
        binding.Register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
        binding.forgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)

        }

    }

    fun signIN(){
        val email=binding.editTextTextEmailAddress.text.toString().trim()
        val pass=binding.editTextTextPassword.text.toString().trim()

        if (email.isNotEmpty()&&pass.isNotEmpty()) {
            binding.progressBarLogin.visibility= View.VISIBLE
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.progressBarLogin.visibility= View.GONE

                    StyleableToast.makeText(requireActivity(),"sucess login",R.style.exampleToast).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                } else {
                    binding.progressBarLogin.visibility= View.GONE

                    StyleableToast.makeText(requireActivity(),it.exception!!.localizedMessage.toString(),R.style.exampleToast).show()

                }
            }
        }else{
            binding.progressBarLogin.visibility= View.GONE
            if (email.isEmpty()){

                binding.editTextTextEmailAddress.setError("emial is empty")
            }
            if (pass.isEmpty()) {

                binding.editTextTextPassword.setError("password is empty")
            }



        }
    }

}