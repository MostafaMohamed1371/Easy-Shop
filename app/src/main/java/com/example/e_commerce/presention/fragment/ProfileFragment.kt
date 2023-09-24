package com.example.e_commerce.presention.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProfileBinding
import com.example.movies2app.login.DataLogin
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    @Inject
    lateinit var user: FirebaseUser
    @Inject
    lateinit var reference: DatabaseReference
    @Inject
    lateinit var userId: String
    @Inject
    @Named("mStorage")
    lateinit var mStorage: StorageReference
    @Inject
    @Named("imageStorage")
    lateinit var imageStorage: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        referenceRealDb()
        updateProfile()
        getImageFromFirebase()
        onLogoutIconClick()
    }


    private fun onLogoutIconClick() {
        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            //val intent=Intent(activity,LoginActivity::class.java)
            //startActivity(intent)
            //activity?.finish()



        }
    }
    private fun getImageFromFirebase() {
        val file: File = File.createTempFile("image","jpeg")
        imageStorage.getFile(file).addOnSuccessListener {
            val  bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.imageProfile.setImageBitmap(bitmap)
        }.addOnFailureListener {
            StyleableToast.makeText(requireActivity(),"Image failed to load",
                R.style.exampleToast).show()
        }
    }

    private fun checkPassword():Boolean{
        if(binding.editTextTextPassword.text.toString()==""){
            StyleableToast.makeText(requireActivity(),"password is empty",
                R.style.exampleToast).show()
            return false
        }
        if(binding.editTextTextPassword.text.toString().length<6){
            StyleableToast.makeText(requireActivity(),"password is leaast than 6 character",
                R.style.exampleToast).show()
            return false
        }
        return true

    }

    private fun checkEmail():Boolean{
        val email=binding.editTextTextEmailAddress3.text.toString()
        if(email==""){
            StyleableToast.makeText(requireActivity(),"Email is empty",
               R.style.exampleToast).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            StyleableToast.makeText(requireActivity(), "Please provide valid email!", R.style.exampleToast).show()
            return false
        }
        return true

    }
    private fun checkUserName():Boolean{
        val userName=binding.editTextTextUserName.toString()
        if(userName==""){
            StyleableToast.makeText(requireActivity(),"userName is empty",
               R.style.exampleToast).show()
            return false
        }

        return true

    }
    lateinit var nameChange:String
    lateinit var emailChange:String
    lateinit var passChange:String
    private fun referenceRealDb() {
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var userProfile: DataLogin = snapshot.getValue(DataLogin::class.java)!!
                nameChange=snapshot.child("userName").getValue().toString()
                emailChange=snapshot.child("email").getValue().toString()
                passChange=snapshot.child("password").getValue().toString()
                if (userProfile!=null){
                    binding.editTextTextUserName.setText(userProfile.userName)
                    binding.editTextTextEmailAddress3.setText(userProfile.email)
                    binding.editTextTextPassword.setText(userProfile.password)

                }

            }


            override fun onCancelled(error: DatabaseError) {
                StyleableToast.makeText(activity!!,"sucess add", R.style.exampleToast).show()
            }

        })
    }

    private fun updateProfile() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Register")
        binding.UpdateProfile.setOnClickListener {
            if (binding.editTextTextUserName.text.toString()
                    .isEmpty() || binding.editTextTextEmailAddress3.text.toString()
                    .isEmpty() || binding.editTextTextPassword.text.toString().isEmpty()
            ) {
                StyleableToast.makeText(requireActivity(), "one or many field is empty",R.style.exampleToast).show()
            } else {

                if (checkUserName()) {
                    reference.child(FirebaseAuth.getInstance().currentUser!!.uid).child("userName")
                        .setValue(binding.editTextTextUserName.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                StyleableToast.makeText(requireActivity(),
                                    "update userName",
                                    R.style.exampleToast).show()
                            } else {
                                StyleableToast.makeText(requireActivity(),
                                    "failed update userName",
                                   R.style.exampleToast).show()
                            }
                        }
                }
                val user2 = FirebaseAuth.getInstance().currentUser
                val credential = EmailAuthProvider.getCredential(emailChange, passChange)
                user2!!.reauthenticate(credential).addOnCompleteListener { task: Task<Void?>? ->
                    val user1 = FirebaseAuth.getInstance().currentUser
                    if (checkEmail()) {
                        user1!!.updateEmail(binding.editTextTextEmailAddress3.text.toString())
                            .addOnCompleteListener { task1: Task<Void?> ->
                                if (task1.isSuccessful) {
                                    reference.child(user2.uid).child("email")
                                        .setValue(binding.editTextTextEmailAddress3.text.toString())
                                    StyleableToast.makeText(requireActivity(),
                                        "update email",
                                       R.style.exampleToast).show()
                                } else {
                                    StyleableToast.makeText(requireActivity(),
                                        "fail update email",
                                        R.style.exampleToast).show()
                                }
                            }
                    }
                    if (checkPassword()) {
                        user1!!.updatePassword(binding.editTextTextPassword.text.toString())
                            .addOnCompleteListener { task1: Task<Void?> ->
                                if (task1.isSuccessful) {
                                    reference.child(user2.uid).child("password")
                                        .setValue(binding.editTextTextPassword.text.toString())
                                    StyleableToast.makeText(requireActivity(),
                                        "update password",
                                       R.style.exampleToast).show()
                                } else {
                                    StyleableToast.makeText(requireActivity(),
                                        "fail update password",
                                       R.style.exampleToast).show()
                                }
                            }
                    }
                }



            }

        }

        binding.ChangePhoto.setOnClickListener {
            val intentImage = Intent(Intent.ACTION_PICK)
            intentImage.type = "image/*"
            startActivityForResult(intentImage, 1)


        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val uriImage = data?.data
            val filePath = mStorage.child(FirebaseAuth.getInstance().currentUser!!.uid)
            if (uriImage != null) {
                filePath.putFile(uriImage).addOnSuccessListener {
                    StyleableToast.makeText(requireActivity(), "upload Image",
                       R.style.exampleToast).show()
                }.addOnFailureListener {
                    StyleableToast.makeText(requireActivity(), "error upload",
                        R.style.exampleToast).show()
                }
            }
        }
    }
}