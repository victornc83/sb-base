@Library('github.com/victornc83/jenkins-library@master') _

mavenTemplate('stage'){
    def sonarUrl = env.SONAR_URL
    def version = env.CHANGE_ID
    def repourl = ""

    stage('Git Checkout'){
      echo "Checking out git repository"
      checkout scm
      version = getVersion()
      repourl = sh(script: "git config --get remote.origin.url", returnStdout: true).trim()
    }

    stage('Build'){
      echo "Building project"
      sh "mvn clean package -DskipTests=true"
    }

    stage('Unit Tests') {
      echo "Running Unit Tests"
      unitTests()
    }

    stage('Analysis'){
      echo "Analysis in Sonar ${sonarUrl}"
      sh "mvn sonar:sonar -Dsonar.host.url=${sonarUrl}"
    }

    stage('Deploy in Dev'){
      echo 'Building docker image and deploying to Dev'
      startBuild('stage','myapp')
      echo "This is the build number: ${env.BUILD_NUMBER}"
    }

    stage('Integration Tests'){
      echo "Running integration tests"
      integrationTests('stage','myapp')
    }

    stage('Promoting image to Stage'){
      echo "Promoting project to Stage environment"
      tagImage('stage','myapp','latest',version)
      newAppFromTemplate{
        name = 'myapp'
        template = 'java-promotion-template'
        project = 'prod'
        parameters = ['APPLICATION_NAME','VERSION','GIT_URI','GIT_REF']
        values = ['myapp',version,"${repourl}",'prod']
      }
    }

}
