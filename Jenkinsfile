def gv

pipeline {
    agent any

    tools {
        maven 'maven_3.9'
    }

    parameters {
        // string(name: 'VERSION', defaultValue: '', description: 'Version of the application')
        choice(name: 'VERSION', choices: ['1.1.0', '2.1.0', '2.2.0'], description: 'Please select the version of the application')
        booleanParam(name: 'ExecuteTests', defaultValue: true, description: 'Please select the flag')
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
                    params.ExecuteTests
                }
            }
            steps {
                script {
                    gv.testApp()
                }
            }
        }

        stage('Increment version') {
            steps {
                script {
                    echo "Incrementing the version of the application"
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.*)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
       
        stage('Build the Application') {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage('Build docker image') {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }

    }

}