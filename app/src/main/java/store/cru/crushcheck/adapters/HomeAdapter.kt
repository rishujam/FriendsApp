package store.cru.crushcheck.adapters

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import store.cru.crushcheck.models.UserProfile
import store.cru.crushcheck.databinding.ItemUserBinding
import store.cru.crushcheck.firebase.FirebaseSource
import java.lang.Exception

class HomeAdapter(
        val users: List<UserProfile>,
        private val listener: OnItemClickListener
):RecyclerView.Adapter<HomeAdapter.UserViewHolder>(){

    inner class UserViewHolder(val binding:ItemUserBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.ibItemAdd.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val source = FirebaseSource()

        holder.binding.apply {
            tvItemName.text = users[position].name
            tvItemUsername.text = users[position].instaName
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val dp = source.downloadDP(users[position].instaName)
                    val bitmap = BitmapFactory.decodeByteArray(dp,0,dp.size)
                    withContext(Dispatchers.Main){
                        ivItemDP.setImageBitmap(bitmap)
                    }
                }catch (e:Exception){
                    Log.e("Adapter","dp error:${e.message}")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}