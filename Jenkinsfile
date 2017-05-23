@Library('github.com/victornc83/jenkins-library@master') _

mavenTemplate('stage'){
    def sonarUrl = env.SONAR_URL
    def version = env.CHANGE_ID
    def repourl = ""
    def appName = 'myapp'
    def project = 'stage'

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
      startBuild(project,appName)
      echo "This is the build number: ${env.BUILD_NUMBER}"
      waitDeployIsComplete(project, appName)
    }

    stage('Integration Tests'){
      echo "Running integration tests"
      integrationTests(project,appName)
    }

    stage('Exposing service in Dev'){
      echo "Creating route in Dev"
      exposeSvc{
        name = appName
        project = project
        service = appName
      }
    }

    stage('Promoting image to Stage'){
      echo "Promoting project to Stage environment"
      def nameVer = sh(script: "echo ${appName} | tr -d '.-'",returnStdout: true)
      tagImage(project,appName,'latest',version)
      newAppFromTemplate{
        name = nameVer
        template = 'uoc-sis-backend-promotion'
        project = 'prod'
        parameters = ['APPLICATION_NAME','VERSION','GIT_URI','GIT_REF']
        values = [nameVer,version,"${repourl}",'prod']
      }
    }

}
