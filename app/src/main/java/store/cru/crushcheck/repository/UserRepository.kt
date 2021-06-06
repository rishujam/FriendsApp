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

    suspend fun addToLikedList(liked:Map<String,String>,username:String)=
        source.addToLiked(liked,username)

    suspend fun showLikedList(username: String) =
        source.readLikedList(username)

    suspend fun instantCheck(likedUser:String,mainUser:String)=
        source.instantCheck(likedUser, mainUser)

    suspend fun addToNotify(notifyingAccount:String, notifySendAccount:Map<String,String>){
        source.addToNotify(notifyingAccount,notifySendAccount)
    }

    suspend fun readNotify(accName:String)=
        source.readNotification(accName)

    //DB
    suspend fun saveProfile(profile:UserProfile) =
            db.getProfileDao().upsert(profile)

    suspend fun getProfile() = db.getProfileDao().getData()

}