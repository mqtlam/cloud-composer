#!/bin/bash
if [ $# -lt 2 ]; then
	echo "Usage: ./moveToRelease.bash <version number> <ftp password>"
	exit 1
fi
release="featurecomplete"
versionNum=$1
currentDir=$PWD
toDir="test/${release}_${versionNum}"
`mkdir -p $toDir`
`mkdir "${toDir}/include"`
`mkdir "${toDir}/images"`

workingDir="src/main/java/CloudComposerGroup/CloudComposer"
tempDir="CloudComposerGroup/CloudComposer"
`javac "${workingDir}/"*.java`
`mkdir -p $tempDir`
`mv $workingDir/*.class $tempDir/.`
`jar -cvf MidiPlayer.jar $tempDir/*.class | echo`
password=$2
`jarsigner -storepass $password -keypass $password MidiPlayer.jar cloudcomposer`
`rm -r CloudComposerGroup`
`mv MidiPlayer.jar $toDir`

workingDir="src/main/webapp"
`cp "${workingDir}/"*.html "${toDir}/"`
`cp "${workingDir}/"*.php "${toDir}/"`
`cp "${workingDir}/"*.js "${toDir}/include/"`
`cp "${workingDir}/"*.css "${toDir}/include/"`

workingDir="src/main/resources"
`cp "${workingDir}/"* "${toDir}/images/"`

username="cloudcomposer@publicstaticdroid.com"
server="ftp.publicstaticdroid.com"
port="21"
#`scp -p $port "${toDir}/*" "${username}:cloudcomposer/"`

#files=$toDir/*

ftp -ni $server <<END_SCRIPT
quote USER $username
quote PASS $password
binary

mkdir test
cd test
mkdir include
mkdir images
mkdir lib
mkdir resources
mkdir songs

lcd $toDir
cd test
mput *
mput images/*
mput include/*
bye

END_SCRIPT
