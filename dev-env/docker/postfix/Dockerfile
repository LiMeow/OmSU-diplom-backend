FROM ubuntu:xenial

RUN \
  apt-get update && apt-get install -y postfix && \
  postconf -e "myhostname = scheduler.omsu.ru"

CMD service postfix start
