pipeline{
	agent {
        	kubernetes{
            		cloud "openshift"
            		label "test-ace"
            		yamlFile "BuildPod.yaml"
		}
    }
	parameters{
		string(defaultValue: "jenkins", description: "Project/Namespace name", name: "project")
		string(defaultValue: "172.30.1.1:5000", description: "Registry",  name:"registry")
		string(defaultValue: "api-calculadora", description: "Image Name", name: "image")
	}
	stages{
		stage('Prepare'){
			steps{
				script{
					tag = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
				}
			}
		}
		stage('Build Bars'){
			steps{
				container('ace'){
					sh "bash -c 'mqsipackagebar -a compiled.bar -w . -k ApiCalculadora'"
				}
			}
   		}
		stage('Docker build'){
			steps{
				container('docker'){
					sh "docker build -t ${registry}/${project}/${image}:${tag} ."
					sh 'docker login -u $(whoami) -p $(cat /var/run/secrets/kubernetes.io/serviceaccount/token) ' + registry + '/' + project
					sh "docker push ${registry}/${project}/${image}:${tag}"
					sh "docker tag ${registry}/${project}/${image}:${tag} ${registry}/${project}/${image}:latest"
					sh "docker push ${registry}/${project}/${image}:latest"
				}
			}
		}
	}
}
