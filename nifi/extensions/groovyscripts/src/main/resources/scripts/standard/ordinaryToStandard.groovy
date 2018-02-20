import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.io.InputStreamCallback
import org.apache.nifi.processor.io.OutputStreamCallback

session = (ProcessSession) session

flowFile = session.get()
if (!flowFile) return

def metadataTableFieldStructure = flowFile.getAttribute("metadata.table.fieldStructure")
def dafStandardJson = flowFile.getAttribute("daf.standard.json")

def columnToJson = [:]
metadataTableFieldStructure.splitEachLine('\\|') { cell ->
    columnToJson.put(StringUtils.lowerCase(cell[0]), cell[2])
}

def ordinaryJson
session.read(flowFile, { inputStream ->
    ordinaryJson = new JsonSlurper().parse(inputStream)
} as InputStreamCallback)


standardJson = new JsonSlurper().parseText(dafStandardJson)

def writeJsonValue(json, path, value) {
    def paths = StringUtils.split(path, ".")
    def currPath = paths[0];
    if (paths.size() == 1) {
        if (currPath.contains("[")) {
            json[StringUtils.substringAfter(StringUtils.substringBefore(currPath, ']'), "[")] = value
        } else {
            if (json[currPath] instanceof String) {
                json[currPath] = value.toString()
            } else {
                json[currPath] = value
            }
        }
    } else {
        if (currPath.startsWith("[")) {
            writeJsonValue(json[StringUtils.substringAfter(StringUtils.substringBefore(currPath, ']'), "[").toInteger()], StringUtils.substringAfter(path, '].'), value)
        } else if (currPath.contains("[")) {
            writeJsonValue(json[StringUtils.substringBefore(path, '[')], '[' + StringUtils.substringAfter(path, '['), value)
        } else {
            writeJsonValue(json[currPath], StringUtils.substringAfter(path, currPath + '.'), value)
        }

    }
}

columnToJson.each { k, v ->
    if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
        writeJsonValue(standardJson, v, ordinaryJson[k])
    }
}

def standardJsonOutput = JsonOutput.toJson(standardJson)

flowFile = session.write(flowFile, { outputStream ->
    IOUtils.write(standardJsonOutput, outputStream)
} as OutputStreamCallback)

session.transfer(flowFile, REL_SUCCESS)