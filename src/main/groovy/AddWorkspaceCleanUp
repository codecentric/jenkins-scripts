import hudson.model.*
import hudson.maven.*
import hudson.tasks.*
import hudson.plugins.ws_cleanup.*
  
// Requires installed Workspace Cleanup Plugin
// For each project
for(item in Hudson.instance.items) {
	// For each FreeStyleProject
	if(item instanceof FreeStyleProject) {
		println("JOB : " + item.name);		
		item.buildWrappersList.add(new PreBuildCleanup(null, false, "", ""))
		println(item.buildWrappers);
	}
}
