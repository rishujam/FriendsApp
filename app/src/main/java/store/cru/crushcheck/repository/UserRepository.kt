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

    suspend fun downloadDP(fileName: String)=
            source.downloadDP(fileName)
    //DB
    suspend fun saveProfile(profile:UserProfile) =
            db.getProfileDao().upsert(profile)

    suspend fun getProfile() = db.getProfileDao().getData()

}