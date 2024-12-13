package project_KMA_test.main

import project_KMA_test.API.APIManager
import project_KMA_test.Data.DataSearcher
import kotlin.system.exitProcess

fun main() {
    val apiManager = APIManager("k5wSm1o7TUicEptaO41Ibw")
    val dataSearcher = DataSearcher()

    // 데이터 조건 설정을 위한 함수 호출
    val (startTime, endTime, station, conditions) = getDataConditions()

    val Station = if (station.isNullOrBlank()) "0" else station

    try {
        // API에서 데이터 가져오기
        val csvData = apiManager.fetchData(startTime, endTime, Station) ?: run {
            println("데이터를 가져오는데 실패했습니다.")
            return
        }

        // CSV 데이터 파싱
        val (headers, parsedData) = dataSearcher.parseCSV(csvData)

        // 데이터 확인
        println("데이터 확인:")
        dataSearcher.printData(headers, parsedData)

        // 필터링된 데이터
        val filteredData = dataSearcher.filterData(parsedData, startTime, station, conditions)

        // 필터링된 데이터 확인
        println("\n필터링된 데이터 확인:")
        dataSearcher.printData(headers, filteredData)

        // 데이터 저장 여부 확인
        saveDataToCSV(filteredData)
    } catch (e: Exception) {
        println("오류 발생: ${e.message}")
        exitProcess(1)
    }
}