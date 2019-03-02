FROM postgres

COPY ./submodules/database /db
WORKDIR /db

RUN ["sh", "-c", "./db.sh"]