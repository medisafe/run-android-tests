#!/bin/bash

# Created by sromku with ☕
rawTests=$1
package=$2

adb shell am instrument -w -r -e log true -e package $package.all -e listener $package.AnnotationsTestPrinter $package.test/androidx.test.runner.AndroidJUnitRunner > $rawTests
