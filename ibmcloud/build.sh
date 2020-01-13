image_name=${1:-calculadora-test}
image_tag=${2:-latest}
#Create container
container=$(buildah from ibmcom/ace)

#Copy Bar file
buildah copy --chown 1000:1001 $container compiled.bar /home/aceuser/bars/

#Copy Configs
buildah copy --chown 1000:1001 $container lib/db2jcc-db2jcc4.jar /home/aceuser/
buildah copy --chown 1000:1001 $container initial-config /home/aceuser/initial-config

buildah config --user 1000 --shell "/bin/bash -c" $container
#Deploy Bars
buildah run $container -- /bin/bash -c "ace_compile_bars.sh"

buildah commit $container "$image_name:$image_tag"

buildah delete $container