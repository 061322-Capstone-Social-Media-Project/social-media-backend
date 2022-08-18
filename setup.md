# Setup Guide

## EC2 SETUP

### Jenkins

Below is the steps you will need to get Jenkins installed in your ec2 instance.
You may also follow this [guide](https://www.jenkins.io/doc/tutorials/tutorial-for-installing-jenkins-on-AWS/) for more
details.

1. Update packages.
    ```console
    sudo yum update -y
    ```

2. Add jenkins repo.
    ```console
    sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
    ```

3. Import key to enable installation.
    ```console
    sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
    ```

    ```console
    sudo yum upgrade
    ```

4. Install Java 11.
    ```console
    sudo amazon-linux-extras install java-openjdk11 -y
    ```

5. Install Jenkins.
    ```console
    sudo yum install jenkins -y
    ```

6. Enable the Jenkins service to start at boot.
    ```console
    sudo systemctl enable jenkins
    ```

7. Start Jenkins as a service.
    ```console
    sudo systemctl start jenkins
    ```

### Docker

Below is the steps you will need to get Docker installed in your ec2 instance.
You may also follow
this [guide](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/create-container-image.html) for more
details.

1. Update packages
    ```console
    sudo yum update -y
    ```

2. Install the most recent Docker Engine package.
    ```console
    sudo amazon-linux-extras install docker
    ```

3. Start the Docker service.
    ```console
    sudo service docker start
    ```

4. Enable the Docker service to start at boot
    ```console
    sudo systemctl enable docker
    ```

### Finishing steps

1. Add the ec2-user to the docker group.
    ```console
    sudo usermod -a -G docker ec2-user
    ```

2. Add jenkins user to docker group.
    ```console
    sudo usermod -a -G docker jenkins
    ```

3. Restart Jenkins service.
    ```console
    sudo service jenkins restart
    ```

4. Reload system daemon.
    ```console
    sudo systemctl enable docker
    ```

5. Reload system daemon.
    ```console
    sudo service docker restart
    ```

6. Check status of Jenkins and Docker (optional).
    ```console
    sudo systemctl status jenkins
    ```

    ```console
    docker info
    ```

7. Install git.
    ```console
    yum install git
    ```

## Jenkins Setup

You can follow [this](https://github.com/061322-VA-JavaMSA/notes/blob/main/week4/jenkins-ec2.txt) guide to finish
setting up jenkins.
Everything after line 31 of the above text document is relevant. Line 1-4 is also handy.

### Note

- The Dockerfile and Jenkinsfile are already located on the repo of both the backend and frontend.
- The **NodeJS Plugin** is required when setting up the frontend.
    - Under **Manage Jenkins** &rarr; **Global Tool Configuration**
        - Set up the nodejs global tool with ```npm install @angular/cli@~14.0.6 typescript@~4.7.2```
- To simplify the deployment we used two Jenkins jobs. One for the backend and another for the frontend.
- The only difference between both jobs besides the plugin mentioned above is the GitHub repository they are pointing
  too and the webhook.
- The external Jenkins link above also goes over how to set up an AWS EC2 instance.