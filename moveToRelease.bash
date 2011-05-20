#!/bin/bash

# moveToRelease.bash
# This script will push the latest files to the FTP server specified within.

# Test if the program is being used with an appropriate detail set.
if [[ ($# -lt 2) || (($# -gt 2) && ($3 != "--release")) ]]; then
	echo "Usage: ./moveToRelease.bash <version number> <ftp password> [--release]"
	exit 1
fi

# Modify these variables for your personal FTP server.
username="cloudcomposer@publicstaticdroid.com"
server="ftp.publicstaticdroid.com"
port="21"

# Change release type if --release is provided.
releaseType="test"
if [[ ($# -gt 2) ]]; then
	releaseType="release"
fi

# Indicates the release milestone and the version number.
release="featurecomplete"
versionNum=$1

# Indicates what directory we are storing local release info in.
toDir="${releaseType}/${release}_${versionNum}"

# Makes the directories we need to place the files.
`mkdir -p $toDir`
`mkdir "${toDir}/include"`
`mkdir "${toDir}/images"`

# Build Java files into a .jar file and place in the release directory.
workingDir="src/main/java/CloudComposerGroup/CloudComposer"
tempDir="CloudComposerGroup/CloudComposer"
`javac "${workingDir}/"*.java`
`mkdir -p $tempDir`
`mv $workingDir/*.class $tempDir/.`
`jar -cvf MidiPlayer.jar $tempDir/*.class | echo`
password=$2
`jarsigner -storepass $password -keypass $password MidiPlayer.jar cloudcomposer | echo`
`rm -r $tempDir`
`mv MidiPlayer.jar $toDir`

# Change working directory and copy over the web application files.
workingDir="src/main/webapp"
`cp "${workingDir}/"*.html "${toDir}/"`
`cp "${workingDir}/"*.php "${toDir}/"`
`cp "${workingDir}/"*.js "${toDir}/include/"`
`cp "${workingDir}/"*.css "${toDir}/include/"`

# Change the working directory to our resources and copy over the images we use.
workingDir="src/main/resources"
`cp "${workingDir}/"* "${toDir}/images/"`

# Begin an FTP session and proceed to copy all the files over.
# Will make directories if they do not yet exist.
ftp -ni $server <<END_SCRIPT
quote USER $username
quote PASS $password
binary

mkdir test
cd $releaseType
mkdir include
mkdir images
mkdir lib
mkdir resources
mkdir songs

lcd $toDir
cd $releaseType
mput *
mput images/*
mput include/*
bye

END_SCRIPT
