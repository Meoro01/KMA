package project_KMA_test.API

import okhttp3.OkHttpClient
import okhttp3.Request

class APIManager(private val authKey: String) {

    private val client = OkHttpClient()

    fun fetchData(tm1: String, tm2: String, stn: String = "0", help: String = "0"): String {
        val url = "https://apihub.kma.go.kr/api/typ01/url/kma_buoy2.php" +
                "?tm1=$tm1&tm2=$tm2&stn=$stn&help=$help&authKey=$authKey"

        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("API 요청 실패: ${response.code}")
            }
            return response.body?.string() ?: throw IllegalStateException("응답 본문이 비어 있습니다.")
        }
    }
}
