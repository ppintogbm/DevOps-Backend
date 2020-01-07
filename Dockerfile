FROM 	ibmcom/ace

#Move bar file compiled via Jenkinsfile
COPY 	compiled.bar bars/
COPY  lib/db2jcc-db2jcc4.jar .
COPY 	initial-config	initial-config

#Change permissions from copied bar
USER	root
RUN	chown -R 1000:mqbrkrs bars/; chown 1000:mqbrkrs db2jcc-db2jcc4.jar; chown 1000:mqbrkrs -R initial-config

#Deploy compiled bar 
USER	1000
RUN  	ls -la bars/ && \
    	ace_compile_bars.sh