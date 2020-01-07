FROM 	ibmcom/ace

#Move bar file compiled via Jenkinsfile
COPY 	compiled.bar bars/
COPY  lib/db2jcc-db2jcc4.jar .
COPY 	initial-config	initial-config

#Change permissions from copied bar
USER	root
RUN	chown -R aceuser:mqbrkrs bars/; chown aceuser:mqbrkrs db2jcc-db2jcc4.jar; chown aceuser:mqbrkrs -R initial-config

#Deploy compiled bar 
USER	aceuser
RUN  	ls -la bars/ && \
    	ace_compile_bars.sh