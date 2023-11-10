pipeline {
    agent any
    stages {
        stage('Clone git') {
            steps {
                checkout scm
            }
        }
        stage('Run Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

    }
    post {
        always {
            cucumber    fileIncludePattern: '*.json',
                            jsonReportDirectory: 'target/cucumber-result/json',
                            reportTitle: 'Test Report'
        }
    }
}