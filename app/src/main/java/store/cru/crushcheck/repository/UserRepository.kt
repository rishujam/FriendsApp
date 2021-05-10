package store.cru.crushcheck.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import store.cru.crushcheck.UserProfile
import store.cru.crushcheck.firebase.FirebaseSource

class UserRepository(
    val source:FirebaseSource
) : ViewModel(){

    fun getAllUsers(list:ArrayList<UserProfile>, context: Context)=
        source.showUsers(list,context)
}