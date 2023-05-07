package com.example.admin_deskita

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.admin_deskita.adapter.CustomersAdapter
import com.example.admin_deskita.adapter.DiscountAdapter
import com.example.admin_deskita.entity.Discount
import com.example.admin_deskita.request.DeskitaService
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val client = DeskitaService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discount, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        callGetDiscount(view)
    }
    fun callGetDiscount(view: View){
        val prefs = activity?.getSharedPreferences("admin_deskita", Context.MODE_PRIVATE)
        val token = prefs?.getString("TOKEN", null)!!
        var res:JSONObject=client.getDiscounts(token);
        val arrayDiscounts=res.getJSONArray("discounts")
        val array: ArrayList<Discount> = ArrayList()
        for (i in 0 until arrayDiscounts.length()) {
            val discount = arrayDiscounts.getJSONObject(i)
            val discountModel = Discount(
                discount.getString("_id"),
                discount.getString("name"),
                discount.getString("categoryProduct"),
                discount.getString("validDate"),
                discount.getInt("quantity"),
                discount.getInt("value"),
                discount.getString("createAt"),
                discount.getBoolean("used")
                )
            array.add(discountModel)
        }
        val lvDiscounts = view.findViewById(R.id.discounts) as ListView
        val adapter = context?.let { DiscountAdapter(it, R.layout.list_discounts, array) }
        lvDiscounts.adapter = adapter
//        val myOrder = gson.fromJson(orders, MyOrder::class.java)
//        classifyOrdersByStatus(myOrder.orders)

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}