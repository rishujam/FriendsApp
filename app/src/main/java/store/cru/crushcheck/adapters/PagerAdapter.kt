package store.cru.crushcheck.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import store.cru.crushcheck.ui.fragments.BlockedFragment
import store.cru.crushcheck.ui.fragments.GlobalChatFragment
import store.cru.crushcheck.ui.fragments.PrivateChatFragment

class PagerAdapter(supportFragmentManager: FragmentManager, lifecycle:Lifecycle): FragmentStateAdapter(supportFragmentManager,lifecycle){

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0 -> {
                PrivateChatFragment()
            }
            1 -> {
                GlobalChatFragment()
            }
            2 -> {
                BlockedFragment()
            }
            else ->{
                Fragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}