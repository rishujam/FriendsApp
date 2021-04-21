package store.cru.crushcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import store.cru.crushcheck.databinding.FragmentVerifyLoginBinding


class VerifyLoginFragment : Fragment() {

    private var _binding: FragmentVerifyLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val storedVerificationId = arguments?.getString("ID","")

        binding.apply {
            btnVerify.setOnClickListener {
                if(etOTP1.text.isNotEmpty() && etOTP2.text.isNotEmpty() && etOTP3.text.isNotEmpty() && etOTP4.text.isNotEmpty() && etOTP5.text.isNotEmpty() && etOTP6.text.isNotEmpty()){
                    val otp = etOTP1.text.toString().trim() + etOTP2.text.toString().trim() + etOTP3.text.toString().trim()+ etOTP4.text.toString().trim() + etOTP5.text.toString().trim()+etOTP6.text.toString().trim()
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                            storedVerificationId.toString(), otp)
                    signInWithPhoneAuthCredential(credential)
                }else{
                    textView3.isVisible = true
                }
            }
        }
        return binding.root
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val x =MainActivity()
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        Navigation.findNavController(binding.root).navigate(R.id.action_verifyLoginFragment_to_homeFragment)
                    } else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(context,"Invalid OTP",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }

}