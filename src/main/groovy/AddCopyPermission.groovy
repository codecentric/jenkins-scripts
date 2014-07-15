import hudson.plugins.copyartifact.CopyArtifactPermissionProperty

/**
 * Add a CopyArtifacts permission for all jobs that have the same name and a suffix
 * For example, if you have a Job called MyJob and a corresponding Maven Sonar Build
 * Job that's called MyJob-sonar, the sonar job will be allowed to copy artifacts.
 */
def handleJob = { job ->
  prop = new CopyArtifactPermissionProperty()
  prop.projectNameList.add(job.name + "*")
  job.addProperty(prop)
}
