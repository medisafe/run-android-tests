#!/bin/bash

# Created by sromku with ☕
rawTests=$1
planOutput=$2
package=$3
flag=$4
selectedTags=$5

if [ -z $rawTests ] || [ -z $planOutput ] || [ -z $package ] ; then
    echo "Missing params"
    exit 1
fi

if [ ! -z $flag ] && [ $flag != "-t" ]; then
    echo "We support only '-t' params"
    exit 1
fi

if [ -e $planOutput ]; then
    rm $planOutput
fi

# [a|b|c|d|e|f|g|h|i|j] - this is array of all available tests
testNames=()

# [a|a|a|b|c|c|c|c|c|d] - this is array of in the same length as of tests
classNames=()

# [same length as of tests]
annotations=()

# tags
tags=()

# total tests
totalTests=0

# total test groups
totalTestGroups=0

index=-1
while read p; do

    if [[ $p == "$package"* ]] ;
    then
        className="${p//[$'\t\r\n ']}"
        className=${className%:}
        continue
    fi

    if [[ $p == *"test="* ]] ;
    then
        arrIN=(${p//=/ })
        testName=${arrIN[2]}
        testName="${testName//[$'\t\r\n ']}"

        foundDuplicateTest="f"
        for i in "${!testNames[@]}"
        do
            # ooo, we found duplicated test name.. let's start checking if we need this test
            if [ "${testNames[$i]}" == "$testName" ] && [ "${classNames[$i]}" == "$className" ] ; then
                foundDuplicateTest="t"
                break
            fi
        done

        if [ $foundDuplicateTest == "f" ] ; then
            testNames+=("$testName")
            classNames+=("$className")
            index=$((index + 1))
        fi

        continue
    fi

    if [[ $p == *"annotations="* ]] ;
    then
        arrIN=(${p//=/ })
        annotation=${arrIN[2]}
        annotation="${annotation//[$'\t\r\n ']}"
        annotations+=("$annotation")
        continue
    fi

    if [[ $p == *"tags="* ]] ;
    then
        arrIN=(${p//=/ })
        tag=${arrIN[2]}
        tag="${tag//[$'\t\r\n ']}"
        tags+=("$tag")
        continue
    fi

done < "$rawTests"

for i in "${!classNames[@]}"
do
    # flags we can set
    clearData=false
    clearNotifications=false
    repeat=0
    following=false

    # if we passed tags, then by default the test isn't selected
    if [ ! -e $selectedTags ]; then
        selected=false
    else
        selected=true
    fi

    # check if we have repeat, clear data and other flags
    annotation=${annotations[$i]}
    anns=(${annotation//,/ })
    for a in "${anns[@]}"
    do
        # check for clear data
        if [ "$a" == "ClearData" ] ; then
            clearData=true
        fi

        # check for clear notifications
        if [ "$a" == "ClearNotifications" ] ; then
            clearNotifications=true
        fi

        # check for number of repeats
        if [[ $a == "Repeat"* ]] ; then
            r=(${a//:/ })
            repeat=${r[1]}
            repeat=$((repeat - 1))

            # just to make sure that we didn't have negative repeat value
            if [ $repeat -lt 0 ]; then
                repeat=0
            fi
        fi

        # if we choose tags for this test
        if [[ $a == "Tags"* ]] && [ ! -e $selectedTags ] ; then

            # we check for intersection between test tags and selected tags
            tagsAArr=(${tags[$i]//,/ })
            tagsBArr=(${selectedTags//,/ })
            tagsCArr=(`echo ${tagsAArr[@]} ${tagsBArr[@]} | tr ' ' '\n' | sort | uniq -d | tr '\n' ' '`)
            num=${#tagsCArr[@]}
            # if number of intersected tags is more than 0, then we choose this test
            if [ $num -gt 0 ]; then
                selected=true
            else
                selected=false
            fi

        fi

        # check for clear data
        if [ "$a" == "Following" ] ; then
            following=true
        fi

    done

    # if test wasn't selected by tags, then continue to next test
    if ! $selected; then
        continue
    fi

    # by default it will run only once, unless we set repeat for parameterized tests
    for j in $(seq 0 $repeat)
    do

        # separate
        if ! $following; then
            echo "~~~" >> $planOutput
            # total test groups =+1
            totalTestGroups=$((totalTestGroups + 1))
        fi

        # if we need to clear data
        if $clearData; then
            echo "clearData" >> $planOutput
        fi

        # if we need to clear notifications
        if $clearNotifications; then
            echo "clearNotifications" >> $planOutput
        fi

        # print executable test name
        fullTest="${classNames[$i]}#${testNames[$i]}"

        # if we have repeat value, then pass it as well
        if [ $repeat -gt 0 ]; then
            fullTest="$fullTest:$j"
        fi

        # the execution ling
        echo "$fullTest" >> $planOutput

        # total tests =+1
        totalTests=$((totalTests + 1))

    done

done

# total tests
cat $planOutput
echo "TOTAL_TESTS=$totalTests"
echo "TOTAL_TEST_GROUPS=$totalTestGroups"
