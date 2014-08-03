import hudson.plugins.websvn2.*
import hudson.scm.*

// Add a new browser (WebSVN2 in this example) to an existing SCM-setting of a job.
// For this a new SCM must be created as a copy of the old one, as the browser cannot 
// be set by it's own.
def transform(job) {
  // handle branches and trunk differently:
  if (job.scm.locations[0].remote.contains("branches")) {
    matcher = (job.scm.locations[0].remote =~ /svn\/Project\/(.+)\/branches\/(.+)/)
    projectSvnName = matcher[0][1]
    branchName = matcher[0][2]
    url = "http://localhost/websvn/listing.php?repname=Project&path=/$projectSvnName/branches/$branchName"
  } else {
    matcher = (job.scm.locations[0].remote =~ /svn\/Project\/(.+)\/trunk/)
    projectSvnName = matcher[0][1]
    url = "http://localhost/websvn/listing.php?repname=Project&path=/$projectSvnName/trunk/"
  }
  
  oldScm = job.scm
  browser = new hudson.plugins.websvn2.WebSVN2RepositoryBrowser(new URL(url))
  newScm = new SubversionSCM(Arrays.asList(oldScm.locations), oldScm.workspaceUpdater,
                             browser, oldScm.excludedRegions, oldScm.excludedUsers, oldScm.excludedRevprop, oldScm.excludedCommitMessages,
                             oldScm.includedRegions, oldScm.ignoreDirPropChanges, oldScm.filterChangelog, oldScm.additionalCredentials)
  job.scm = newScm
  job.save()
}
