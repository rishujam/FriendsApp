package store.cru.crushcheck.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import store.cru.crushcheck.databinding.ActivityVerifyLoginBinding
import store.cru.crushcheck.db.ProfileDatabase
import store.cru.crushcheck.firebase.FirebaseSource
import store.cru.crushcheck.models.UserProfile
import store.cru.crushcheck.repository.UserRepository
import store.cru.crushcheck.ui.FriendsViewModel
import store.cru.crushcheck.ui.FriendsViewModelProviderFactory

class VerifyLogin : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyLoginBinding
    private lateinit var auth : FirebaseAuth
    private val firebaseSource = FirebaseSource()
    private lateinit var userProfile: UserProfile
    private lateinit var viewModel: FriendsViewModel
    private lateinit var db : ProfileDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        db = ProfileDatabase(this)
        val userRepository = UserRepository(firebaseSource,db)
        val viewModerProviderFactory = FriendsViewModelProviderFactory(userRepository)
        viewModel = ViewModelProvider(this,viewModerProviderFactory).get(FriendsViewModel::class.java)

        val storedVerificationId = intent.getStringExtra("ID")
        userProfile = UserProfile("@${intent.getStringExtra("username").toString()}",intent.getStringExtra("phone").toString())

        binding.apply {
            binding.btnVerify.setOnClickListener {
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
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    firebaseSource.saveUserProfile(userProfile,applicationContext) // not by viewmodel  TODO
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.saveProfile(userProfile)
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}