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

        stage('Prepare Allure Report') {
            steps {
                script {
                    if (fileExists('build/allure-results')) {
                        echo 'Allure results directory found'
                        sh 'echo "Allure files:"'
                        sh 'find build/allure-results -maxdepth 2 -type f || true'
                    } else {
                        echo 'Allure results directory not found: build/allure-results'
                    }
                }
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

            script {
                def allureResultsExist = fileExists('build/allure-results')
                def allureFilesCount = '0'

                if (allureResultsExist) {
                    allureFilesCount = sh(
                        script: 'find build/allure-results -type f | wc -l',
                        returnStdout: true
                    ).trim()
                }

                if (allureResultsExist && allureFilesCount != '0') {
                    echo "Publishing Allure report from build/allure-results (${allureFilesCount} files)"
                } else {
                    echo 'Allure results directory is missing or empty'
                }
            }

            allure(
                commandline: 'Allure',  // Manage Jenkins -> Tools -> Allure Commandline installations (naming should match)
                includeProperties: false,
                jdk: '',
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'build/allure-results']]
            )
        }
    }
}