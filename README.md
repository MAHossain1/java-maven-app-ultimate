# Java Maven Application - Complete CI/CD Pipeline on AWS

A complete end-to-end CI/CD project that builds, versions, containerizes, provisions infrastructure, and deploys a Java application on AWS using modern DevOps tools.

## Project Overview

This project demonstrates a full automated pipeline that:

1. Builds a Java Maven application
2. Automatically increments the application version
3. Builds and pushes a Docker image
4. Provisions an EC2 instance using Terraform
5. Deploys the application using Ansible
6. Verifies the deployment
7. Optionally destroys the infrastructure

## Architecture

GitHub → Jenkins → Maven Build → Docker Build → Docker Hub
↓
Terraform (EC2)
↓
Ansible Deploy
↓
Application Running on AWS
text## Technologies Used

| Category               | Tools                      |
| ---------------------- | -------------------------- |
| Language               | Java, Maven                |
| CI/CD                  | Jenkins (Pipeline as Code) |
| Containerization       | Docker, Docker Compose     |
| Infrastructure as Code | Terraform                  |
| Configuration Mgmt     | Ansible                    |
| Cloud                  | AWS (EC2, Security Groups) |
| Version Control        | Git, GitHub                |
| Registry               | Docker Hub                 |

## Features

- Fully automated Jenkins Declarative Pipeline
- Automatic version increment using Maven Versions Plugin
- Docker image build and push to Docker Hub
- Infrastructure provisioning with Terraform
- Configuration and deployment with Ansible
- Dynamic inventory generation from Terraform output
- Health check / verification stage
- Manual approval before destroying infrastructure
- Secure credential management using Jenkins Credentials

## Project Structure

java-maven-app-ultimate/
├── Jenkinsfile
├── pom.xml
├── src/
├── terraform/
│ ├── main.tf
│ ├── variables.tf
│ ├── outputs.tf
│ └── ...
├── ansible/
│ ├── deploy-docker-on-ubuntu.yaml
│ ├── docker-compose-jma.yaml
│ ├── ansible.cfg
│ └── inventory.ini (generated)
├── script.groovy
└── README.md
text## Pipeline Stages

1. **Init** – Load shared Groovy scripts
2. **Test** – Run unit tests (optional via parameter)
3. **Increment Version** – Automatically bump application version
4. **Build Application** – Build JAR using Maven
5. **Build Docker Image** – Build and push image to Docker Hub
6. **Provision Infrastructure** – Create EC2 instance using Terraform
7. **Deploy with Ansible** – Install Docker and deploy the application
8. **Verify Deployment** – Health check using curl
9. **Commit Version Update** – Push updated `pom.xml` back to GitHub
10. **Destroy Infrastructure** (Optional) – Manual approval + Terraform destroy

## Prerequisites

- Jenkins with necessary plugins (Git, Pipeline, Credentials, SSH Agent, etc.)
- Docker installed and accessible from Jenkins
- Terraform installed
- Ansible installed
- AWS account with appropriate IAM permissions
- Docker Hub account
- SSH key pair for EC2 access

## How to Run

1. Push the code to GitHub
2. Create a Multibranch Pipeline or Pipeline job in Jenkins pointing to this repository
3. Configure the following Jenkins Credentials:
   - AWS Access Key & Secret Key
   - Docker Hub credentials
   - GitHub SSH key
   - EC2 SSH private key
4. Run the pipeline
5. After successful deployment, access the application at:
   http://<EC2_PUBLIC_IP>:8080
   text## Key Learnings & Skills Demonstrated

- Writing production-style Jenkins Declarative Pipelines
- Managing secrets securely with Jenkins Credentials
- Infrastructure as Code using Terraform
- Configuration Management using Ansible
- Docker multi-stage concepts and Docker Compose
- Dynamic inventory with Ansible
- Automated versioning strategy
- End-to-end deployment automation on AWS
- Error handling and verification in pipelines

## Future Improvements

- Add Prometheus + Grafana monitoring
- Implement Blue-Green or Canary deployment
- Use AWS EKS instead of EC2
- Add SonarQube for code quality
- Integrate Trivy for container scanning
- Use Ansible Vault for secrets
- Convert to GitOps using ArgoCD

## Author

**Arman Hossain**  
DevOps Enthusiast | AWS | Jenkins | Docker | Terraform | Ansible

---

Feel free to fork this repository and use it for learning purposes.
