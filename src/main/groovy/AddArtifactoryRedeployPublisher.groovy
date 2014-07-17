import org.jfrog.hudson.ArtifactoryRedeployPublisher
import org.jfrog.hudson.ServerDetails

/**
 * Adds a publisher that publishes build artifacts to artifactory.
 */
def handleJob = { job ->

  /*
   * easiest way to get the artifactoryName is to configure a ArtifactoryRedeployPublisher
   * manually and look it up in the xml config of the job
   */
  server = new ServerDetails(
      "-1839878724@1400066745155", // String artifactoryName
      "http://localhost:8081/artifactory", // String artifactoryUrl
      "libs-release-local", // String repositoryKey
      "libs-snapshot-local", // String snapshotsRepositoryKey,
      null, // String downloadReleaseRepositoryKey
      null // String downloadSnapshotRepositoryKey
      )

  artifactoryDeployer = new ArtifactoryRedeployPublisher(
      server, // ServerDetails details
      true, // boolean deployArtifacts
      null, // org.jfrog.hudson.util.IncludesExcludes artifactDeploymentPatterns
      null, // org.jfrog.hudson.util.Credentials overridingDeployerCredentials,
      false, // boolean includeEnvVars
      null, // org.jfrog.hudson.util.IncludesExcludes envVarsPatterns
      true, // boolean deployBuildInfo
      false, // boolean evenIfUnstable
      false, // boolean runChecks
      null, // String violationRecipients
      false, // boolean includePublishArtifacts
      null, // String scopes,
      false, // boolean disableLicenseAutoDiscovery
      false, // boolean discardOldBuilds
      false, // boolean passIdentifiedDownstream
      true, // boolean discardBuildArtifacts
      null, // String matrixParams
      false, // boolean enableIssueTrackerIntegration
      false, // boolean aggregateBuildIssues
      "Released", // String aggregationBuildStatus
      false, // boolean allowPromotionOfNonStagedBuilds
      false, // boolean blackDuckRunChecks
      null, // String blackDuckAppName
      null, // String blackDuckAppVersion
      null, // String blackDuckReportRecipients
      null, // String blackDuckScopes
      false, // boolean blackDuckIncludePublishedArtifacts
      true, // boolean autoCreateMissingComponentRequests,
      true, // boolean autoDiscardStaleComponentRequests,
      false // boolean filterExcludedArtifactsFromBuild
      )

  job.publishers.replace(artifactoryDeployer)
}
