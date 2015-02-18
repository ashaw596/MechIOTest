#!/bin/bash -e

# Become root
if [ $UID -ne 0 ]; then
	echo "-- Becoming root"
	exec sudo $0
fi

wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo

yum -y install $(cat packages.txt)

