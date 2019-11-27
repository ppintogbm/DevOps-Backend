FROM ibmcom/ace

COPY compiled.bar bars/

RUN  ace_compile_bars.sh
