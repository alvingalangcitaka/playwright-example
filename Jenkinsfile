pipeline {
    agent any
    stages {
        stage('Clone git') {
            steps {
                git url: 'https://github.com/alvingalangcitaka/playwright-example.git',
                    branch: 'main'
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