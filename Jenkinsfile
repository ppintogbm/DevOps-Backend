pipeline{
	agent {
        	kubernetes{
            		cloud "openshift"
            		label "test-ace"
            		yamlFile "BuildPod.yaml"
		}
    	}
	stages{
		stage('Prepare'){
			steps{
				script{
					imagetag = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
					/*openshift.withCluster(){
						registry = "http://" + openshift.raw("registry info").actions[0].out
					}*/
					registry = "172.30.1.1:5000"
					project = "jenkins"
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
					sh "docker build -t ${registry}/${project}/api-calculadora:${imagetag} ."
					sh 'docker login -u $(whoami) -p $(cat /var/run/secrets/kubernetes.io/serviceaccount/token) ' + registry + '/' + project
					sh "docker push ${registry}/${project}/api-calculadora:${imagetag}"
					/*script{
						docker.withRegistry(registry){
							docker.build("${project}/api-calculadora:${imagetag}").push()
						}
					}*/
				}
			}
		}
	}
}
