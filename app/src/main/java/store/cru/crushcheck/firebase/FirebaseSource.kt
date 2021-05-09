package store.cru.crushcheck.firebase

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import store.cru.crushcheck.UserProfile

class FirebaseSource {

    private val userCollectionRef = Firebase.firestore.collection("users")

    fun saveUserProfile(userProfile: UserProfile,context:Context) = CoroutineScope(Dispatchers.IO).launch {
        try {
            userCollectionRef.add(userProfile).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context.applicationContext, "Successfully saved data.", Toast.LENGTH_LONG).show()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}