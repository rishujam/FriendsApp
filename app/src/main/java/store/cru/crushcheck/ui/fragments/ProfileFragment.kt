package store.cru.crushcheck.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import store.cru.crushcheck.databinding.FragmentProfileBinding
import store.cru.crushcheck.models.UserProfile
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.ui.HostActivity
import java.lang.Exception

private const val REQUEST_CODE_IMAGE_PICK = 0

class ProfileFragment :Fragment() {

    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!
    var currFile:Uri? = null
    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        viewModel = (activity as HostActivity).viewModel
        CoroutineScope(Dispatchers.IO).launch {
            val profile = viewModel.getProfile()[0] //profile from local db only username and phone
            withContext(Dispatchers.Main){
                binding.tvUsernameShow.text = profile.instaName
                binding.tvPhoneShow.text = profile.phone
            }
            try {
                val dp = viewModel.downloadDP(profile.instaName)
                val bitmap = BitmapFactory.decodeByteArray(dp,0,dp.size)
                withContext(Dispatchers.Main){
                    binding.ivDP.setImageBitmap(bitmap)
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"No DP for this Profile",Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.ibEditDP.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }
        binding.btnUpdateProfile.setOnClickListener {
            val profile = UserProfile(
                binding.tvUsernameShow.text.toString(),
                binding.tvPhoneShow.text.toString(),
                binding.etName.text.toString(),
                binding.etBio.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.saveProfile(profile)
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
                val username = binding.tvUsernameShow.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.uploadDP(username, currFile!!)
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Image Uploaded",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=  null
    }
}
