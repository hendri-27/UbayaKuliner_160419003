package com.ubaya.ubayakuliner_160419003.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Cart
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.detail_transaction_item.view.*

class CheckoutAdapter(val listCart:ArrayList<Cart>) : RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {
    class CheckoutViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.detail_transaction_item, parent, false)

        return CheckoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val cart = listCart[position]
        with(holder.view) {
            textDetailTransFoodName.text = cart.food.name
            textDetailTransQty.text = "${cart.cartQty} x"
            textDetailTransFoodPrice.text = String.format("Rp%,d", cart.cartQty * cart.food.price)

            imageDetailTransFood.loadImage(
                "https://hendri-27.github.io/ubayakuliner_db/images" + cart.food.photoURL,
                progressLoadingDetailTransFoodPhoto
            )
        }
    }

    override fun getItemCount() = listCart.size

    fun updateListCheckout(newListCart: ArrayList<Cart>){
        listCart.clear()
        listCart.addAll(newListCart)
        notifyDataSetChanged()
    }
}