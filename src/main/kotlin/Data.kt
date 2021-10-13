import java.io.File

data class FloatAndStringData(val data: List<Pair<Float, String>>, val sum: Float)
typealias TwoFloatsData = List<Pair<Float, Float>>

fun parseNumberAndStringData(dataFile: String) : FloatAndStringData {
    val resultList = mutableListOf<Pair<Float, String>>()
    val resultSum = 0f
    for (line in File(dataFile).readLines()) {
        val splitLine = line.split('~')
        if (splitLine.size != 2) {
            throw Exception("Incorrect data format in line: $line")
        }
        val float = splitLine[0].toFloatOrNull() ?: throw Exception("First parameter in line \"$line\" is not a number")
        resultList.add(Pair(float, splitLine[1]))
    }
    return FloatAndStringData(resultList, resultSum)
}

fun parseTwoFloatsData(dataFile: String) : TwoFloatsData {
    TODO()
}
