@Library('github.com/victornc83/jenkins-library@master') _

mavenTemplate('prod'){
    def sonarUrl = env.SONAR_URL
    def version = env.CHANGE_ID

    stage('Deploy in Staging'){
      echo "Tagging image"
      promoteImage('stage', 'prod', 'myapp', '0.1-SNAPSHOT')
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
