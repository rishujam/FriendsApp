package store.cru.crushcheck.adapters

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import store.cru.crushcheck.databinding.ItemLikedBinding
import store.cru.crushcheck.firebase.FirebaseSource
import java.lang.Exception

class LikedAdapter(
    val likedUsers:List<String>
) : RecyclerView.Adapter<LikedAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemLikedBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int){
        holder.binding.likedName.text = likedUsers[position]
        val source = FirebaseSource()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dp = source.downloadDP(likedUsers[position])
                val bitmap = BitmapFactory.decodeByteArray(dp,0,dp.size)
                withContext(Dispatchers.Main){
                    holder.binding.likedIv.setImageBitmap(bitmap)
                }
            }catch (e: Exception){
                Log.e("Adapter","dp error:${e.message}")
            }
        }
    }

    override fun getItemCount(): Int {
        return likedUsers.size
    }

    inner class UserViewHolder(val binding: ItemLikedBinding) : RecyclerView.ViewHolder(binding.root)
}