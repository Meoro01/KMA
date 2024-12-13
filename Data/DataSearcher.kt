package project_KMA_test.Data

class DataSearcher {

    fun parseCSV(csvData: String): Pair<List<String>, List<Map<String, String>>> {
        val lines = csvData.lines()
            .filterNot { it.startsWith("#") } // 주석 줄 제거

        if (lines.isEmpty()) throw IllegalArgumentException("CSV 데이터가 비어 있습니다.")

        // 첫 번째 줄을 헤더로 사용
        val headers = lines.first().split(",").map { it.trim() }
        val dataLines = lines.drop(1) // 헤더를 제외한 데이터 줄들

        val parsedData = dataLines.map { line ->
            val values = line.split(",").map { it.trim() }
            headers.zip(values).toMap() // 헤더와 값을 매핑하여 Map으로 변환
        }

        return headers to parsedData
    }

    fun printData(headers: List<String>, data: List<Map<String, String>>) {
        // 헤더 출력
        println(headers.joinToString(" | ") { it.padEnd(15) })

        // 데이터 출력
        data.forEach { row ->
            println(headers.joinToString(" | ") { header ->
                row[header]?.padEnd(15) ?: "".padEnd(15)
            })
        }
    }

    fun filterData(
        parsedData: List<Map<String, String>>,
        startTime: String,
        station: String?,
        conditions: List<Pair<String, (String) -> Boolean>>
    ): List<Map<String, String>> {
        return parsedData.filter { data ->
            // 특정 시간, 지역 필터링
            val isInTimeRange = data["YYMMDDHHMI"]?.startsWith(startTime) ?: false
            val isCorrectStation = station?.let { it == data["STN"] } ?: true

            // 조건 필터링
            val meetsConditions = conditions.all { (key, condition) ->
                data[key]?.let(condition) ?: false
            }

            isInTimeRange && isCorrectStation && meetsConditions
        }
    }
}