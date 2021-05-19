package store.cru.crushcheck.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import store.cru.crushcheck.models.UserProfile
import store.cru.crushcheck.databinding.FragmentHomeBinding
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.adapters.HomeAdapter
import store.cru.crushcheck.ui.HostActivity
import java.lang.Exception

class HomeFragment : Fragment() , HomeAdapter.OnItemClickListener{

    override fun onItemClick(position: Int) {

        val list = ArrayList<String>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                viewModel.addToLikedList(list,username)
                withContext(Dispatchers.Main){
                    Toast.makeText( context,"Profile Added to Liked List", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText( context,"Error Adding: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel: FriendsViewModel
    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = (activity as HostActivity).viewModel
        CoroutineScope(Dispatchers.IO).launch {
            val users = viewModel.getUsers() // getting all users from firebase
            username = viewModel.getProfile()[0].instaName //saving username for this fragment
            withContext(Dispatchers.Main){
                setupRecyclerView(users)
            }

        }
        return binding.root
    }

    private fun setupRecyclerView(list:ArrayList<UserProfile>){
        //homeAdapter = HomeAdapter(list)
        binding.rvHome.apply {
            homeAdapter.notifyDataSetChanged()
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}