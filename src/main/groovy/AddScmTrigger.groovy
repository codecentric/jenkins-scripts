import hudson.triggers.*

def handleJob = { job ->
  job.addTrigger(new SCMTrigger("H 23 * * 1-5"))
  job.save()
}
