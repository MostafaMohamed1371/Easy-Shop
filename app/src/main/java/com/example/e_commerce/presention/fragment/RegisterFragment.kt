package com.example.e_commerce.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.example.movies2app.login.DataLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
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
        binding=FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Register.setOnClickListener {_->

            createUser()
        }


        binding.returnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }
    }



    fun createUser(){
        val userName=binding.editTextTextUserName.text.toString()
        val email=binding.editTextTextEmailAddress.text.toString()
        val pass=binding.editTextTextPassword.text.toString()
        val confirmPass=binding.editTextTextConfirmPassword.text.toString()

        if (email.isNotEmpty()&&pass.isNotEmpty()&&pass.equals(confirmPass)&&userName.isNotEmpty()) {
            binding.progressBarRegister.visibility= View.VISIBLE
            mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var Register= DataLogin(userName,email,pass)
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("Register")


                        myRef.child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(Register).addOnCompleteListener {
                            if (it.isSuccessful) {
                                StyleableToast.makeText(requireActivity(),"user has been registered successful",R.style.exampleToast).show()
                            }else{
                                StyleableToast.makeText(requireActivity(),"Failed to register! try again",R.style.exampleToast).show()
                            }
                        }


                        binding.progressBarRegister.visibility = View.GONE

                        StyleableToast.makeText(requireActivity(),"sucess add",R.style.exampleToast).show()
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)

                    } else {
                        binding.progressBarRegister.visibility = View.GONE

                        StyleableToast.makeText(requireActivity(),it.exception!!.localizedMessage.toString(),R.style.exampleToast).show()
                    }
                }
        }else{
            binding.progressBarRegister.visibility = View.GONE
            if (userName.isEmpty()){
                binding.editTextTextUserName.setError("userName is empty")
            }
            if (email.isEmpty()){

                binding.editTextTextEmailAddress.setError("emial is empty")
            }
            if (pass.isEmpty()) {

                binding.editTextTextPassword.setError("password is empty")
            }
            if (confirmPass.isEmpty()) {

                binding.editTextTextConfirmPassword.setError("confirmPassword is empty")
            }
            else if (confirmPass!==pass) {

                StyleableToast.makeText(requireActivity(),"confirmPassword is not egual password",R.style.exampleToast).show()
            }
        }
    }

}