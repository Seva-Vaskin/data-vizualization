import org.jetbrains.skija.Rect

fun makeRectSmaller(rect: Rect, border: Float): Rect {
    return Rect(rect.left + border, rect.top + border, rect.right - border, rect.bottom - border)
}

fun splitRectVertical(rect: Rect, factor: Float): Pair<Rect, Rect> {
    assert(factor in 0.0..1.0)
    return Pair(
        Rect(rect.left, rect.top, rect.right, rect.top + rect.height * factor),
        Rect(rect.left, rect.top + rect.height * factor, rect.right, rect.bottom)
    )
}

fun splitRectHorizontal(rect: Rect, factor: Float): Pair<Rect, Rect> {
    assert(factor in 0.0..1.0)
    return Pair(
        Rect(rect.left, rect.top, rect.left + rect.width * factor, rect.bottom),
        Rect(rect.left + rect.width * factor, rect.top, rect.right, rect.bottom)
    )
}

fun splitRectVerticalIntoBlocks(rect: Rect, count: Int): List<Rect> {
    val heightOfBlock = rect.height / count
    return List<Rect>(count) {
        Rect(rect.left, rect.top + it * heightOfBlock, rect.right, rect.top + (it + 1) * heightOfBlock)
    }
}

fun getMiddleSquare(rect: Rect): Rect {
    val h = rect.height
    val w = rect.width
    return if (h < w)
        Rect(
            rect.left + (w - h) / 2,
            rect.top,
            rect.right - (w - h) / 2,
            rect.bottom
        )
    else
        Rect(
            rect.left,
            rect.top + (h - w) / 2,
            rect.right,
            rect.bottom - (h - w) / 2
        )
}