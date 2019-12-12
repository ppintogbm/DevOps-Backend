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
		string(defaultValue: "api-calculadora", description: "App Name", name: "app")
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
					sh "bash -c 'mqsipackagebar -a compiled.bar -w . -k ApiCalculadora"
				}
			}
   	}
		stage('Docker build'){
			parallel{
				stage('Build ACE'){
					steps{
						container('docker'){
							sh "docker build -t ${registry}/${project}/${app}-ace:${tag} ."
							sh 'docker login -u $(whoami) -p $(cat /var/run/secrets/kubernetes.io/serviceaccount/token) ' + registry + '/' + project
							sh "docker push ${registry}/${project}/${app}-ace:${tag}"
						}
					}
				}
				stage('Build DB'){
					steps{
						container('docker'){
							sh "docker build -t ${registry}/${project}/${app}-db:${tag} database"
							sh 'docker login -u $(whoami) -p $(cat /var/run/secrets/kubernetes.io/serviceaccount/token) ' + registry + '/' + project
							sh "docker push ${registry}/${project}/${app}-db:${tag}"
						}	
					}
				}
			}
		}
		stage('Deploy/Update'){
			steps{
				container('origin'){
					script{
						openshift.withCluster(){
								openshift.withProject(){
									def deployment = openshift.selector('dc',[template: 'api-calculadora', app: app])
									if(!deployment.exists()){             
										def model = openshift.process("-f", "oc/newtemplate.yaml", "-p", "APPLICATION_NAME=${app}", "-p", "ACE_IMAGE_NAME=${app}-ace:latest", "DB_IMAGE_NAME=${app}-db:latest", "-p", "IMAGE_NAMESPACE=${project}", "-p", "DB_PVC_SIZE=5", "-p", "DB_NAME=TEST")
										openshift.apply(model)
										deployment = openshift.selector('dc',[template: 'api-calculadora', app: app])
									}
									openshift.tag("${app}-ace:${tag}","${app}-ace:latest")
									openshift.tag("${app}-db:${tag}","${app}-db:latest")
									/*
									def latestVersion = deployment.object().status.latestVersion
									def rc = openshift.selector('rc',"${app}-${latestVersion}")
									timeout(time:1, unit: 'MINUTES'){
										rc.untilEach(1){
											def rcMap = it.object()
											return (rcMap.status.replicas.equals(rcMap.status.readyReplicas))
										}
									}
									*/
								}
						}
					}
				}
			}
		}
	}
}
