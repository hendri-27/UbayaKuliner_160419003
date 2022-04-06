package com.ubaya.ubayakuliner_160419003.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.DetailTransaction
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.detail_transaction_item.view.*
import kotlinx.android.synthetic.main.food_list_item.view.*


class DetailTransactionAdapter(val listDetailTransaction:ArrayList<DetailTransaction>) : RecyclerView.Adapter<DetailTransactionAdapter.DetailTransactionViewHolder>() {
    class DetailTransactionViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.detail_transaction_item, parent, false)

        return DetailTransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailTransactionViewHolder, position: Int) {
        val detailTransaction = listDetailTransaction[position]
        with(holder.view){
            textDetailTransFoodName.text = detailTransaction.food.name
            textDetailTransFoodPrice.text = String.format("Rp%,d",detailTransaction.historyPrice)
            textDetailTransQty.text = "${detailTransaction.historyQty} x"

            imageDetailTransFood.loadImage("https://hendri-27.github.io/ubayakuliner_db/images"+ detailTransaction.food.photoURL,progressLoadingDetailTransFoodPhoto)
        }
    }

    override fun getItemCount() = listDetailTransaction.size

    fun updateListDetailTransaction(newListDetailTransaction: ArrayList<DetailTransaction>){
        listDetailTransaction.clear()
        listDetailTransaction.addAll(newListDetailTransaction)
        notifyDataSetChanged()
    }
}