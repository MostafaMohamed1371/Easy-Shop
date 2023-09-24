package com.example.e_commerce.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object FireBaseModueol {
    @Provides
    fun provideFireBaseAuth(): FirebaseAuth {

        return FirebaseAuth.getInstance()
    }
    @Provides
    fun provideFirebaseUser(): FirebaseUser {

        return FirebaseAuth.getInstance().currentUser!!
    }
    @Provides
    fun provideDatabaseReference(): DatabaseReference {

        return FirebaseDatabase.getInstance().getReference("Register")
    }
    @Provides
    fun provideuserId(user:FirebaseUser): String {

        return user.uid
    }
    @Provides
    @Named("mStorage")
    fun provideStorageReference(): StorageReference {

        return FirebaseStorage.getInstance().reference
    }
    @Provides
    @Named("imageStorage")
    fun provideimageStorage(): StorageReference {

        return FirebaseStorage.getInstance().getReferenceFromUrl("gs://e-commerce-243a0.appspot.com/").child(
            FirebaseAuth.getInstance().currentUser!!.uid)
    }

}