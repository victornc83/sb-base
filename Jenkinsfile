@Library('github.com/victornc83/jenkins-library@master') _

mavenTemplate('stage'){
    def sonarUrl = env.SONAR_URL

    stage('Git Checkout'){
      echo "Checking out git repository"
      checkout scm
    }

    stage('Build'){
      echo "Building project"
      def version = getVersion()
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
      tagImage(project,image,'latest',version)
      newAppFromTemplate{
        template = 'java-promotion-template'
        project = 'prod'
        parameters = ['APPLICATION_NAME','VERSION','SOURCE_NAMESPACE']
        values = ['myapp',version,'stage']
      }
    }

}
