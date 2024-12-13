package project_KMA_test.File

import java.io.File

class FileManager {

    fun saveToCSV(data: List<Map<String, String>>, filePathPrefix: String) {
        if (data.isEmpty()) throw IllegalArgumentException("저장할 데이터가 없습니다.")

        val headers = data.first().keys
        val lines = mutableListOf<String>()

        // Write headers
        lines.add(headers.joinToString(","))

        // Write new data
        data.forEach { row ->
            lines.add(headers.joinToString(",") { row[it] ?: "" })
        }

        // Generate new file name
        var fileCount = 1
        var filePath = "$filePathPrefix$fileCount.csv"

        while (File(filePath).exists()) {
            fileCount++
            filePath = "$filePathPrefix$fileCount.csv"
        }

        // Write data to new CSV file
        File(filePath).writeText(lines.joinToString("\n"))

        println("데이터가 '$filePath'에 저장되었습니다.")
    }
}