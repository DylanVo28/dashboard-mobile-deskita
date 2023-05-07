package com.example.admin_deskita

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.admin_deskita.request.DeskitaService
import com.example.admin_deskita.support.OTPService
import kotlinx.android.synthetic.main.fragment_confirm.*

class ConfirmActivity : AppCompatActivity() {
    private val client = DeskitaService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_confirm)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val prefs= getSharedPreferences("admin_deskita", Context.MODE_PRIVATE)
        val token = prefs?.getString("TOKEN",null)!!
        val res=client.getProfile(token)
        val user=res.getJSONObject("user")
        val phoneNumber=user.getString("phoneNumber")
        val otp= OTPService()
        otp.sendVerificationCode(phoneNumber)
        btnConfimOTP.setOnClickListener {
            val res:Boolean=otp.verifyCode(etConfirmOTP.text.toString())
            if(res==true){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

//                findNavController().navigate(R.id.action_ConfirmFragment_to_SecondFragment)
            }
            else{
                Toast.makeText(
                    this, "Mã không khớp",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}