#Create container
container=$(buildah from ibmcom/ace)

#Copy Bar file
buildah copy --chown aceuser:mqbrkrs $container ../compiled.bar /home/aceuser/bars

#Copy Configs
buildah copy --chown aceuser:mqbrkrs $container ../lib/db2jcc-db2jcc4.jar /home/aceuser/
buildah copy --chown aceuser:mqbrkrs $container ../initial-config /home/aceuser/initial-config

#Deploy Bars
buildah run --user aceuser:mqbrkrs $container -- /bin/bash -c ace_compile_bars.sh