## Spring Boot application
Running spring boot app directly in your Openshift/Kubernetes cluster using Fabric8 maven plugin.

[Fabric8 maven plugin](https://github.com/fabric8io/fabric8-maven-plugin)

### Using maven plugin

To compile and build image in Openshift:
```
mvn clean package fabric8:build
```
To deploy in Openshift:
```
mvn fabric8:apply
```

### Using OC client

Using oc to create build from our local compiled artifact:
```
$ git clone https://github.com/victornc83/sb-base.git
$ cd ./sb-base
$ mvn clean package -DskipTests=true
$ oc new-build --name=sb-base-1 --binary --image-stream=openshift/openjdk18-openshift
$ oc start-build sb-base-1 --from-dir=. --follow
$ oc new-app sb-base-2
$ oc set probe dc/sb-base-2 --readiness --get-url=https://:8080/ --initial-delay-seconds=90
$ oc set probe dc/sb-base-2 --liveness --get-url=https://:8080/ --initial-delay-seconds=90
```
