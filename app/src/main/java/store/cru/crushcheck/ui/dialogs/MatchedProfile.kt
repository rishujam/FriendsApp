package store.cru.crushcheck.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import store.cru.crushcheck.databinding.DialogMatchedBinding

class MatchedProfile :DialogFragment() {

    private var _bind:DialogMatchedBinding? = null
    private val binding get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = DialogMatchedBinding.inflate(inflater,container,false)
        return binding.root
    }
}