package project_KMA_test.main


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DataConditions(
    val startTime: String,
    val endTime: String,
    val station: String?,
    val conditions: List<Pair<String, (String) -> Boolean>>
)

fun getDataConditions(): DataConditions {
    println("1. 특정 시간 데이터를 가져옵니다.")
    println("0을 입력하면 현재 시간 기준으로 데이터를 가져옵니다.")
    print("시작 시간 (YYYYMMDDHHMM): ")
    val currentTime = LocalDateTime.now()
    val startTime = readLine()?.takeIf { it != "0" } ?: currentTime.minusHours(2).withMinute(0).withSecond(0).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))

    print("끝 시간 (YYYYMMDDHHMM): ")
    val endTime = readLine()?.takeIf { it != "0" } ?: currentTime.withMinute(0).withSecond(0).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))

    println("2. 특정 지역 데이터를 가져옵니다.")
    println("0을 입력하면 모든 지역 데이터를 가져옵니다.")
    print("지역 코드: ")
    val station = readLine()?.takeIf { it != "0" }

    val conditions = mutableListOf<Pair<String, (String) -> Boolean>>()
    while (true) {
        println("3. 특정 조건을 입력합니다.")
        println("조건이 없다면 0을 입력하세요.")
        println("1. 풍향 (WD), 2. 풍속 (WS), 3. 기압 (PA), 4. 습도 (HM), 5. 기온 (TA)")
        println("6. 해수면 온도 (TW), 7. 최대 파고 (WH_MAX), 8. 유의 파고 (WH_SIG), 9. 평균 파고 (WH_AVE), 10. 파주기 (WP), 11. 파향 (WO)")
        print("조건 키: ")
        val key = readLine()
        if (key == "0") break

        print("조건: 1. 값 이상, 2. 값 이하, 3. 값과 동일: ")
        val conditionType = readLine()?.toInt()
        print("조건 값: ")
        val conditionValue = readLine()?.toDouble() ?: continue

        val condition: (String) -> Boolean = when (conditionType) {
            1 -> { value -> value.toDoubleOrNull()?.let { it >= conditionValue } == true }
            2 -> { value -> value.toDoubleOrNull()?.let { it <= conditionValue } == true }
            3 -> { value -> value.toDoubleOrNull() == conditionValue }
            else -> continue
        }
        conditions.add(key!! to condition)
    }

    return DataConditions(startTime, endTime, station, conditions)
}