def testApp() {
    echo "testing the application..."
}

def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'my-dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t nanajanashia/demo-app:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push nanajanashia/demo-app:jma-2.0'
    }
}      

def deployApp() {
       echo "building the docker image ${params.VERSION}"
       withCredentials([usernamePassword(credentialsId: 'my-dockerhub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh 'docker build -t  arman04/java-maven-app:jma-3.0 .'
            sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
            sh 'docker push arman04/java-maven-app:jma-3.0'
    }
} 
  
return this