package store.cru.crushcheck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import store.cru.crushcheck.databinding.FragmentHomeBinding
import store.cru.crushcheck.databinding.FragmentLoginBinding

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        if(currentUser==null){
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_loginFragment)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}