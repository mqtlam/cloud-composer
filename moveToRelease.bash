#!/bin/bash
if [ $# -lt 2 ]; then
	echo "Usage: ./moveToRelease.bash <version number> <ftp password>"
	exit 1
fi
release="beta"
versionNum=$1
workingDir="src/main/java/CloudComposerGroup/CloudComposer"
toDir="release/${release}_${versionNum}"
`mkdir -p $toDir`
`mkdir "${toDir}/include"`
`mkdir "${toDir}/images"`

`javac "${workingDir}/"*.java`
`jar -cvf $workingDir/MidiPlayer.jar $workingDir/*.class | echo`
`rm "${workingDir}/"*.class`
`mv "${workingDir}/MidiPlayer.jar" $toDir`

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

for ff in $toDir/*; do
file=${ff#*/*/}
ftp -n $server <<END_SCRIPT
quote USER $username
quote PASS $password
binary
put $ff $file
quit
END_SCRIPT
done

for ff in $toDir/include/*; do
file=${ff#*/*/*/}
ftp -n $server <<END_SCRIPT
quote USER $username
quote PASS $password
binary
put $ff $file
quit
END_SCRIPT
done

for ff in $toDir/images/*; do
file=${ff#*/*/*/}
ftp -n $server <<END_SCRIPT
quote USER $username
quote PASS $password
binary
put $ff $file
quit
END_SCRIPT
done
