## Spring Boot application
Running spring boot app directly in your Openshift/Kubernetes cluster using Fabric8 maven plugin.

[Fabric8 maven plugin](https://github.com/fabric8io/fabric8-maven-plugin)

To compile and build image in Openshift:
```
mvn clean package fabric8:build
```
To deploy in Openshift:
```
mvn fabric8:apply
```
