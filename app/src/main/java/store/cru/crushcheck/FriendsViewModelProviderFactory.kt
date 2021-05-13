package store.cru.crushcheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import store.cru.crushcheck.repository.UserRepository

class FriendsViewModelProviderFactory(
    val userRepository: UserRepository
) :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FriendsViewModel(userRepository) as T
    }
}