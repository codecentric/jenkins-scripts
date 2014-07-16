/**
 * This script gathers all cron triggers and displays them above a given view tab.
 * The result will look like the following.
 *
 * +------------------+----------------------+-----------------------+
 * | Job Name	      | Next Run             | Cron                  |
 * +------------------+----------------------+-----------------------+
 * | Project A - CI	  | 07:15 - 21.8.2014    | 15 07 21 8 *          |
 * | Project B - CI	  | 11:25 - 24.8.2014    | 25 11 24 8 *          |
 * +------------------+----------------------+-----------------------+
 */

import hudson.model.*
import hudson.triggers.*

def viewName = "Default"
def viewTitle = "Build Triggers"

for(view in Hudson.instance.views) {
  if( view.name == viewName ) {
    view.description = "<h3>" + viewTitle + "</h3>"
    view.description <<= "<table id=\"projectstatus\"><tr><th>Job Name</th><th>Next Run</th><th>Cron</th></tr>"

    for(item in Hudson.instance.items) {
    for(trigger in item.triggers.values()) {
    if(trigger instanceof TimerTrigger) {
          view.description <<= "<tr><td>" + item.name + "</td>"
          items = trigger.spec.split(' ')
          if( items.length == 5 ) {
            view.description <<= "<td>" + items[1] +":"+ items[0] + " - " + items[2] + "." + items[3] + "." + (new Date().year + 1900) + "</td>"
          } else {
            view.description <<= "<td>" + "</td>"
          }
          view.description <<= "<td>" + trigger.spec + "</td></tr>"
        }
      }
    }

    view.description <<= "</table>"
  }
}