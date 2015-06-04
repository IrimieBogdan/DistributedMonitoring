#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/conf.properties
echo '#RabbitMQ connection details'
$env | grep rabbitHost
$env | grep prRabbitSendQueue | sed 's/prRabbitSendQueue/rabbitSendQueue/g'
$env | grep prRabbitReceiveQueue | sed 's/prRabbitReceiveQueue/rabbitReceiveQueue/g'
$env | grep rabbitUser
$env | grep rabbitPassword
echo 
echo '#MongoDB connection details'
$env | grep mongoHost
$env | grep mongoPort

exec > ../var/specs_monitoring_nmap_presenter.pid
java -jar ../lib/Presenter.jar & echo $!