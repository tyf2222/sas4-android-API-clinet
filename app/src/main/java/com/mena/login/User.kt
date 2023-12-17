import com.google.gson.Gson

data class UserData(
    val status: Int,
    val data: Data,
    val permissions: List<String>
)

data class Data(

    val profile_name: String,
    val username: String,
    val firstname: String,
    val lastname: String?,
    val remaining_days: String?,
    val name: String,
    val email: String?,
    val phone: String,
    val address: String,
    val company: String,
    val description: String,
    val expiration: String,
    val status: String,
    val price: Int,
    val subscription_status: SubscriptionStatus,
    val registered_on: RegisteredOn,
    val id: Int,
    val static_ip: String?,
    val balance: Int,
    val auto_renew: Int,
    val profile_id: Int,
    val contract_id: String?,
    val loan_balance: Int
)

data class RegisteredOn(
    val date: String,
    val timezone_type: Int,
    val timezone: String
)

data class SubscriptionStatus(
    val status: String,
    val traffic: String,
    val expiration: String,
    val uptime: String
)




fun parseUserData(jsonString: String): UserData {
    val gson = Gson()
    return gson.fromJson(jsonString, UserData::class.java)
}

