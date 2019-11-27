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
					openshift.withCluster(){
						registry = "http://" + openshift.raw("registry info").actions[0].out
					}
					project = env.PROJECT_NAME
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
					sh "docker login -u jenkins -p $(cat /var/run/secrets/kubernetes.io/serviceaccount/token) ${registry}/${project}" 
					script{
						docker.withRegistry(registry){
							docker.build("${project}/api-calculadora:${imagetag}").push()
						}
					}
				}
			}
		}
	}
}
