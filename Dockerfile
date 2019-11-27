FROM ibmcom/ace

COPY compiled.bar bars/

RUN  ls -la bars/ && \
     ace_compile_bars.sh
