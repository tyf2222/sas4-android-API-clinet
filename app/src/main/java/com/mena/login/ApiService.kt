import com.google.gson.Gson
import com.mena.login.LoginData
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException

class ApiService {
    private var token: String? = null

    fun login(username: String, password: String, callback: (LoginData?, String?) -> Unit) {
        val client = OkHttpClient()
        val mediaType = "text/plain".toMediaType()
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()
        val request = Request.Builder()
            .url("http://185.96.160.51/user/api/index.php/api/auth/login")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // التعامل مع الفشل
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                responseData?.let {
                    val loginResponse = Gson().fromJson(it, LoginData::class.java)
                    callback(loginResponse, null)
                } ?: run {
                    // إذا لم تكن هناك بيانات
                    callback(null, "No data received from server")
                }
            }
        })
    }

    fun getToken(): String? {
        return token
    }

}

