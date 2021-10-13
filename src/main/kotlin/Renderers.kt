import org.jetbrains.skija.*
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer

abstract class Renderer : SkiaRenderer {
    val typeface = Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf")
    val font = Font(typeface, 40f)
    val paint = Paint().apply {
        color = Colors.YELLOW.code
        mode = PaintMode.STROKE
        strokeWidth = 10f
    }
}

class CycleDiagramRenderer(private val layer: SkiaLayer, val data: CycleDiagramData) : Renderer() {
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

class HistogramRenderer(private val layer: SkiaLayer, val data: HistogramData) : Renderer() {
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


