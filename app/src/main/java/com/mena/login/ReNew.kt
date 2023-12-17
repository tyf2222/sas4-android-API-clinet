package com.mena.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.apache.commons.text.StringEscapeUtils
import parseRedeemData
import java.io.IOException


class ReNew : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_new)

        val pinCode = findViewById<EditText>(R.id.cardpin)
        val rechargeButton: Button = findViewById(R.id.refullbtn)
        rechargeButton.setOnClickListener {
            val pin = pinCode.text.toString()

            if (pin.isEmpty()){
                showMessage("يرجى ادخال رقم البطاقة")
                return@setOnClickListener

            }

            refillSubscription(pin)
        }
    }

    private fun refillSubscription(pin: String) {
        val token = intent.getStringExtra("TOKENTOKEN_KEY")

        val client = OkHttpClient()
        val mediaType = "text/plain".toMediaType()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("pin", pin)
            .build()
        val request = Request.Builder()
            .url("http://185.96.160.51/user/api/index.php/api/redeem")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showMessage("خطاء في العملية")
            }


            override fun onResponse(call: Call, response: Response) {
                val responseRedeem = response.body?.string()

                val unescapedResponseData = StringEscapeUtils.unescapeJava(responseRedeem)
                val user = parseRedeemData(unescapedResponseData)

                if (user?.status == "200") {
                    showMessage("تم تفعيل الاشتراك")
                    backhome()

                }

                if (user?.status == "-2") {
                    showMessage("البطاقة غير صالحة او مستخدمة")

                }

            }
        })

    }

    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun backhome(){
        val token = intent.getStringExtra("TOKENTOKEN_KEY")

        val intent = Intent(this, Home::class.java)
        intent.putExtra("TOKEN_KEY", token)
        startActivity(intent)
    }

}
