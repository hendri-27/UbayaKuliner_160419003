package com.ubaya.ubayakuliner_160419003.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Food
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.food_list_item.view.*

class ListFoodAdapter(val listFood:ArrayList<Food>) : RecyclerView.Adapter<ListFoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_list_item, parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = listFood[position]
        with(holder.view){
            textFoodName.text = food.name
            textFoodPrice.text = String.format("Rp%,d", food.price)

            imageFood.loadImage("https://hendri-27.github.io/ubayakuliner_db/images"+food.photoURL,progressLoadingFoodPhoto)

            if (food.stock != 0){
                buttonAddCart.setOnClickListener {
                    cardQtyCounter.visibility = View.VISIBLE
                    buttonAddCart.visibility = View.GONE
                    if (food.stock == 1){
                        buttonIncreaseFIR.isEnabled = false
                        buttonIncreaseFIR.setColorFilter(
                            ContextCompat.getColor(context, android.R.color.darker_gray),
                            PorterDuff.Mode.MULTIPLY)
                    }
                }

                buttonDecreaseFIR.setOnClickListener {
                    val qty = Integer.parseInt(textQtyFoodCounter.text.toString()) - 1

                    if (qty <= 0){
                        buttonAddCart.visibility = View.VISIBLE
                        cardQtyCounter.visibility = View.GONE
                    }else {
                        textQtyFoodCounter.text = qty.toString()

                        if (!buttonIncreaseFIR.isEnabled){
                            buttonIncreaseFIR.isEnabled = true
                            buttonIncreaseFIR.setColorFilter(
                                Color.parseColor("#DC5959"),
                                PorterDuff.Mode.MULTIPLY)
                        }
                    }
                }

                buttonIncreaseFIR.setOnClickListener {
                    val qty = Integer.parseInt(textQtyFoodCounter.text.toString()) + 1

                    if (qty < food.stock){
                        textQtyFoodCounter.text = qty.toString()
                    }else{
                        textQtyFoodCounter.text = qty.toString()
                        buttonIncreaseFIR.isEnabled = false
                        buttonIncreaseFIR.setColorFilter(
                            ContextCompat.getColor(context, android.R.color.darker_gray),
                            PorterDuff.Mode.MULTIPLY)
                    }
                }
            }else{
                buttonAddCart.isEnabled = false
                buttonAddCart.text = "Sold Out"
            }
        }
    }

    override fun getItemCount() = listFood.size

    fun updateListFood(newListFood: ArrayList<Food>){
        listFood.clear()
        listFood.addAll(newListFood)
        notifyDataSetChanged()
    }
}