package store.cru.crushcheck.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import store.cru.crushcheck.auth.UserProfile
import store.cru.crushcheck.databinding.ItemUserBinding

class HomeAdapter(
        private val users: List<UserProfile>
) : RecyclerView.Adapter<HomeAdapter.UserViewHolder>(){

    inner class UserViewHolder(val binding:ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val dpUrl = users[position].dp
        holder.binding.apply {
            tvItemName.text = users[position].name
            tvItemUsername.text = users[position].instaName
            //Glide.with(this.root).load(dpUrl).into(ivItemDP)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}