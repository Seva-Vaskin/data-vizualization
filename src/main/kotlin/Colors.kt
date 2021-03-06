import kotlin.random.Random

enum class Colors(val code: Int) {
    RED(0xffff3333.toInt()),
    YELLOW(0xffffe433.toInt()),
    GREEN(0xff88ff33.toInt()),
    CYAN(0xff33ffbc.toInt()),
    BLUE(0xff3344ff.toInt()),
    PURPLE(0xff8b33ff.toInt()),
    PINK(0xfffa33ff.toInt()),
    ORANGE(0xffff9433.toInt()),
    LIGHT_GREY(0xffb0b0b0.toInt()),
    DARK_GREY(0xff4a4a4a.toInt()),
    DARK_RED(0xff9c0b0b.toInt()),
    DARK_GREEN(0xff0b6b1b.toInt()),
    DARK_PURPLE(0xff5c0b6b.toInt()),
    BROWN(0xff6b2c0b.toInt()),
    LIGHT_BLUE(0xff33e6ff.toInt()),
    LIGHT_PINK(0xffff8686.toInt()),
    LIGHT_ORANGE(0xffffc186.toInt()),
    LIGHT_GREEN(0xff9aff98.toInt()),
    LIGHT_PURPLE(0xffb498ff.toInt()),
    LIGHT_YELLOW(0xfffeff98.toInt())
}

fun generateRandomColor(): Int {
    val r = Random.nextInt() % 256
    val g = Random.nextInt() % 256
    val b = Random.nextInt() % 256
    return ((0xff * 256 + r) * 256 + g) * 256 + b
}

fun getColorByIndex(index: Int): Int {
    return if (index < Colors.values().size) {
        Colors.values()[index].code
    } else {
        generateRandomColor()
    }
}