import org.junit.jupiter.api.Test
import org.jetbrains.skija.Rect
import kotlin.test.assertEquals

internal class TestRectTransformation {
    @Test
    fun getMiddleSquare_Horizontal() {
        val rect = Rect(0f, 0f, 10f, 5f)
        val square = getMiddleSquare(rect)
        val expected = Rect(2.5f, 0f, 7.5f, 5f)
        assertEquals(expected, square)
    }

    @Test
    fun getMiddleSquare_Vertical() {
        val rect = Rect(0f, 0f, 5f, 10f)
        val square = getMiddleSquare(rect)
        val expected = Rect(0f, 2.5f, 5f, 7.5f)
        assertEquals(expected, square)
    }

    @Test
    fun splitRectHorizontalIntoBlocks() {
        val rect = Rect(0f, 0f, 5f, 1f)
        val actual = splitRectHorizontalIntoBlocks(rect, 5)
        val expected = listOf(
            Rect(0f, 0f, 1f, 1f),
            Rect(1f, 0f, 2f, 1f),
            Rect(2f, 0f, 3f, 1f),
            Rect(3f, 0f, 4f, 1f),
            Rect(4f, 0f, 5f, 1f)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun splitRectVerticalIntoBlocks() {
        val rect = Rect(0f, 0f, 1f, 5f)
        val actual = splitRectVerticalIntoBlocks(rect, 5)
        val expected = listOf(
            Rect(0f, 0f, 1f, 1f),
            Rect(0f, 1f, 1f, 2f),
            Rect(0f, 2f, 1f, 3f),
            Rect(0f, 3f, 1f, 4f),
            Rect(0f, 4f, 1f, 5f)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun splitRectHorizontal() {
        val rect = Rect(0f, 0f, 10f, 5f)
        val (left, right) = splitRectHorizontal(rect, 0.2f)
        assertEquals(Rect(0f, 0f, 2f, 5f), left)
        assertEquals(Rect(2f, 0f, 10f, 5f), right)
    }

    @Test
    fun splitRectVertical() {
        val rect = Rect(0f, 0f, 10f, 5f)
        val (left, right) = splitRectVertical(rect, 0.2f)
        assertEquals(Rect(0f, 0f, 10f, 1f), left)
        assertEquals(Rect(0f, 1f, 10f, 5f), right)
    }

    @Test
    fun testMakeRectSmaller() {
        val rect = Rect(0f, 0f, 10f, 5f)
        val smallRect = makeRectSmaller(rect, 1.2f)
        val expected = Rect(1.2f, 1.2f, 8.8f, 3.8f)
        assertEquals(expected, smallRect)
    }
}