#Create container
container=$(buildah from ibmcom/ace)

#Copy Bar file
buildah copy --chown 1000:1001 $container compiled.bar /home/aceuser/bars

#Copy Configs
buildah copy --chown 1000:1001 $container lib/db2jcc-db2jcc4.jar /home/aceuser/
buildah copy --chown 1000:1001 $container initial-config /home/aceuser/initial-config

#Deploy Bars
buildah run --user 1000:1001 $container -- /bin/bash -c ace_compile_bars.sh