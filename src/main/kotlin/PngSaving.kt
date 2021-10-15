import org.jetbrains.skija.Surface
import org.jetbrains.skiko.SkiaLayer
import java.nio.file.Files
import java.nio.file.Path

fun cycleDiagramToPng(cycleDiagramData: CycleDiagramData, pngFile: String) {
    val surface = Surface.makeRasterN32Premul(640, 360)
    val canvas = surface.canvas
    val renderer = CycleDiagramRenderer(SkiaLayer(), cycleDiagramData)
    renderer.putObjectsOnCanvas(canvas, surface.width, surface.height)
    val data = surface.makeImageSnapshot().encodeToData() ?: throw Exception("Can't generate image snapshot")
    val pngBytes = data.bytes
    Files.write(Path.of(pngFile), pngBytes)
}

fun histogramToPng(histogramData: HistogramData, pngFile: String) {
    val surface = Surface.makeRasterN32Premul(640, 360)
    val canvas = surface.canvas
    val renderer = HistogramRenderer(SkiaLayer(), histogramData)
    renderer.putObjectsOnCanvas(canvas, surface.width, surface.height)
    val data = surface.makeImageSnapshot().encodeToData() ?: throw Exception("Can't generate image snapshot")
    val pngBytes = data.bytes
    Files.write(Path.of(pngFile), pngBytes)
}