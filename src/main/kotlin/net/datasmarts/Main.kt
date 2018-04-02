package net.datasmarts

import weka.core.converters.ArffLoader
import weka.core.converters.ArffSaver
import weka.core.converters.CSVLoader
import weka.core.converters.CSVSaver
import java.io.File

fun main(args: Array<String>) {
    require(args.size >= 3, { "Arguments size must be at least 3" })

    val mode = args[0].toInt()
    val inputFilePath = args[1]
    val outputFilePath = args[2]

    val inputFile = File(inputFilePath)
    val outputFile = File(outputFilePath)

    val isHeaderAbsent = {
        if (args.size == 4) {
            args[2].toBoolean()
        } else {
            true
        }
    }

    when (mode) {
        1 -> {
            println("MODE 1 selected: CSV -> ARFF")
            csvToArff(inputFile, outputFile, isHeaderAbsent())
        }
        2 -> {
            println("MODE 2 selected: ARFF -> CSV")
            arffToCsv(inputFile, outputFile)
        }
        else -> {
            throw NoSuchElementException("Unknown mode $mode. Please, chose 1 or 2")
        }
    }
}

/**
 * @param inputFile CSV file to be converted to ARFF.
 * @param outputFile Destination file to save the contents of inputFile in ARFF format.
 */
fun csvToArff(inputFile: File, outputFile: File, headerAbsent: Boolean = false) {
    require(inputFile.exists(), { "Input file ${inputFile.absolutePath} doesn't exist." })

    val csvLoader = CSVLoader()
    csvLoader.noHeaderRowPresent = headerAbsent
    csvLoader.setSource(inputFile)
    val csvData = csvLoader.dataSet

    val arffSaver = ArffSaver()
    arffSaver.instances = csvData
    arffSaver.setFile(outputFile)
    arffSaver.writeBatch()
}

/**
 * @param inputFile ARFF file to be converted to CSV.
 * @param outputFile Destination file to save the contents of inputFile in CSV format.
 */
fun arffToCsv(inputFile: File, outputFile: File) {
    require(inputFile.exists(), { "Input file ${inputFile.absolutePath} doesn't exist." })

    val arffLoader = ArffLoader()
    arffLoader.setSource(inputFile)
    val arffData = arffLoader.dataSet

    val csvSaver = CSVSaver()
    csvSaver.instances = arffData
    csvSaver.setFile(outputFile)
    csvSaver.writeBatch()
}