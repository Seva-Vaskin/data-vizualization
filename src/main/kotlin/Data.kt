import java.io.File

data class NumberAndString(val number: Float, val string: String)
typealias NumberAndStringData = List<NumberAndString>
typealias TwoNumbersData = List<Pair<Float, Float>>

fun parseNumberAndStringData(dataFile: String): NumberAndStringData {
    val result = mutableListOf<NumberAndString>()
    for (line in File(dataFile).readLines()) {
        val splitLine = line.split('~')
        if (splitLine.size != 2) {
            throw Exception("Incorrect data format in line: $line")
        }
        val number =
            splitLine[0].toFloatOrNull() ?: throw Exception("First parameter in line \"$line\" is not a number")
        result.add(NumberAndString(number, splitLine[1]))
    }
    return result
}

fun parseTwoFloatsData(dataFile: String): TwoNumbersData {
    TODO()
}

data class Legend(val colorCode: Int, val title: String)
data class CycleDiagramData(val data: NumberAndStringData, var colorCodes: List<Int>, var sum: Float) {

    constructor(numberAndStringData: NumberAndStringData) : this(numberAndStringData, listOf(), 0f){
        colorCodes = List(numberAndStringData.size) {
            getColorByIndex(it)
        }
        numberAndStringData.forEach {
            sum += it.number
        }
    }

    fun toLegend(): List<Legend> {
        return List<Legend>(data.size) {
            Legend(colorCodes[it], data[it].string)
        }
    }
}


