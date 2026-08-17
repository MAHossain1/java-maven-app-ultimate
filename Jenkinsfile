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

        stage("Provisioning the EC2 instance") {
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
                TF_VAR_env_prefix = 'test'
            }
            steps {
                script {
                    echo "Provisioning the EC2 instance using Terraform"
                    dir('terraform') {
                        sh 'terraform init'
                        sh 'terraform apply -auto-approve'
                        EC2_PUBLIC_IP = sh(
                            script: "terraform output ec2_public_ip",
                            returnStdout: true
                        ).trim()
                    }
                }
            }
        }

        stage('Deploy the application') {
            environment {
                DOCKER_CREDS = credentials('docker-hub-repo') 
                #for private docker images, you can use the above credentials to login to docker hub and pull the image
            }
            steps {
                script {
                    echo "waiting for EC2 server to initialize"
                    sleep(time: 90, unit: "SECONDS")

                    echo "Deploying docker image on EC2 instance using docker-compose"
                    echo "${EC2_PUBLIC_IP}"

                    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                    def ec2Instance = "ubuntu@${EC2_PUBLIC_IP}"

                    sshagent(['ec2-server-key']) {
                        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ubuntu/server-cmds.sh"
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ubuntu/docker-compose.yaml"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} '${shellCmd}'"
                    }
                }

            }
        }

        stage('Commit version update') {
            steps {
                script {
                    sshagent(['github-ssh-key']) {

                        sh '''
                            git config user.email "jenkins@example.com"
                            git config user.name "Jenkins"

                            git remote set-url origin git@github.com:MAHossain1/java-maven-app-ultimate.git

                            mkdir -p ~/.ssh
                            ssh-keyscan github.com >> ~/.ssh/known_hosts

                            git status

                            if ! git diff --quiet pom.xml; then
                                git add pom.xml
                                git commit -m "Increment application version"
                                git push origin HEAD:main
                            else
                                echo "No version changes to commit"
                            fi
                        '''
                    }
                }
            }
        }

    }

}