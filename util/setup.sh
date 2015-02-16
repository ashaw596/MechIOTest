#!/bin/bash -e

# Become root
if [ $UID -ne 0 ]; then
	echo "-- Becoming root"
	exec sudo $0
fi

yum -y install $(cat packages.txt)
