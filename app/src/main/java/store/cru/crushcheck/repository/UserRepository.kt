package store.cru.crushcheck.repository

import android.net.Uri
import androidx.lifecycle.ViewModel
import store.cru.crushcheck.db.ProfileDatabase
import store.cru.crushcheck.firebase.FirebaseSource
import store.cru.crushcheck.models.UserProfile

class UserRepository(
    val source:FirebaseSource,
    val db: ProfileDatabase
) : ViewModel(){

    //Firebase
    suspend fun getAllUsers()=
        source.showUser()

    suspend fun uploadDP(fileName:String,uri:Uri) =
        source.uploadDP(fileName,uri)

    //DB
    suspend fun saveProfile(profile:UserProfile) =
            db.getProfileDao().upsert(profile)

    fun getProfile() = db.getProfileDao().getData()
}