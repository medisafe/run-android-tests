#!/bin/bash

# Created by sromku with ☕
package=com.sromku.sample.runtests
planFile=$1
outputDir=$2
device=$3

# check for correct input
if [ -z $planFile ] || [ -z $outputDir ] ; then
    echo "Missing params"
    exit 1
fi

# if specific device is not passed, we choose the first one that is attached
if [ -z $device ] ; then

    # pick the first device in the list
    for dl in $(adb devices | grep -v List | grep device)
    do
        if [ $dl == "device" ]; then
            continue
        fi
        device=$dl
        echo "Selected device: $device"
        break
    done

    # if device is empty, say no connected devices
    if [ -z $device ] ; then
        echo "No connected devices"
        exit 1
    fi

fi


# clear notifications
clearNotifications() {
    adb -s $1 shell input keyevent 3
    adb -s $1 shell input swipe 0 0 0 300
    num=$(adb -s "$1" shell dumpsys notification | grep NotificationRecord | wc -l)
    while [ $num -gt 0 ]; do
        adb -s $1 shell input swipe 0 400 300 400
        num=$(( $num - 1 ))
    done
    adb -s $1 shell input keyevent 3
}

# run line by line from plan and execute
for line in `cat "$planFile"`
do

    # skip grouped separator
    if [ $line == "~~~" ]; then
        continue
    fi

    # in case of clear data we execute and move to next line
    if [ $line == "clearData" ]; then
        echo "*** Clear data"
        adb -s $device shell pm clear $package
        sleep 3
        echo ""
        continue
    fi

    # in case of clear notifications we execute and move to next line
    if [ $line == "clearNotifications" ]; then
        echo "*** Clear notifications"
        clearNotifications $device
        sleep 3
        echo ""
        continue
    fi

    # print full test name
    echo "*** Testing: $line"

    # create folder
    testDir="$outputDir/$line"
    mkdir $testDir

    # files we create
    runningTest="$testDir/running-test-$device.txt"
    recording="$testDir/recording.mp4"
    logcat="$testDir/logcat.txt"
    db="$testDir/app.db"
    preferences="$testDir/shared_preferences.xml"
    netstats="$testDir/dumpsys_netstats.txt"
    batterystats="$testDir/dumpsys_batterystats.txt"
    alarms="$testDir/dumpsys_alarm.txt"

    # start collecting logs
    adb -s $device logcat > "$logcat" &
    PID_LOGCAT=$!

    # start recording video
    adb -s $device shell screenrecord --bit-rate 6000000 "/sdcard/recording.mp4" &
    PID_RECORDING=$!

    # parse to get param (in case of params)
    if [[ $line == *":"* ]]; then
        # attach index param
        testArr=(${line//:/ })
        test=${testArr[0]}
        index=${testArr[1]}
        adb -s $device shell am instrument -w -e class $test -e paramIndex $index $package.test/android.support.test.runner.AndroidJUnitRunner > $runningTest
    else
        # run as usual
        echo "$line"
        startTime=$(node -e 'console.log(Date.now())')
        adb -s $device shell am instrument -w -e class $line $package.test/android.support.test.runner.AndroidJUnitRunner > $runningTest
        endTime=$(node -e 'console.log(Date.now())')
        echo "test ($line) device ($device) , duration: $((endTime-startTime)) millis."
        echo "test ($line) device ($device) , duration: $((endTime-startTime)) millis." >> "$outputDir/times.txt"
    fi

    # kill logcat process
    kill $PID_LOGCAT
    sleep 1

    # kill recording process
    kill $PID_RECORDING
    sleep 3

     # pull and remove recording from device
    adb -s $device pull "/sdcard/recording.mp4" $recording
    adb -s $device shell rm "/sdcard/recording.mp4"

    # SCAN for errors
    shortReason=''
    if grep -q "FAILURES!!!" $runningTest; then
        shortReason=$(head -5 "$runningTest")
        shortReason=${shortReason//[$'\t\r\n ' / ]}
        shortReason=${shortReason//[$'\t\r\n']}
    fi

    # if 'shortReason' is not empty, then we found a bug!
    if [ ! -z "$shortReason" ] ; then

        # dump db
        adb -s $device shell "run-as $package cat /data/data/$package/databases/app.db" > $db

        # extract preferences
        adb -s $device shell "run-as $package cat /data/data/$package/shared_prefs/'$package'_preferences.xml" > $preferences

        # dump netstats, battery, alarms
        adb -s $device shell dumpsys netstats > $netstats
        adb -s $device shell dumpsys batterystats > $batterystats
        adb -s $device shell dumpsys alarm > $alarms

        # fail
        adb -s $device shell am force-stop $package
        echo "[x] FAIL"
        echo "$shortReason"
        echo ""

    else

        # remove dir if we are fine
        adb -s $device shell am force-stop $package
        rm -r $testDir
        echo "[v] OK"
        echo ""
    fi

done
