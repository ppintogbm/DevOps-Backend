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
		string(defaultValue: "db-calculadora", description: "Database Image Name", name: "dbimage")
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
				}
			}
		}
		stage('Docker build database'){
			steps{
				container('docker'){
					sh "cd database; docker build -t ${registry}/${project}/${dbimage}:${tag} "
					sh 'docker login -u $(whoami) -p $(cat /var/run/secrets/kubernetes.io/serviceaccount/token) ' + registry + '/' + project
					sh "docker push ${registry}/${project}/${dbimage}:${tag}"
				}
			}
		}
		/*
		stage('Deploy/Update'){
			steps{
				container('origin'){
					script{
						openshift.withCluster(){
							openshift.withProject(){
								def deployment = openshift.selector('dc',[template: 'ace', app: image])
								if(!deployment.exists()){             
              						def model = openshift.process("-f", "oc/template.yaml", "-p", "APPLICATION_NAME=${image}", "-p", "IMAGE_NAME=${image}:latest")
              						openshift.create(model)
              						deployment = openshift.selector('dc',[template: 'ace', app: image])
              					}
								openshift.tag("${image}:${tag}","${image}:latest")
              					//deployment.rollout().latest()
              					def latestVersion = deployment.object().status.latestVersion
								def rc = openshift.selector('rc',"${image}-${latestVersion}")
								timeout(time:1, unit: 'MINUTES'){
									rc.untilEach(1){
										def rcMap = it.object()
										return (rcMap.status.replicas.equals(rcMap.status.readyReplicas))
                					}
              					}
							}
						}
					}
				}
			}
		}*/
	}
}
