import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min

data class NumberAndString(val number: Float, val string: String)
typealias NumberAndStringData = List<NumberAndString>

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

data class CycleDiagramLegend(val colorCode: Int, val title: String)
data class CycleDiagramData(val data: NumberAndStringData, var colorCodes: List<Int>, var sum: Float) {

    constructor(numberAndStringData: NumberAndStringData) : this(numberAndStringData, listOf(), 0f) {
        colorCodes = List(numberAndStringData.size) {
            getColorByIndex(it)
        }
        numberAndStringData.forEach {
            sum += it.number
        }
    }

    fun toLegend(): List<CycleDiagramLegend> {
        return List(data.size) {
            CycleDiagramLegend(colorCodes[it], data[it].string)
        }
    }
}

typealias NumbersData = List<Float>

fun parseNumbersData(dataFile: String): NumbersData {
    val result = mutableListOf<Float>()
    for (line in File(dataFile).readLines()) {
        val number = line.toFloatOrNull() ?: throw Exception("\"$line\" is not a number")
        result.add(number)
    }
    return result
}

data class HistogramBlock(val left: Float, val right: Float, var count: Int)
data class HistogramData(
    var blocks: List<HistogramBlock>,
    var columnsNumber: Int,
    var minValue: Float,
    var maxValue: Float
) {
    constructor(columns: Int, data: NumbersData) : this(listOf<HistogramBlock>(), columns, 0f, 0f) {
        maxValue = data.maxOrNull() ?: throw Exception("Histogram data is empty")
        minValue = data.minOrNull() ?: throw Exception("Histogram data is empty")
        val columnWidth = (maxValue - minValue) / columnsNumber
        blocks = MutableList(columnsNumber) {
            HistogramBlock(
                minValue + it * columnWidth,
                minValue + (it + 1) * columnWidth,
                0
            )
        }
        data.forEach {
            val index = floor((it - minValue) / columnWidth).toInt()
            blocks[min(index, blocks.size - 1)].count++
        }
    }

    fun maxCount() = blocks.maxOfOrNull { it.count } ?: throw Exception("Histogram is empty")
}
