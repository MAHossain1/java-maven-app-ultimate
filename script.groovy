def testApp() {
    echo "testing the application..."
}

def buildJar() {
    echo "building the application..."
    sh 'mvn clean package -U'
} 

def buildImage() {
    echo 'building the docker image..'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "docker build -t arman04/java-maven-app:${IMAGE_NAME} ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push arman04/java-maven-app:${IMAGE_NAME}"
    }
    echo 'docker image built and pushed to docker hub'
}      

return this