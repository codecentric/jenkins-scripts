import hudson.model.*
import hudson.triggers.*
  
for(item in Hudson.instance.items) {
  if(item.scm instanceof hudson.plugins.git.GitSCM ){
    println("Project: " + item.name )
    println("Credentials: " + item.scm.userRemoteConfigs[0].credentialsId) 
    
    println("Set credentials")
    item.scm.userRemoteConfigs[0].credentialsId = "55b59e2c-8d2a-1285-85eb-d6d8b34d7399"
  }  
}

