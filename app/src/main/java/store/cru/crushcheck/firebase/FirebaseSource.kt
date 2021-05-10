package store.cru.crushcheck.firebase

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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
            userCollectionRef.document(userProfile.instaName).set(userProfile).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context.applicationContext, "Successfully saved data.", Toast.LENGTH_LONG).show()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showUsers(list:ArrayList<UserProfile>,context: Context) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot=userCollectionRef.get().await()
            for(document in querySnapshot.documents){
                val obj = document.toObject<UserProfile>()
                obj?.let { list.add(it) }
            }
        }catch (e:java.lang.Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context.applicationContext,e.message,Toast.LENGTH_LONG).show()
            }
        }
    }
}