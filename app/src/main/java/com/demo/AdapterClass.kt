package com.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.databinding.LayoutItemBinding

class AdapterClass(
    private val list: ArrayList<Profile>,
) : RecyclerView.Adapter<AdapterClass.ViewHolder>() {
    class ViewHolder(
        val biniding: LayoutItemBinding,
    ) : RecyclerView.ViewHolder(biniding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = list[position]
        holder.biniding.apply {
            profileImage.setImageResource(item.img)
            textName.text = item.name
            textStatus.text = if (item.status) "đang chốt" else "đã chốt"
            textPhone.text = item.phone
            textCategory.text = item.category
            textConsultPrice.text = item.consultPrice
            textProductConsult.text = item.productConsult
        }
    }

    override fun getItemCount(): Int = list.size

    fun addData(newList: List<Profile>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun setItems(newItems: List<Profile>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }
}
