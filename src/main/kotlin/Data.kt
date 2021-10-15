import kotlin.math.floor
import kotlin.math.min

data class NumberAndString(val number: Float, val string: String)

typealias NumberAndStringData = List<NumberAndString>

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
