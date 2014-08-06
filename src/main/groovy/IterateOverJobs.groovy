/**
 * Iterates over all items, identifing Folders and interating of the jobs inside
 * of the folders as well. Not that currently folder in folders are not supported
 */
import hudson.maven.MavenModuleSet
import com.cloudbees.hudson.plugins.folder.Folder

/**
 * All maven jobs ending with "-build"
 */
def relevantJobs = { job ->
  job instanceof MavenModuleSet && job.isBuildable() && job.name.endsWith("-build")
}

/**
 * Identify folders
 */
def folders = { item ->
  item instanceof Folder
}

def handleJob = { job ->
  // do something with the job...
}

Jenkins.instance.items.findAll(folders).each{ folder ->
  folder.items.findAll(relevantJobs).each(handleJob)
}


Jenkins.instance.items.findAll(relevantJobs).each(handleJob)
