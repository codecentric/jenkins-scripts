/**
 * List all cron trigger times for all jobs
 */
import hudson.model.*
import hudson.triggers.*

for(item in Hudson.instance.items) {
  for(trigger in item.triggers.values()) {
    if(trigger instanceof TimerTrigger) {
      println("Trigger for Job: " + item.name)
      println(trigger.spec + '\n')
    }
  }
}