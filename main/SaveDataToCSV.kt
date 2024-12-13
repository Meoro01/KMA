package project_KMA_test.main

import project_KMA_test.File.FileManager

fun saveDataToCSV(filteredData: List<Map<String, String>>) {
    println("데이터를 저장하시겠습니까? (Y/N): ")
    val saveData = readLine()
    if (saveData.equals("Y", ignoreCase = true)) {
        val fileManager = FileManager()
        fileManager.saveToCSV(filteredData, "filtered_data_")
        println("데이터가 'filtered_data_1.csv'에 저장되었습니다.")
    } else {
        println("데이터를 저장하지 않았습니다.")
    }
}