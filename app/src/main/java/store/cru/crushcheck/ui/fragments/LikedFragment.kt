package store.cru.crushcheck.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import store.cru.crushcheck.databinding.FragmentLikedBinding
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.ui.HostActivity

class LikedFragment : Fragment() {

    private var _binding: FragmentLikedBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLikedBinding.inflate(inflater,container,false)
        viewModel = (activity as HostActivity).viewModel
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=  null
    }
}