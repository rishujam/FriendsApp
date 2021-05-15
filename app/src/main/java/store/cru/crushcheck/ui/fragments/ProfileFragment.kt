package store.cru.crushcheck.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import store.cru.crushcheck.databinding.FragmentProfileBinding
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.ui.HostActivity

private const val REQUEST_CODE_IMAGE_PICK = 0

class ProfileFragment :Fragment() {

    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!
    var currFile:Uri? = null
    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        viewModel = (activity as HostActivity).viewModel
        binding.ibEditDP.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK){
            data?.data?.let {
                currFile = it
                binding.ivDP.setImageURI(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=  null
    }
}
