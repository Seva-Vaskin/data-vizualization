import org.jetbrains.skija.*
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer
import kotlin.random.Random as Random

abstract class Renderer : SkiaRenderer {
    val typeface = Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf")
    val font = Font(typeface, 40f)

}

class CycleDiagramRenderer(private val layer: SkiaLayer, val data: NumberAndStringData) : Renderer() {
    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)
        val w = (width / contentScale).toInt()
        val h = (height / contentScale).toInt()
        val fillPaint = Paint().apply {
            mode = PaintMode.FILL
        }
        val strokePaint = Paint().apply {
            mode = PaintMode.STROKE
            color = 0xff000000.toInt()
            strokeWidth = 5f
        }
        val diagramRect = Rect(0f, 0f, w.toFloat(), h.toFloat())
        val diagramSquare = getMiddleSquare(diagramRect)
        fillPaint.color = getColorByIndex(0)
        var prefSum = 0f
        for (i in data.data.indices) {
            fillPaint.color = getColorByIndex(i)
            val startAngle = prefSum / data.sum * 360
            val sweepAngle = data.data[i].number / data.sum * 360
            canvas.drawArc(diagramSquare.left, diagramSquare.top, diagramSquare.right, diagramSquare.bottom,
                startAngle, sweepAngle, true, fillPaint)
            canvas.drawArc(diagramSquare.left, diagramSquare.top, diagramSquare.right, diagramSquare.bottom,
                startAngle, sweepAngle, true, strokePaint)
            prefSum += data.data[i].number
        }
        // РИСОВАНИЕ
        layer.needRedraw()

    }
}

class HistogramRenderer(private val layer: SkiaLayer, val data: NumberAndStringData) : Renderer() {
    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)
        val w = (width / contentScale).toInt()
        val h = (height / contentScale).toInt()
        // РИСОВАНИЕ
        layer.needRedraw()
        TODO()
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

fun generateRandomColor(): Int {
    val r = Random.nextInt() % 256
    val g = Random.nextInt() % 256
    val b = Random.nextInt() % 256
    return ((0xff * 256 + r) * 256 + g) * 256 + b
}

fun getColorByIndex(index: Int): Int {
    return if (index <= Colors.values().size) {
        Colors.values()[index].code
    } else {
        generateRandomColor()
    }
}


