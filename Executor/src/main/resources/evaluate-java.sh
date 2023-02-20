#!/bin/bash
file1=$1
file2=$2
i=$3
java -cp Executor/src/main/resources/temp_zip homework Executor/src/main/resources/temp_zip/inputs/$file1 Executor/src/main/resources/temp_zip/$file2
diff Executor/src/main/resources/temp_zip/outputs/output$3 Executor/src/main/resources/temp_zip/output$3 | wc -l > Executor/src/main/resources/temp_zip/isDiff

