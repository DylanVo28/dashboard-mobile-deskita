package com.example.admin_deskita.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.admin_deskita.R
import com.example.admin_deskita.entity.Customer
import com.example.admin_deskita.entity.Discount
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class DiscountViewHolder(val view:View){
    var txtValidDate : TextView
    var txtName : TextView
    var txtUsed : TextView
    init {
        txtName = view.findViewById(R.id.txtName)
        txtValidDate = view.findViewById(R.id.txtValidDate)
        txtUsed = view.findViewById(R.id.txtUsed)
    }
}
class DiscountAdapter (private val context: Context,
                       private val idLayout: Int,
                       private val listDiscount: List<Discount>?): BaseAdapter() {
    private var positionSelect = -1
    override fun getCount(): Int {
        return if (listDiscount!!.size != 0 && !listDiscount.isEmpty()) {
            listDiscount.size
        } else 0
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        var viewHolder:DiscountViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(idLayout, parent, false)
            viewHolder = DiscountViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as DiscountViewHolder
        }
        val discount= listDiscount?.get(position)

        viewHolder.txtName.text = discount?.name
        viewHolder.txtValidDate.text = discount?.validDate
        viewHolder.txtUsed.text = discount?.used.toString()

        return view
    }
}