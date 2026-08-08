def gv

pipeline {
    agent any

    tools {
        maven 'maven_3.9'
    }

    parameters {
        // string(name: 'VERSION', defaultValue: '', description: 'Version of the application')
        choice(name: 'VERSION', choices: ['1.1.0', '1.1.1', '2.1.0'], description: 'Environment to deploy the application')
        booleanParam(name: 'executeTests', defaultValue: true, description: 'Run tests?')
    }

    stages {

        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }

        stage('Test the application') {
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                script {
                    gv.testApp()
                }
            }
        }
       
        stage('Build jar') {
            steps {
                script {
                    // gv.buildJar()
                    echo "building the application"
                    // sh 'mvn package'
                }
            }
        }

        stage('Build docker image') {
            steps {
                script {
                    echo "building the docker image ${params.VERSION}"
                }
            }
        }

    }

}