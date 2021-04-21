package store.cru.crushcheck

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import store.cru.crushcheck.databinding.FragmentLoginBinding
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private lateinit var storedVerificationId:String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding.btnGetotp.setOnClickListener {
            if(binding.etPhone.text.isNotEmpty() && binding.etUsername.text.isNotEmpty()){
                signUp()
            }
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_homeFragment)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                val bundle = Bundle()
                val verifyLoginFragment = VerifyLoginFragment()
                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                bundle.putString("ID", storedVerificationId)
                verifyLoginFragment.arguments = bundle
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_verifyLoginFragment)

            }
        }
        return binding.root
    }

    private fun signUp(){
        var mobileNumber=binding.etPhone.text.toString().trim()

        if(mobileNumber.isNotEmpty()){
            mobileNumber= "+91$mobileNumber"
            sendVerificationCode (mobileNumber)
        }else{
            Toast.makeText(context,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {
        val x = MainActivity()
        val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!=null){
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }
}