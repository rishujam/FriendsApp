package store.cru.crushcheck.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import store.cru.crushcheck.adapters.PagerAdapter
import store.cru.crushcheck.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMessageBinding.inflate(inflater,container,false)

        val pagerAdapter = PagerAdapter(childFragmentManager,lifecycle)

        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab,position ->
            when(position){
                0 ->{
                    tab.text ="Private"
                }
                1 ->{
                    tab.text ="Global"
                }
                2 -> {
                    tab.text = "Blocked"
                }
            }
        }.attach()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=  null
    }
}