#!/bin/bash
if [ $# -lt 2 ]; then
	echo "Usage: ./moveToRelease.bash <version number> <ftp password>"
	exit 1
fi
release="featurecomplete"
versionNum=$1
workingDir="src/main/java/CloudComposerGroup/CloudComposer"
currentDir=$PWD
toDir="test/${release}_${versionNum}"
`mkdir -p $toDir`
`mkdir "${toDir}/include"`
`mkdir "${toDir}/images"`

`javac "${workingDir}/"*.java`
`mv $workingDir/*.class .`
`jar -cvf MidiPlayer.jar *.class | echo`
`rm *.class`
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
password=$2
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
lcd images
mput *
lcd ../include
mput *
bye

END_SCRIPT
