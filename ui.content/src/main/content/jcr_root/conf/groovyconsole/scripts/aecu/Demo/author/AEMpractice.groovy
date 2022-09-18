import com.day.cq.replication.Replicator;
import com.day.cq.replication.ReplicationActionType

def queryInner = sqlQueryInner()
def resultInner = queryInner.execute()
resultInner.nodes.each{nodeInner ->
    def innerPosition = nodePosition(nodeInner)
    wrap(nodeInner)
    def innerWrapper = nodeInner.getParent()
    def wrapperContainer = innerWrapper.getParent()
    wrapperContainer.orderBefore(innerWrapper.getName(), wrapperContainer.getNodes().getAt(innerPosition).getName())
    replicate(findPageContentNode(nodeInner).getPath())
}

save()

def sqlQueryInner() {
    def queryManager = session.workspace.queryManager
    def statement = "SELECT * FROM [nt:unstructured] AS comp WHERE ISDESCENDANTNODE(comp," +
            " '/content/Demo') AND [sling:resourceType] = 'Demo/components/inner'"
    queryManager.createQuery(statement, "JCR-SQL2")
}

def wrap(Node node) {
    def nodeName = node.getName()
    def parentNode = node.getParent()
    parentNode.addNode("inner-wrapper").setProperty("sling:resourceType", "Demo/components/wrapper")
    session.move(node.getPath(), parentNode.getPath() + "/inner-wrapper" + "/" + node.getName())
}

def findPageContentNode(Node node) {
    if (node.getName().equals("jcr:content")) {
        return node
    }
    def newNode = node.getParent()
    findPageContentNode(newNode)
}

def replicate(String path) {
    def replicator = getService(Replicator.class)
    replicator.replicate(session, ReplicationActionType.ACTIVATE, path)
}

def nodePosition (Node node) {
    def nodesIterator = node.getParent().getNodes()
    def num = 0
    while (nodesIterator.hasNext()) {
        def nextNode = nodesIterator.next()
        if (nextNode.getName() == "inner" ) {
            return num
        }
        num += 1
    }
    return 0
}
