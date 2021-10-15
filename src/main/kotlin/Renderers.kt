import org.jetbrains.skija.*
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer
import kotlin.math.max
import kotlin.math.min

/**
 * Describes common part of all renderers in project
 */
abstract class Renderer : SkiaRenderer {
    private val typeface = Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf")
    val maxFontSize = 500
    val fonts = List(maxFontSize + 1) {
        Font(typeface, it.toFloat())
    }
    val fillPaint = Paint().apply {
        mode = PaintMode.FILL
    }
    val strokePaint = Paint().apply {
        mode = PaintMode.STROKE
        color = 0xff000000.toInt()
        strokeWidth = 5f
    }

    fun fitTextSizeToRect(rect: Rect, text: String): Float {
        val basicSize = min(rect.height, maxFontSize.toFloat())
        if (fonts[basicSize.toInt()].measureText(text, fillPaint).width <= rect.width)
            return basicSize
        var minimalSize = 0
        var maximalSize = basicSize.toInt()
        while (maximalSize - minimalSize > 1) {
            val currentSize = (maximalSize + minimalSize) / 2
            if (fonts[currentSize].measureText(text, fillPaint).width <= rect.width)
                minimalSize = currentSize
            else
                maximalSize = currentSize
        }
        return minimalSize.toFloat()
    }
}

/**
 * Describes how to render cycle diagram
 */
class CycleDiagramRenderer(private val layer: SkiaLayer, val data: CycleDiagramData) : Renderer() {
    val legend = data.toLegend()
    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)
        val w = (width / contentScale).toInt()
        val h = (height / contentScale).toInt()
        val windowRect = Rect(0f, 0f, w.toFloat(), h.toFloat())
        var (diagramRect, legendRect) = (if (w < h) ::splitRectVertical else ::splitRectHorizontal)(windowRect, 0.5f)
        diagramRect = makeRectSmaller(diagramRect, 5f)
        legendRect = makeRectSmaller(legendRect, 5f)
        drawCycleDiagram(canvas, diagramRect, fillPaint, strokePaint)
        drawLegend(canvas, legendRect, legend)
        layer.needRedraw()
    }

    private fun drawCycleDiagram(canvas: Canvas, diagramRect: Rect, fillPaint: Paint, strokePaint: Paint) {
        val diagramSquare = getMiddleSquare(diagramRect)
        var prefSum = 0f
        for (i in data.data.indices) {
            fillPaint.color = data.colorCodes[i]
            val startAngle = prefSum / data.sum * 360
            val sweepAngle = data.data[i].number / data.sum * 360
            canvas.drawArc(
                diagramSquare.left, diagramSquare.top, diagramSquare.right, diagramSquare.bottom,
                startAngle, sweepAngle, true, fillPaint
            )
            canvas.drawArc(
                diagramSquare.left, diagramSquare.top, diagramSquare.right, diagramSquare.bottom,
                startAngle, sweepAngle, true, strokePaint
            )
            prefSum += data.data[i].number
        }
    }

    private fun drawLegend(canvas: Canvas, legendRect: Rect, legend: List<CycleDiagramLegend>) {
        // split drawable rectangular into blocks
        val blocks = splitRectVerticalIntoBlocks(legendRect, legend.size)
        val ballRect = mutableListOf<Rect>()
        val textRect = mutableListOf<Rect>()
        for (i in legend.indices) {
            var (ball, text) = splitRectHorizontal(blocks[i], 0.15f)
            ball = makeRectSmaller(ball, 4f)
            ballRect.add(ball)
            textRect.add(text)
        }
        // find font size
        var fontSize = maxFontSize.toFloat()
        for (i in legend.indices)
            fontSize = min(fontSize, fitTextSizeToRect(textRect[i], legend[i].title))
        // draw
        for (i in legend.indices) {
            // draw ball
            fillPaint.color = legend[i].colorCode
            val radius = min(fontSize / 2, ballRect[i].width / 2)
            val font = fonts[fontSize.toInt()]
            canvas.drawCircle(
                ballRect[i].left + radius,
                ballRect[i].bottom - font.measureText(legend[i].title, fillPaint).height / 2,
                radius, fillPaint
            )
            // draw text
            fillPaint.color = 0xff000000.toInt() // black
            canvas.drawString(legend[i].title, textRect[i].left, textRect[i].bottom, fonts[fontSize.toInt()], fillPaint)
        }
    }
}

/**
 * Describes how to render histogram
 */
class HistogramRenderer(private val layer: SkiaLayer, val data: HistogramData) : Renderer() {
    private val blackFillStrokePaint = Paint().apply {
        mode = PaintMode.STROKE_AND_FILL
        color = 0xff000000.toInt()
        strokeWidth = 1f
    }

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)
        val w = (width / contentScale).toInt()
        val h = (height / contentScale).toInt()
        val windowRect = Rect(0f, 0f, w.toFloat(), h.toFloat())
        val r1 = makeRectSmaller(windowRect, 10f)
        var (verticalLineRect, r2) = splitRectHorizontal(r1, max(0.07f, 30 / r1.width))
        var (histogramRect, horizontalLineRect) = splitRectVertical(r2, min(0.93f, 1 - 30 / r2.height))
        verticalLineRect = makeRectSmaller(verticalLineRect, 5f)
        horizontalLineRect = makeRectSmaller(horizontalLineRect, 5f)
        // histogram
        val blocksRect = splitRectHorizontalIntoBlocks(histogramRect, data.columnsNumber)
        val maxCount = data.maxCount()
        for (i in blocksRect.indices) {
            val blockHeight = blocksRect[i].height * data.blocks[i].count / maxCount
            val rect = Rect(
                blocksRect[i].left, blocksRect[i].bottom - blockHeight,
                blocksRect[i].right, blocksRect[i].bottom
            )
            canvas.drawRect(rect, blackFillStrokePaint)
        }
        // numbers
        val fontSize = min(
            fitTextSizeToRect(verticalLineRect, maxCount.toString()),
            fitTextSizeToRect(horizontalLineRect, data.minValue.toString() + "  " + data.maxValue.toString()),
        )
        val font = fonts[fontSize.toInt()]
        canvas.drawString(
            data.minValue.toString(), horizontalLineRect.left, horizontalLineRect.bottom,
            font, blackFillStrokePaint
        )
        canvas.drawString(
            data.maxValue.toString(),
            horizontalLineRect.right - font.measureText(data.maxValue.toString(), blackFillStrokePaint).width,
            horizontalLineRect.bottom, font, blackFillStrokePaint
        )
        canvas.drawString(
            maxCount.toString(),
            verticalLineRect.left,
            verticalLineRect.top + font.measureText(maxCount.toString(), blackFillStrokePaint).height,
            font, blackFillStrokePaint
        )
        layer.needRedraw()

    }
}
