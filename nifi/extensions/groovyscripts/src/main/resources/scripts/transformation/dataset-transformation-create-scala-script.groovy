// Get flow file
def flowFile = session.get()
if (!flowFile) return
// Create output directory
def inputFolder = flowFile.getAttribute("spark.input_folder")
def organization = flowFile.getAttribute("daf.organization")
def domain = flowFile.getAttribute("daf.domain")
def subDomain = flowFile.getAttribute("daf.subdomain")
def feed = flowFile.getAttribute("feed")
def feedts = flowFile.getAttribute("feedts")
def folder = new File(inputFolder + "/" + organization + "/" + domain + "__" + subDomain + "/" + feed + "/" + feedts)
if (!folder.exists()) folder.mkdirs()
// Write script
def script = "sqlContext.setConf(\"hive.exec.dynamic.partition\", \"true\")\n"
script = script + "sqlContext.setConf(\"hive.exec.dynamic.partition.mode\", \"nonstrict\")\n"
script = script + flowFile.getAttribute("metadata.dataTransformation.dataTransformScript")
script = script + ".withColumn(\"processing_dttm\", org.apache.spark.sql.functions.lit(\"" + feedts + "\"))"
script = script + ".write.mode(SaveMode.Overwrite)"
def isPreFeed = (flowFile.getAttribute("transform_script") != null)
def sparkVersion = flowFile.getAttribute("spark.version")
if (!isPreFeed && (sparkVersion == null || sparkVersion == "1")) {
    script = script + ".partitionBy(\"processing_dttm\")"
}
script = script + ".insertInto(\"" + domain + "__" + subDomain + "." + feed + (isPreFeed ? "" : "_feed") + "\")"
def scalaFile = new File(folder, "transform.scala")
scalaFile.write(script)
// Write field policies
def json = flowFile.getAttribute("metadata.table.fieldPoliciesJson")
if (json != null) {
    def jsonFile = new File(folder, feed + "_field_policy.json")
    jsonFile.write(json)
    flowFile = session.putAttribute(flowFile, "table_field_policy_json_file", jsonFile.getCanonicalPath())
}
// Output file path
flowFile = session.putAttribute(flowFile, "transform_script_file", scalaFile.getCanonicalPath())
session.transfer(flowFile, REL_SUCCESS)
