# DevOps-Backend
Repositorio de calculara para piloto 3

## Descripcion
Repositorio contenedor de componente de backend del piloto numero 3, destinado a ser instalado en minishift y utilizar Jenkins para automatización y App Connect Enterprise como runtime del backend para el piloto.

## Instrucciones
1. Por medio del CLI de openshift, ingresar con usuario con privilegios administrativos
 ```bash
 oc login -u system:admin
 ``` 
2. Creamos un proyecto donde desplegar, de no tener alguno
 ```bash
 oc new-project [nombre_del_proyecto]
 ``` 
3. Brindamos privilegios de acceso al usuario developer, al proyecto
```bash
oc adm policy add-role-to-user admin developer
```
4. Brindamos el contexto de seguridad `privileged` a la cuenta de servicio `jenkins`
```bash
oc adm policy add-scc-to-user privileged -z jenkins
```
5. Creamos el contexto de seguridad `ibm-anyuid-scc`
```bash
oc create -f https://github.com/IBM/cloud-pak/raw/master/spec/security/scc/ibm-anyuid-scc.yaml
```
6. Brindamos el contexto de seguridad `ibm-anyuid-scc` a la cuenta de servicio `default` (predeterminada) del proyecto
```bash
oc adm policy add-scc-to-user privileged -z jenkins
```
7. Configuramos el pipeline de despliegue del presente repositorio:
```yaml
apiVersion: build.openshift.io/v1
kind: BuildConfig
metadata:
  name: test-ace-git
spec:
  runPolicy: Serial
  source:
    git:
      ref: [branch]
      uri: '[git_dir]'
    type: Git
  strategy:
    jenkinsPipelineStrategy:
      jenkinsfilePath: Jenkinsfile
    type: JenkinsPipeline