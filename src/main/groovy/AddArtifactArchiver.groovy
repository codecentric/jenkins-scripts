import hudson.tasks.ArtifactArchiver

/**
 * Adds an element archiver to the job, that archives results of the build.
 * It makes sense to use this in conjunction with a CopyPermission (see AddCopyPermission.groovy)
 * to allow other projects to copy the archived artifacts.
 */
def handleJob = { job ->
  archiver = new ArtifactArchiver(
      "target/jacoco.exec, target/*.jar, target/**/*.class, target/surefire-reports/*",  // artifacts
      null, // excludes
      true, // latestOnly
      false, // allowEmptyArchive
      true // onlyIfSuccessful
      )
  job.publishers.replace(archiver)
}
