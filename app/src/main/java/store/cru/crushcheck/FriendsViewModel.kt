package store.cru.crushcheck

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import store.cru.crushcheck.repository.UserRepository

class FriendsViewModel(
    val userRepository: UserRepository
) :ViewModel() {

    fun getUsers(list:ArrayList<UserProfile>, context: Context) = viewModelScope.launch {

    }
}