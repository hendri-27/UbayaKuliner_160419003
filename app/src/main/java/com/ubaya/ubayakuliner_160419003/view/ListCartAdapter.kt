package com.ubaya.ubayakuliner_160419003.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Cart
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.fragment_cart.*

class ListCartAdapter(val listCart:ArrayList<Cart>, val viewParent:CartFragment) : RecyclerView.Adapter<ListCartAdapter.CartViewHolder>() {
    class CartViewHolder(var view: View): RecyclerView.ViewHolder(view)
    private var subTotal:Int=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cart_list_item, parent, false)

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = listCart[position]
        with(holder.view){
            textCartFoodName.text = cart.food.name
            textCartFoodPrice.text = String.format("Rp%,d", cart.food.price)
            textQtyCartFoodCounter.text = cart.cartQty.toString()
            imageCartFood.loadImage("https://hendri-27.github.io/ubayakuliner_db/images"+cart.food.photoURL,progressLoadingCartFoodPhoto)

            buttonDecreaseFIC.setOnClickListener {
                val qty = Integer.parseInt(textQtyCartFoodCounter.text.toString()) - 1

                if (qty <= 0){
                    listCart.remove(cart)
                    notifyDataSetChanged()

                    if (itemCount == 0){
                        viewParent.textNoDataListCart.visibility = View.VISIBLE
                        viewParent.cardViewCheckout.visibility = View.GONE
                    }
                }else {
                    cart.cartQty = qty
                    textQtyCartFoodCounter.text = qty.toString()

                    if (!buttonIncreaseFIC.isEnabled){
                        buttonIncreaseFIC.isEnabled = true
                        buttonIncreaseFIC.setColorFilter(
                            Color.parseColor("#DC5959"),
                            PorterDuff.Mode.MULTIPLY)
                    }
                }
                subTotal -= cart.food.price
                viewParent.textCartSubtotal.text = String.format("Rp%,d", subTotal)
            }

            buttonIncreaseFIC.setOnClickListener {
                val qty = Integer.parseInt(textQtyCartFoodCounter.text.toString()) + 1

                if (buttonIncreaseFIC.isEnabled){
                    subTotal += cart.food.price
                    viewParent.textCartSubtotal.text = String.format("Rp%,d", subTotal)
                    listCart[position].cartQty = qty
                    textQtyCartFoodCounter.text = qty.toString()
                }

                if (qty >= cart.food.stock){
                    buttonIncreaseFIC.isEnabled = false
                    buttonIncreaseFIC.setColorFilter(
                        ContextCompat.getColor(context, android.R.color.darker_gray),
                        PorterDuff.Mode.MULTIPLY)
                }
            }
        }
    }

    override fun getItemCount() = listCart.size

    fun setSubTotal(total:Int){
        subTotal = total
    }

    fun getSubTotal(): Int {
        return subTotal
    }

    fun updateListFood(newListCart: ArrayList<Cart>){
        listCart.clear()
        listCart.addAll(newListCart)
        notifyDataSetChanged()
    }
}