@Library('github.com/victornc83/jenkins-library@master') _

mavenTemplate('prod'){
    def sonarUrl = env.SONAR_URL
    def version = '0.1-SNAPSHOT'

    stage('Deploy in Staging'){
      echo "Tagging image"
      promoteImage('stage', 'prod', 'myapp', version)
      waitDeployIsComplete('prod', 'myapp')
    }

    stage('Regression Tests'){
      echo "Running sanity checks"
    }

    stage('Exposing app') {
      echo "Exposing app in Stage"
      exposeSvc{
          name = 'myapp'
          project = 'prod'
          service = 'myapp'
      }
    }

    stage('Performance Tests'){
      echo "Perf tests"
    }

}
