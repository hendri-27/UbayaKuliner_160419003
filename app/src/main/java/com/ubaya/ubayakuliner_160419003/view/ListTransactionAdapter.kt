package com.ubaya.ubayakuliner_160419003.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Transaction
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.transaction_list_item.view.*

class ListTransactionAdapter(val listTransaction:ArrayList<Transaction>) : RecyclerView.Adapter<ListTransactionAdapter.TransactionViewHolder>() {
    class TransactionViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.transaction_list_item, parent, false)

        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = listTransaction[position]
        with(holder.view) {
            textTransactionId.text = transaction.id
            textTransactionDate.text = transaction.date
            textTransactionRestoName.text = transaction.restaurant.name
            textTransactionGrandtotal.text = String.format("Rp%,d - %s", transaction.grandTotal, transaction.paymentMethod)
            textHistoryStatus.text = transaction.status

            cardTransaction.setOnClickListener {
                val action = ListTransactionFragmentDirections.actionDetailTransactionFragment(transaction)
                Navigation.findNavController(it).navigate(action)
            }

            imageTransactionResto.loadImage(
                "https://hendri-27.github.io/ubayakuliner_db/images"+transaction.restaurant.photoURL,
                progressLoadingTransactionRestoPhoto
            )

            if (!transaction.rate.isNullOrEmpty()){
                buttonRate.isEnabled = false
                buttonRate.text = "Rated"
            }else {
                buttonRate.setOnClickListener {
                    val action = ListTransactionFragmentDirections.actionAddReviewFragment(transaction)
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = listTransaction.size

    fun updateListTransaction(newListTransaction: ArrayList<Transaction>) {
        listTransaction.clear()
        listTransaction.addAll(newListTransaction)
        notifyDataSetChanged()
    }
}