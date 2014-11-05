import hudson.model.*
import hudson.maven.*
import hudson.tasks.*

def newAntOptions="-Xmx1G -XX:MaxPermSize=512M"

// For each project
for(item in Hudson.instance.items) {
  // For each FreeStyleProject
  if(item instanceof FreeStyleProject) {
    println("JOB : " + item.name);
    // For each builder
    for (builder in item.builders){
      println("-" + builder);
      if (builder instanceof Ant) {
        println("-- ANT BUILDER");
        println("-- TARGETS:    " + builder.targets);
        println("-- NAME:       " + builder.antName);
        println("-- BUILDFILE:  " + builder.buildFile);
        println("-- PROPERTIES: " + builder.@properties);
        println("-- ANTOPTS:    " + builder.antOpts);
        def newBuilder = new Ant(builder.targets,builder.antName,newAntOptions,builder.buildFile,builder.@properties);
        item.buildersList.replace(newBuilder);
      }
    }
  }
}
