import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TestData {

    @Test
    fun testCycleDiagramData_Constructor() {
        val numberAndStringData: NumberAndStringData = listOf(
            NumberAndString(10f, "ten"),
            NumberAndString(1f, "one")
        )
        val data = CycleDiagramData(numberAndStringData)
        assertEquals(11f, data.sum)
        assertEquals(numberAndStringData, data.data)
        assertEquals(2, data.colorCodes.size)
        assertEquals(Colors.values()[0].code, data.colorCodes[0])
        assertEquals(Colors.values()[1].code, data.colorCodes[1])
    }

    @Test
    fun testCycleDiagramData_ToLegend() {
        val numberAndStringData: NumberAndStringData = listOf(
            NumberAndString(10f, "ten"),
            NumberAndString(1f, "one")
        )
        val data = CycleDiagramData(numberAndStringData)
        val legend = data.toLegend()
        val expected = listOf(
            CycleDiagramLegend(Colors.values()[0].code, "ten"),
            CycleDiagramLegend(Colors.values()[1].code, "one")
        )
        assertEquals(expected, legend)
    }

    @Test
    fun testHistogramData_Constructor() {
        val numbersData = listOf(10f, 11f, 12f)
        val data = HistogramData(5, numbersData)
        assertEquals(10f, data.minValue)
        assertEquals(12f, data.maxValue)
        assertEquals(5, data.columnsNumber)
        val exceptedBlocks = listOf(
            HistogramBlock(10f, 10f + 2f / 5, 1),
            HistogramBlock(10 + 2f / 5, 10 + 4f / 5, 0),
            HistogramBlock(10 + 4f / 5, 10 + 6f / 5, 1),
            HistogramBlock(10 + 6f / 5, 10 + 8f / 5, 0),
            HistogramBlock(10 + 8f / 5, 12f, 1)
        )
        assertEquals(exceptedBlocks, data.blocks)
    }

    @Test
    fun testHistogramData_MaxCount() {
        val numbersData = listOf(1f, 1f, 2f, 3f, 1f, 2f)
        val data = HistogramData(3, numbersData)
        assertEquals(3, data.maxCount())
    }
}