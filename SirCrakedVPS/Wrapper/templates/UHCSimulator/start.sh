#!/bin/sh
rm ./lobby/playerdata/*
rm ./lobby/stats/*
while true
do
java -jar -Xmx1024M spigot.jar
rm ./lobby/playerdata/*
rm ./lobby/stats/*
sleep 3
done