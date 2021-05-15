package store.cru.crushcheck.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import store.cru.crushcheck.models.UserProfile
import store.cru.crushcheck.repository.UserRepository

class FriendsViewModel(
        val userRepository: UserRepository
) :ViewModel() {

    suspend fun getUsers() =
        userRepository.getAllUsers()

    suspend fun uploadDP(fileName:String, uri:Uri) =
        userRepository.uploadDP(fileName,uri)

    suspend fun saveProfile(profile:UserProfile)=
            userRepository.saveProfile(profile)

    fun getProfile() =
            userRepository.getProfile()
}