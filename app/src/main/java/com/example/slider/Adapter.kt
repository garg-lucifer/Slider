package com.example.slider

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import kotlin.math.abs
import kotlin.math.roundToInt

class Adapter(private val list : List<SlideData>, private val onChange: OnChange) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val slideData = list[position]
        holder.btn1.text = slideData.intStart.toString()
        holder.btn2.text = slideData.curr.toString()
        val diff = slideData.intEnd.toFloat() - slideData.intStart.toFloat()
        val diff2 = slideData.curr.toFloat() - slideData.intStart.toFloat()
        val point = diff2/diff

        holder.slider.value = point

        holder.slider.addOnChangeListener(object : Slider.OnChangeListener{
            override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                val valuee = (slideData.intStart + (diff)*value).roundToInt()
                holder.btn2.text = (valuee).toString()
                slideData.curr = valuee
            }
        })

        holder.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {
                onChange.valueStart()
            }

            override fun onStopTrackingTouch(slider: Slider) {
                if(slideData.curr == slideData.intEnd){

                }
                else if(slideData.curr  == slideData.intStart){
                    onChange.delete(slideData)
                }
                else if(abs(slideData.curr - slideData.intEnd) <= 2 &&
                        abs(slideData.curr - slideData.intStart) <= 2 )onChange.displayToast()
                else onChange.valueStop(slideData)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
class MyViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){
    val btn1 : Button = itemView.findViewById(R.id.btn1)
    val btn2 : Button = itemView.findViewById(R.id.btn2)
    val slider : Slider = itemView.findViewById(R.id.slider)
}