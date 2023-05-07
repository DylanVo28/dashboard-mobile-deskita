package com.example.admin_deskita

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.admin_deskita.request.DeskitaService
import kotlinx.android.synthetic.main.act_recovery_password.*
import okhttp3.OkHttpClient

class RecoveryPasswordAct : AppCompatActivity() {
    private val client = DeskitaService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_recovery_password)

        val code = intent.getIntExtra("code",0)

        btnConfirmCode.setOnClickListener {
            if(et_code.text.toString().toInt()==code){
                etNewPassword.visibility= View.VISIBLE
                etConfirmNewPassword.visibility=View.VISIBLE
                btnRecoveryPassword.visibility=View.VISIBLE

            }
        }
        btnRecoveryPassword.setOnClickListener {
            try {
                client.confirmPassword(et_code.text.toString().toInt(),etNewPassword.text.toString(),etConfirmNewPassword.text.toString())
                Toast.makeText(
                    this, "Khôi phục mật khẩu thành công",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }catch (e:Exception){
                Toast.makeText(
                    this, "Không thể khôi phục mật khẩu",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun goBack(view: View) {
        onBackPressed()
    }
}