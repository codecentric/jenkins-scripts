import hudson.model.*;
import hudson.util.*;
import jenkins.model.*;
import hudson.FilePath.FileCallable;
import hudson.slaves.OfflineCause;
import hudson.node_monitors.*;

/**
 * This script can be used to cleanup the workspace directories on all connected Jenkins slaves.
 * All slaves with a disk usage below "thresholdInMB" will be tidied up.
 * Workspaces of running Jobs will be ignored.
 */

thresholdInMB = 1500

for (node in Jenkins.instance.nodes) {

    computer = node.toComputer()
    if (computer.getChannel() == null) continue

    rootPath = node.getRootPath()
    size = DiskSpaceMonitor.DESCRIPTOR.get(computer).size
    slaveNodeSize = size / (1024 * 1024) as int

    println("slave: " + node.getDisplayName() + ", free space: " + slaveNodeSize + " MB")

    if (slaveNodeSize < thresholdInMB) {

        println("turning off slave temporarily")			
        computer.setTemporarilyOffline(true, new hudson.slaves.OfflineCause.ByCLI("disk cleanup on slave"))

        for (item in Jenkins.instance.items) {
            jobName = item.getFullDisplayName()

            if (item.isBuilding()) {
                println(".. job " + jobName + " is currently running, skip")
                continue
            }

            println(".. wiping out workspace of job " + jobName)

            workspacePath = node.getWorkspaceFor(item)
            if (workspacePath == null) {
                println(".... could not get workspace path")
                continue
            }

            println(".... workspace = " + workspacePath)	

            pathAsString = workspacePath.getRemote()
            if (workspacePath.exists()) {
                workspacePath.deleteRecursive()
                println(".... deleted from location " + pathAsString)					
            } else {
                println(".... nothing to delete at " + pathAsString)
            }
        }

        println("bringing slave back online")	
        computer.setTemporarilyOffline(false, null)

    }	
}
