import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.io.InputStreamCallback
import org.apache.nifi.processor.io.OutputStreamCallback

session = (ProcessSession) session

flowFile = session.get()
if (!flowFile) return

String separatorChar = separatorChar.evaluateAttributeExpressions(flowFile).value
String quoteChar = quoteChar.evaluateAttributeExpressions(flowFile).value
String escapeChar = escapeChar.evaluateAttributeExpressions(flowFile).value

CSVReader reader
// Cast a closure with an inputStream parameter to InputStreamCallback
session.read(flowFile, { inputStream ->
    reader = new CSVReader(new InputStreamReader(inputStream), separatorChar.charAt(0), quoteChar.charAt(0), escapeChar.charAt(0))
} as InputStreamCallback)


flowFile = session.write(flowFile, { outputStream ->
    CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream), separatorChar.charAt(0), quoteChar.charAt(0), escapeChar.charAt(0))
    reader.each { line ->
        def cleanLine = []

        line.each { cell ->
            cleanLine.add(cell.replaceAll("\r\n|\n|\r", " "))
        }
        String[] cleanLineArr = new String[cleanLine.size()];
        writer.writeNext(cleanLine.toArray(cleanLineArr));
    }
    writer.flush()
} as OutputStreamCallback)

session.transfer(flowFile, REL_SUCCESS)