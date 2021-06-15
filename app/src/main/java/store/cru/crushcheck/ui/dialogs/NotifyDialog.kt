package store.cru.crushcheck.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import store.cru.crushcheck.adapters.NotificationAdapter
import store.cru.crushcheck.databinding.NotifyDialogBinding
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.ui.HostActivity
import java.lang.Exception

class NotifyDialog :DialogFragment() {

    private var _binding : NotifyDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FriendsViewModel
    private lateinit var accName:String
    private lateinit var notifications :List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotifyDialogBinding.inflate(inflater,container,false)
        viewModel = (activity as HostActivity).viewModel
        CoroutineScope(Dispatchers.IO).launch {
            accName = viewModel.getProfile()[0].instaName
            try {
                notifications = viewModel.readNotify(accName)?.values?.toList() as List<String>
                withContext(Dispatchers.Main){
                    val notifyAdapter = NotificationAdapter(notifications)
                    binding.rvNotifyShow.apply {
                        adapter = notifyAdapter
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"No Notifications",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}