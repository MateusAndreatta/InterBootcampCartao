package com.mateusandreatta.cartaofidelidade.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mateusandreatta.cartaofidelidade.R


class ColorsAdapter(private val dataSet: Array<String>,
                    private val listener: (String) -> Unit) :
    RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView

        init {
            card = view.findViewById(R.id.color_card)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_color, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.card.setCardBackgroundColor(Color.parseColor(dataSet[position]))
        viewHolder.itemView.setOnClickListener { listener(dataSet[position]) }
    }

    override fun getItemCount() = dataSet.size

}
