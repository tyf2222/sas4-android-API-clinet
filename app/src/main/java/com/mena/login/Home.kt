package com.mena.login


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.apache.commons.text.StringEscapeUtils
import parseUserData
import java.io.IOException
import java.util.Timer
import java.util.TimerTask


class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                ProfileService()
                BalanceInfo()
                fetchUserData()
            }
        }

        // جدولة التكرار كل دقيقة (60 ثانية)
        timer.schedule(task, 0, 60 * 1000)




        cardgo()

    }

    private fun cardgo() {
        val token = intent.getStringExtra("TOKEN_KEY")

        val cardbtn: Button = findViewById(R.id.cardButton)
        cardbtn.setOnClickListener {
            val intent = Intent(this, ReNew::class.java)
            intent.putExtra("TOKENTOKEN_KEY", "$token" )
            startActivity(intent)
        }
    }


   private fun ProfileService() {
        val token = intent.getStringExtra("TOKEN_KEY")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://185.96.160.51/user/api/index.php/api/service")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Authorization", "Bearer $token")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {



                val responseData = response.body?.string()
                // التعامل مع الاستجابة هنا
                // يمكنك تحويل البيانات إلى كائن مناسب هنا بناءً على هيكل الاستجابة
                val unescapedResponseData = StringEscapeUtils.unescapeJava(responseData) // تحويل القيمة المشفرة إلى نص قابل للقراءة
                val user = parseUserData(unescapedResponseData)



                runOnUiThread {
                    //findViewById<ProgressBar>(R.id.progressBar6).visibility = View.INVISIBLE
                    findViewById<ImageView>(R.id.imageView_days).setImageResource(R.drawable.remaining_days)
                    findViewById<TextView>(R.id.textView_prpfile).text = "${user?.data?.profile_name}"
                    findViewById<TextView>(R.id.textView3).visibility = View.VISIBLE
                    findViewById<ProgressBar>(R.id.progressBar_prof2).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_prof1).visibility =  View.GONE
                    findViewById<TextView>(R.id.textView_ex).text = "${user?.data?.expiration}"
                    findViewById<TextView>(R.id.textView6).text = "${resources.getString(R.string.days)}"

                    if(user?.data?.subscription_status?.status == "true") {

                        findViewById<ImageView>(R.id.imageView_user).setImageResource(R.drawable.active)
                        findViewById<TextView>(R.id.textView_user).text = "${resources.getString(R.string.active)}"
                        findViewById<ImageView>(R.id.user_load).visibility = View.INVISIBLE
                    }
                    else  {

                        findViewById<ImageView>(R.id.imageView_user).setImageResource(R.drawable.unactive)
                        findViewById<TextView>(R.id.textView_user).text = "${resources.getString(R.string.unactive)}"
                        findViewById<ImageView>(R.id.user_load).visibility = View.INVISIBLE

                    }

                    if (user?.data?.status == "true") {

                        findViewById<ImageView>(R.id.imageView_online).setImageResource(R.drawable.connected)
                        findViewById<TextView>(R.id.textView_online).text = "${resources.getString(R.string.online)}"
                        findViewById<ImageView>(R.id.online_load).visibility = View.INVISIBLE


                    } else {
                        findViewById<ImageView>(R.id.imageView_online).setImageResource(R.drawable.disconnected)
                        findViewById<TextView>(R.id.textView_online).text = "${resources.getString(R.string.ofline)}"
                        findViewById<ImageView>(R.id.online_load).visibility = View.INVISIBLE

                    }

                }
            }
        })


    }


    private fun BalanceInfo() {
        val token = intent.getStringExtra("TOKEN_KEY")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://185.96.160.51/user/api/index.php/api/dashboard")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Authorization", "Bearer $token")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                // التعامل مع الاستجابة هنا
                // يمكنك تحويل البيانات إلى كائن مناسب هنا بناءً على هيكل الاستجابة
                val unescapedResponseData = StringEscapeUtils.unescapeJava(responseData) // تحويل القيمة المشفرة إلى نص قابل للقراءة
                val user = parseUserData(unescapedResponseData)


                runOnUiThread {

                    findViewById<TextView>(R.id.textView_days).text = "${user?.data?.remaining_days}"
                    findViewById<ImageView>(R.id.days_load2).visibility = View.INVISIBLE



                }
            }
        })


    }

    private fun fetchUserData() {
        val token = intent.getStringExtra("TOKEN_KEY")
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://185.96.160.51/user/api/index.php/api/user")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Authorization", "Bearer $token")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()

            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                // التعامل مع الاستجابة هنا
                // يمكنك تحويل البيانات إلى كائن مناسب هنا بناءً على هيكل الاستجابة
                val unescapedResponseData = StringEscapeUtils.unescapeJava(responseData) // تحويل القيمة المشفرة إلى نص قابل للقراءة
                val user = parseUserData(unescapedResponseData)



                runOnUiThread {

                    findViewById<ProgressBar>(R.id.progressBar_info).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info1).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info2).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info3).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info4).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info5).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info7).visibility =  View.GONE
                    findViewById<ProgressBar>(R.id.progressBar_info8).visibility =  View.GONE
                    findViewById<TextView>(R.id.textView7).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView8).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView10).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView12).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView_name).text = "${user?.data?.firstname}"
                    findViewById<TextView>(R.id.textview_username).text = "${user?.data?.username}"
                    findViewById<TextView>(R.id.textView_name1).text = "${user?.data?.firstname}"

                }
            }
        })


    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "${resources.getString(R.string.out)}", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

}
