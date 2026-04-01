pipeline {
    agent any

    options {
        timestamps()
        skipDefaultCheckout(true)
    }

    environment {
        SONAR_PROJECT_KEY = 'portfolio-aqa-demo'
        SONAR_HOST_URL = 'http://sonarqube:9000'
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests & Coverage') {
            steps {
                ansiColor('xterm') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean test jacocoTestReport'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh '''
                    ./gradlew sonar \
                      -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                      -Dsonar.host.url=$SONAR_HOST_URL \
                      -Dsonar.token=$SONAR_TOKEN
                '''
            }
        }

    }

    post {
        always {
            junit testResults: 'build/test-results/test/*.xml', allowEmptyResults: true

            script {
                if (fileExists('build/reports/jacoco/test/html/index.html')) {
                    publishHTML(target: [
                        reportDir: 'build/reports/jacoco/test/html',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                } else {
                    echo 'JaCoCo HTML report not found'
                }
            }
        }
    }
}