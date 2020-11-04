#echo "SequenceNum,UpdateDT,FullName,Street,City,State,Zip,PhoneNum,Comment"
while read p; do
  echo "$p,$(date '+%Y-%m-%d'T'%H:%M:%S.%N'),$(rig|tr  "\n" ','|sed 's/  /,/g')"
done < seq_continue-cont.csv
