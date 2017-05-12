node('maven'){
  def mvnCmd = "mvn"
  def sonarUrl = env.SONAR_URL
  def v
  stage('Build'){
    git branch: "master", url: "https://github.com/victornc83/sb-base.git"
    v = version()
    sh "${mvnCmd} clean package -DskipTests=true"
  }
  stage('Unit Tests'){
    sh "${mvnCmd} clean org.jacoco:jacoco-maven-plugin:prepare-agent verify -DskipITs=true"
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', excludes: null, fingerprint: true, onlyIfSuccessful: true])
    try {
      step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    } catch (err) {
      echo "No unit tests result were found: ${err}"
    }
  }
  stage('Analysis'){
    echo "Analysis in Sonar ${sonarUrl}"
    sh "${mvnCmd} sonar:sonar -Dsonar.host.url=${sonarUrl}"
  }
  stage('Deploy'){
    sh "rm -rf oc-build && mkdir -p oc-build/deployments"
    sh "cp target/*.jar oc-build/deployments/"
    openshift.withCluster('clusterlab'){
      echo "Hello from mycluster's default project: ${openshift.project()}"
      def sb = openshift.startBuild("sb-base-container","--from-dir=oc-build","--wait=true")
      echo "startBuild created: ${sb.count()} objects : ${sb.names()}"
      sb.logs()
      def deploy = openshift.selector("dc","sb-base")
      def deploy_ver = openshift.raw("get","dc","sb-base","--template={{.status.latestVersion}}").out.trim()
      timeout(5){
        deploy.watch {
          return it.object().status.unavailableReplicas != 1
        }
      }
      openshiftVerifyDeployment
      deploy.logs()
    }
  }
  stage('Integration Tests'){
    echo "Running integration tests"
    openshift.withCluster('clusterlab'){
      def svc = openshift.selector("svc","sb-base")
      def host = svc.object().spec.clusterIP
      def ports = svc.object().spec.ports
      for (p in ports) {
        sh "${mvnCmd} clean org.jacoco:jacoco-maven-plugin:prepare-agent-integration verify -Dsurefire.skip=true -Dservice.endpoint=http://${host}:${p.port}"
      }
      try {
        step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
      } catch (err) {
        echo "No integration test result were found: ${err}"
      }
    }
  }
  stage('Promotion'){
    openshift.withCluster('clusterlab'){
      openshift.tag("sb-base:latest","sb-base:${v}")
    }
  }
}
def version() {
  def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}
