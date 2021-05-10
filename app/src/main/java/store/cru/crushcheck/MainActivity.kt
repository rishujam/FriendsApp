
package store.cru.crushcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import store.cru.crushcheck.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val userCollectionRef = Firebase.firestore.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(this@MainActivity,HostActivity::class.java))
                finish()

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                resendToken = token

                var intent = Intent(this@MainActivity,VerifyLogin::class.java)
                intent.putExtra("ID",verificationId)
                intent.putExtra("username",binding.etUsername.text.toString())
                intent.putExtra("phone",binding.etPhone.text.toString())
                startActivity(intent)

            }
        }

        binding.btnGetotp.setOnClickListener {
            if(binding.etPhone.text.isNotEmpty() && binding.etUsername.text.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    val querySnapshot = userCollectionRef.document("@"+binding.etUsername.text.toString()).get().await()
                    querySnapshot?.let {
                        val username = querySnapshot.get("instaName").toString()
                        val phone = querySnapshot.get("phone").toString()
                        if(username=="@"+binding.etUsername.text.toString()){
                            if(phone==binding.etPhone.text.toString()){
                                withContext(Dispatchers.Main){
                                    signUp()
                                }
                            }else{
                                withContext(Dispatchers.Main){
                                    Toast.makeText(this@MainActivity,"Acc already exists with Phone: ${phone} ",Toast.LENGTH_LONG).show()
                                }
                            }
                        }else{
                            withContext(Dispatchers.Main){
                                signUp()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun signUp(){
        var mobileNumber=binding.etPhone.text.toString().trim()

        if(mobileNumber.isNotEmpty()){
            mobileNumber= "+91$mobileNumber"
            sendVerificationCode (mobileNumber)
        }else{
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)// Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this,HostActivity::class.java))
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
    
}