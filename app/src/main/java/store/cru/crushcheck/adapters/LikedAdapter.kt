package store.cru.crushcheck.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import store.cru.crushcheck.databinding.ItemLikedBinding

class LikedAdapter(
    val likedUsers:List<String>
) : RecyclerView.Adapter<LikedAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemLikedBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int){
        holder.binding.likedName.text = likedUsers[position]
    }

    override fun getItemCount(): Int {
        return likedUsers.size
    }

    inner class UserViewHolder(val binding: ItemLikedBinding) : RecyclerView.ViewHolder(binding.root)
}