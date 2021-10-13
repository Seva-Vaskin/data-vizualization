import java.io.File

data class NumberAndString(val number: Float, val string: String)
data class NumberAndStringData(val data: List<NumberAndString>, val sum: Float)
typealias TwoNumbersData = List<Pair<Float, Float>>

fun parseNumberAndStringData(dataFile: String) : NumberAndStringData {
    val resultList = mutableListOf<NumberAndString>()
    var resultSum = 0f
    for (line in File(dataFile).readLines()) {
        val splitLine = line.split('~')
        if (splitLine.size != 2) {
            throw Exception("Incorrect data format in line: $line")
        }
        val number = splitLine[0].toFloatOrNull() ?: throw Exception("First parameter in line \"$line\" is not a number")
        resultList.add(NumberAndString(number, splitLine[1]))
        resultSum += number
    }
    return NumberAndStringData(resultList, resultSum)
}

fun parseTwoFloatsData(dataFile: String) : TwoNumbersData {
    TODO()
}
