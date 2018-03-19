import org.apache.nifi.controller.ControllerService
import org.apache.nifi.processor.ProcessSession

session = (ProcessSession) session

flowFile = session.get()
if (!flowFile) return

lookup = context.controllerServiceLookup
impalaThriftServiceName = impalaThriftServiceName.evaluateAttributeExpressions(flowFile).value
invalidateMetadata = invalidateMetadata.evaluateAttributeExpressions(flowFile).value

def dbcpServiceCS = lookup.getControllerServiceIdentifiers(ControllerService).find {
    cs -> lookup.getControllerServiceName(cs) == impalaThriftServiceName
}
def impalaConnection = lookup.getControllerService(dbcpServiceCS)?.getConnection()
def statement = impalaConnection.prepareCall(invalidateMetadata)

try {
    statement.execute()
} catch (Exception ex) {
    log.error(ex.message)
    flowFile = session.putAttribute(flowFile, context.getName() + " Exit code", "FAILED")
    flowFile = session.putAttribute(flowFile, context.getName() + " Exit description", ex.message)
    flowFile = session.putAttribute(flowFile, "kylo.jobExitDescription", ex.message)
    impalaConnection?.close()
    session.transfer(flowFile, REL_FAILURE)
    return -1
}

impalaConnection?.close()
session.transfer(flowFile, REL_SUCCESS)