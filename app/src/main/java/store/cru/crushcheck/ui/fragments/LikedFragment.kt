package store.cru.crushcheck.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import store.cru.crushcheck.adapters.LikedAdapter
import store.cru.crushcheck.databinding.FragmentLikedBinding
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.ui.HostActivity
import java.lang.Exception

class LikedFragment : Fragment() {

    private var _binding: FragmentLikedBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: FriendsViewModel
    private lateinit var likedAdapter:LikedAdapter
    private lateinit var username:String
    private lateinit var likedList: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLikedBinding.inflate(inflater,container,false)
        viewModel = (activity as HostActivity).viewModel

        CoroutineScope(Dispatchers.IO).launch {
            username = viewModel.getProfile()[0].instaName
            try {
                likedList = viewModel.getLikedProfiles(username)?.values?.toList() as List<String>
                withContext(Dispatchers.Main){
                    likedAdapter = LikedAdapter(likedList)
                    binding.rvLikedList.apply {
                        likedAdapter.notifyDataSetChanged()
                        adapter = likedAdapter
                        layoutManager = LinearLayoutManager(activity)
                    }

                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"No Liked Profile",Toast.LENGTH_LONG).show()
                }
            }

        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=  null
    }

}