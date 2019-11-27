pipeline{
	agent {
        	kubernetes{
            		cloud "openshift"
            		label "test-ace"
            		yamlFile "BuildPod.yaml"
		}
    	}
	stages{
		stage('Extract'){
			steps{
			//		git branch: 'Dev', url: 'https://github.com/lgaritav/DevOps-Backend.git'
					sh "ls -a"
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
					sh "docker build ."
				}
			}
		}
	}
}
