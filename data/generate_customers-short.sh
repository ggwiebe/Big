ctype[0]="bronze"
ctype[1]="silver"
ctype[2]="gold"
ctype[3]="platinum"
ctype[4]="diamond"

echo "SequenceNum,UUID,UpdateDT,FullName,Description,CustomerType,Password"
while read p; do
  # petname is TOOOOO slow!!!
  #echo "$p,$(uuidgen),$(date '+%Y-%m-%d'T'%H:%M:%S.%N'),$(petname),Full Name for $p,Description for user with id: $p"
  echo "$p,$(uuidgen),$(date '+%Y-%m-%d'T'%H:%M:%S.%N'),Full Name for $p,Description for user with id: $p,${ctype[$((RANDOM % 5))]},$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 13 ; echo '')"
done <seq_short.csv
