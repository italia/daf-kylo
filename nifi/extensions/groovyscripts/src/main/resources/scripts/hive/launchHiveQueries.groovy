import groovy.sql.Sql
import org.apache.nifi.controller.ControllerService
import org.apache.nifi.processor.ProcessSession

session = (ProcessSession) session

flowFile = session.get()
if (!flowFile) return

lookup = context.controllerServiceLookup
hiveThriftServiceName = hiveThriftServiceName.evaluateAttributeExpressions(flowFile).value
createTableRoleQueryValue = createTableRoleQuery.evaluateAttributeExpressions(flowFile).value
grantRoleQueryList = grantRoleQuery.evaluateAttributeExpressions(flowFile).value

def dbcpServiceCS = lookup.getControllerServiceIdentifiers(ControllerService).find {
    cs -> lookup.getControllerServiceName(cs) == hiveThriftServiceName
}
def hiveConnection = lookup.getControllerService(dbcpServiceCS)?.getConnection()
def sql = new Sql(hiveConnection)

try {
    // this can fail
    sql.execute(createTableRoleQueryValue)
} catch (Exception ex) {
    log.info(ex.message)
}

try {
    // this should not fail
    grantRoleQueryList.split(";").each {
        sql.execute(it)
    }
} catch (Exception ex) {
    log.error(ex.message)
    flowFile = session.putAttribute(flowFile, context.getName() + " Exit code", "FAILED")
    flowFile = session.putAttribute(flowFile, context.getName() + " Exit description", ex.message)
    flowFile = session.putAttribute(flowFile, "kylo.jobExitDescription", ex.message)
    hiveConnection?.close()
    session.transfer(flowFile, REL_FAILURE)
    return -1
}

hiveConnection?.close()
session.transfer(flowFile, REL_SUCCESS)