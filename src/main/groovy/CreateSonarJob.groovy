import jenkins.triggers.ReverseBuildTrigger
import hudson.plugins.copyartifact.CopyArtifact
import hudson.plugins.copyartifact.StatusBuildSelector
import hudson.plugins.sonar.SonarPublisher

/**
 * Use this after you have added the ArtifactArchiver to your build jobs.
 * This will create a new FreeStyleProject for the given job that execute sonar analysis.
 * It's good to have sonar running in it's own job, so that failures during analysis
 * won't mark the build as failed. This separation makes failure analysis easier.
 */
def handleJob = { job ->
  sonarJob = Jenkins.instance.createProject(FreeStyleProject.class, job.name + "-sonar")
  sonarJob.scm = job.scm
  sonarJob.addTrigger(new ReverseBuildTrigger(job.name, Result.SUCCESS))

  copyArtifacts = new CopyArtifact(
    job.name, // String projectName
    null, // String parameters
    new StatusBuildSelector(false), // hudson.plugins.copyartifact.BuildSelector selector
    "target/jacoco.exec, target/**/*.class, target/surefire-reports/*", // String target - should agree with what has been archived by the ArtifactArchiver
    null, // String filter
    false, // boolean flatten
    false, // boolean optional
    false // boolean fingerprintArtifacts
  )

  sonarJob.getBuildersList().replace(copyArtifacts)

  sonar = new SonarPublisher(
    null, // String installationName
    null, // String jobAdditionalProperties
    null // String mavenOpts
  )

  sonarJob.addPublisher(sonar)
}
