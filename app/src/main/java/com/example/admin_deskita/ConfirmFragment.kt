package com.example.admin_deskita

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.admin_deskita.request.DeskitaService
import com.example.admin_deskita.support.OTPService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val client = DeskitaService()

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
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val prefs=activity?.getSharedPreferences("admin_deskita", Context.MODE_PRIVATE)
        val token = prefs?.getString("TOKEN",null)!!
        val res=client.getProfile(token)
        val user=res.getJSONObject("user")
        val phoneNumber=user.getString("phoneNumber")

        val otp=OTPService()
        otp.sendVerificationCode(phoneNumber)

        val confirmOTP:Button=view.findViewById(R.id.btnConfimOTP)
        confirmOTP.setOnClickListener {
            val confirmOTP:EditText=view.findViewById(R.id.etConfirmOTP)
            val res:Boolean=otp.verifyCode(confirmOTP.text.toString())
            if(res==true){
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)

//                findNavController().navigate(R.id.action_ConfirmFragment_to_SecondFragment)
            }
            else{
                Toast.makeText(
                    activity, "Mã không khớp",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConfirmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfirmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}