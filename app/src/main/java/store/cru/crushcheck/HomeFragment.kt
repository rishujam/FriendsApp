package store.cru.crushcheck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import store.cru.crushcheck.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel:FriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = (activity as HostActivity).viewModel
        CoroutineScope(Dispatchers.IO).launch {
            val users = viewModel.getUsers()
            withContext(Dispatchers.Main){
                setupRecyclerView(users)
            }
        }
        return binding.root
    }

    private fun setupRecyclerView(list:ArrayList<UserProfile>){
        homeAdapter = HomeAdapter(list)
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