testNmb=$1
echo -n "Score.........." >> Executor/src/main/resources/temp_zip/results
echo -n $(cat Executor/src/main/resources/temp_zip/results | grep Passed | wc -l) >> Executor/src/main/resources/temp_zip/results
echo -n "/" >> Executor/src/main/resources/temp_zip/results
echo -n $testNmb >> Executor/src/main/resources/temp_zip/results
