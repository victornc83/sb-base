@Library('github.com/victornc83/jenkins-library@master') _

mavenTemplate('stage'){
  node('maven'){
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
      unitTests()
    }

    stage('Analysis'){
      echo "Analysis in Sonar ${sonarUrl}"
      sh "${mvnCmd} sonar:sonar -Dsonar.host.url=${sonarUrl}"
    }

    stage('Deploy in Dev'){
      echo 'Building docker image and deploying to Dev'
      startBuild('myapp')
      echo "This is the build number: ${env.BUILD_NUMBER}"
    }
  }
}
