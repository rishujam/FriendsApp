package store.cru.crushcheck.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import store.cru.crushcheck.models.UserProfile

class FirebaseSource {

    private val userCollectionRef = Firebase.firestore.collection("users")
    val imageRef = Firebase.storage.reference

    fun saveUserProfile(userProfile: UserProfile, context:Context) = CoroutineScope(Dispatchers.IO).launch {
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

    suspend fun showUser() : ArrayList<UserProfile>{
        val list = ArrayList<UserProfile>()
        val querySnapshot = userCollectionRef.get().await()
        for(document in querySnapshot.documents){
            val obj = document.toObject<UserProfile>()
            obj?.let { list.add(it) }
        }
        return list
    }

    suspend fun uploadDP(filename:String,uri:Uri){
        try {
            uri?.let {
                imageRef.child("profilePictures/$filename").putFile(it).await()
            }
        }catch (e:Exception){
            Log.e("FirebaseSource","UploadDP error: ${e.message}")
        }
    }

    suspend fun downloadDP(filename: String):ByteArray{
        val maxDownloadSize = 5L * 1024 * 1024
        return imageRef.child("profilePictures/$filename").getBytes(maxDownloadSize).await()
    }
}