package store.cru.crushcheck.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import store.cru.crushcheck.databinding.ItemNotifyBinding

class NotificationAdapter(
    val list:List<String>
) : RecyclerView.Adapter<NotificationAdapter.NotifyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        return NotifyViewHolder(ItemNotifyBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        holder.binding.apply {
            tvNotifyShow.text = list[position]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NotifyViewHolder(val binding : ItemNotifyBinding):RecyclerView.ViewHolder(binding.root)
}