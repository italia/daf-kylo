import groovy.sql.Sql
import org.apache.nifi.controller.ControllerService
import org.apache.nifi.processor.ProcessSession

session = (ProcessSession) session

flowFile = session.get()
if (!flowFile) return

dafDbRole = "db_tecnologia__monitoraggio_role" //flowFile.getAttribute("daf.db.role")

lookup = context.controllerServiceLookup
hiveThriftServiceName = hiveThriftServiceName.evaluateAttributeExpressions(flowFile).value

def dbcpServiceCS = lookup.getControllerServiceIdentifiers(ControllerService).find {
    cs -> lookup.getControllerServiceName(cs) == hiveThriftServiceName
}
def hiveConnection = lookup.getControllerService(dbcpServiceCS)?.getConnection()

try {

    def checkRoleQuery = "SHOW grant role " + dafDbRole
    def sql = new Sql(hiveConnection)
    def rows = sql.rows(checkRoleQuery)
    hiveConnection?.close()
    flowFile = session.putAttribute(flowFile, "rows", rows.first().toString())
    session.transfer(flowFile, REL_SUCCESS)

} catch (Exception ex) {
    log.error(ex)

    flowFile = session.putAttribute(flowFile, context.getName() + " Exit code", "FAILED")
    flowFile = session.putAttribute(flowFile, context.getName() + " Exit description", ex.message)
    flowFile = session.putAttribute(flowFile, "kylo.jobExitDescription", ex.message)
    hiveConnection?.close()
    session.transfer(flowFile, REL_FAILURE)
    return -1
}
