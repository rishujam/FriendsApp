package store.cru.crushcheck.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import store.cru.crushcheck.models.UserProfile
import store.cru.crushcheck.repository.UserRepository

class FriendsViewModel(
        private val userRepository: UserRepository
) :ViewModel() {

    //Firebase
    suspend fun getUsers() =
        userRepository.getAllUsers()

    suspend fun uploadDP(fileName:String, uri:Uri) =
        userRepository.uploadDP(fileName,uri)

    suspend fun downloadDP(fileName: String)=
        userRepository.downloadDP(fileName)

    suspend fun getLikedProfiles(username:String)=
        userRepository.showLikedList(username)

    suspend fun addToLikedList(liked:Map<String,String>,username: String)=
        userRepository.addToLikedList(liked,username)

    suspend fun instantCheck(likedUser:String,mainUser:String)=
        userRepository.instantCheck(likedUser, mainUser)

    suspend fun addToNotify(notifyingAccount:String, notifySendAccount:Map<String,String>){
        userRepository.addToNotify(notifyingAccount,notifySendAccount)
    }

    suspend fun readNotify(accName:String)=
        userRepository.readNotify(accName)

    //DB

    suspend fun saveProfile(profile:UserProfile)=
            userRepository.saveProfile(profile)

    suspend fun getProfile() =
            userRepository.getProfile()
}