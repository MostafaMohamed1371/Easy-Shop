package com.example.e_commerce.presention.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import javax.inject.Inject

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
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
        binding=FragmentForgetPasswordBinding.inflate(inflater,container,false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ResetPassword.setOnClickListener {
            ResetPassword()
        }
    }


    private fun ResetPassword() {
        val email = binding.editTextTextEmailAddress2.text.toString().trim()
        if (email.isEmpty()) {
            //  StyleableToast.makeText(this, "Email is empty", R.style.exampleToast).show()
            binding.editTextTextEmailAddress2.setError("Email is empty")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            StyleableToast.makeText(requireActivity(), "Please provide valid email!", R.style.exampleToast).show()
        } else {
            binding.progressBar.visibility = View.VISIBLE
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    StyleableToast.makeText(requireActivity(),
                        "Check your email to reset your password",
                        R.style.exampleToast).show()
                } else {
                    binding.progressBar.visibility = View.GONE
                    StyleableToast.makeText(requireActivity(),
                        "Try again something wrong happen",
                        R.style.exampleToast).show()
                }
            }
        }
    }

}