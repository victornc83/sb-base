node ('maven'){
  @Library('github.com/victornc83/jenkins-library@master')

  def mvnCmd = 'mvn'
  def sonarUrl = env.SONAR_URL

  stage('Git Checkout'){
    echo "Checking out git repository"
    checkout scm
  }

  stage('Build'){
    echo "Building project"
    sh "${mvnCmd} clean package -DskipTests=true"
  }

  stage('Unit Tests') {
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

  stage('Deploy in Dev'){
    echo 'Building docker image and deploying to Dev'
    startBuild('clusterlab', 'stage', 'sb-base')
    echo "This is the build number: ${env.BUILD_NUMBER}"
  }
}
