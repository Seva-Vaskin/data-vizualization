import java.io.File

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
    if (result.isEmpty()) {
        throw Exception("Data can't be empty")
    }
    return result
}

fun parseNumbersData(dataFile: String): NumbersData {
    val result = mutableListOf<Float>()
    for (line in File(dataFile).readLines()) {
        val number = line.toFloatOrNull() ?: throw Exception("\"$line\" is not a number")
        result.add(number)
    }
    if (result.isEmpty()) {
        throw Exception("Data can't be empty")
    }
    return result
}
