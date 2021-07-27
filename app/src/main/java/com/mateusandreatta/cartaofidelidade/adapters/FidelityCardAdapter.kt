package com.mateusandreatta.cartaofidelidade.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mateusandreatta.cartaofidelidade.R
import com.mateusandreatta.cartaofidelidade.data.FidelityCard
import com.mateusandreatta.cartaofidelidade.databinding.CardFidelityBinding
import com.mateusandreatta.cartaofidelidade.ui.MainActivity


class FidelityCardAdapter :
    ListAdapter<FidelityCard, FidelityCardAdapter.ViewHolder>(DiffCallBack()) {

    var listnerMenu: (View, FidelityCard) -> Unit = {_,_ ->}
    var listnerChangeStamp: (View, FidelityCard) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardFidelityBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(getItem(position), holder.itemView.context)
    }

    inner class ViewHolder(
        private val binding: CardFidelityBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: FidelityCard, context: Context){

            binding.tvCompany.text = item.companyName
            binding.tvClient.text = item.clientName
            binding.tvDescription.text = item.description
            if(item.background != null)
                binding.fidelityCardView.setCardBackgroundColor(Color.parseColor(item.background))

            binding.imageViewStamp1.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp2.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp3.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp4.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp5.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp6.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp7.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp8.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp9.setOnClickListener { listnerChangeStamp(it,item) }
            binding.imageViewStamp10.setOnClickListener { listnerChangeStamp(it,item) }

            updateImage(item.checkedStamp1, binding.imageViewStamp1, context)
            updateImage(item.checkedStamp2, binding.imageViewStamp2, context)
            updateImage(item.checkedStamp3, binding.imageViewStamp3, context)
            updateImage(item.checkedStamp4, binding.imageViewStamp4, context)
            updateImage(item.checkedStamp5, binding.imageViewStamp5, context)
            updateImage(item.checkedStamp6, binding.imageViewStamp6, context)
            updateImage(item.checkedStamp7, binding.imageViewStamp7, context)
            updateImage(item.checkedStamp8, binding.imageViewStamp8, context)
            updateImage(item.checkedStamp9, binding.imageViewStamp9, context)
            updateImage(item.checkedStamp10, binding.imageViewStamp10, context)

            if(item.companyImage != null){
                val bmp = BitmapFactory.decodeByteArray(item.companyImage, 0, item.companyImage!!.size)
                binding.imageViewLogo.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        bmp,
                        bmp.width,
                        bmp.height,
                        false
                    )
                )
                binding.imageViewLogo.visibility = VISIBLE
            }else{
                binding.imageViewLogo.visibility = GONE
            }

            binding.fidelityCardView.setOnLongClickListener {
                listnerMenu(it,item)
                true
            }
        }
    }

    private fun updateImage(checked: Boolean, imageView: ImageView, context: Context){
        if(checked)
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.stamp_background_checked))
        else
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.stamp_background))
    }


}

class DiffCallBack: DiffUtil.ItemCallback<FidelityCard>(){
    override fun areItemsTheSame(oldItem: FidelityCard, newItem: FidelityCard) = oldItem == newItem
    override fun areContentsTheSame(oldItem: FidelityCard, newItem: FidelityCard) = oldItem.id == newItem.id
}