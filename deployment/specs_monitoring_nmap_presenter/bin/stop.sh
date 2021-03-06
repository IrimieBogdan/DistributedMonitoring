#!/bin/bash

# Load config file if it exists.
if [ -f /etc/sysconfig/presenter_config ];
then
  echo "Load config."
  source /etc/sysconfig/presenter_config
fi

# Load config file if it exists on Ubuntu.
if [ -f /etc/default/presenter_config ];
then
  echo "Load config."
  source /etc/default/presenter_config
fi

if [ ! -z "$PRESENTER_HOME" ];
then
  # PRESENTER_HOME is set, us it.
  component_home=$PRESENTER_HOME
else
  # PRESENTER_HOME is not set.
  component_home=$(pwd)/..
fi

PID_FILE="$component_home/var/specs_monitoring_nmap_presenter.pid"

# Check if PID file exists.
if [ ! -f $PID_FILE ];
then
    echo "No PID file found!"
    exit -1
fi

# Read PID from file.
PID=$(cat "$PID_FILE")

# Check if file content is a number.
numberRegex='^[0-9]+$'
if ! [[ $PID =~ $numberRegex ]];
then
    echo "The content of the file is not a valid PID!"
    echo "The file will be removed!"
    rm $PID_FILE
    exit -2
fi

# Kill running process.
if kill $PID;
then
    echo "Process with PID $PID was killed!"
else
    echo "There is no process with $PID running!"
fi

# Remove PID file
echo "Removing PID file!"
rm $PID_FILE
