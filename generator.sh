I=0

while [ $I -lt 2877 ]
do
	cat string.txt >> a
	I=`expr $I + 1`
done
