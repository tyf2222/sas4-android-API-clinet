import com.google.gson.Gson

data class Redeem(

    val status: String,
    val message: String,

)

 fun parseRedeemData(jsonString: String): Redeem {
    val gson = Gson()
    return gson.fromJson(jsonString, Redeem::class.java)
}
