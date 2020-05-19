#!/bin/bash

now=$(date +%Y-%m-%dT%H:%M:%S)
nowExpiration=$(date -d "15 minutes" +%Y-%m-%dT%H:%M:%S)

yesterday=$(date -d "yesterday 23:59:59" '+%Y-%m-%dT%H:%M:%S')
tomorrow=$(date -d "tomorrow 23:59:59" '+%Y-%m-%dT%H:%M:%S')

[ -d test-results ] || mkdir test-results

echo $'http://localhost:8080/screenings/timebetween/'"$yesterday"'/'"$tomorrow" >> test-results/test-result-"$now".txt
curl 'http://localhost:8080/screenings/timebetween/'"$yesterday"'/'"$tomorrow" >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/screenings/2' >> test-results/test-result-"$now".txt
curl http://localhost:8080/screenings/2 >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/seats/free/by_screening/2' >> test-results/test-result-"$now".txt
curl http://localhost:8080/seats/free/by_screening/2 >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/persons --data {"id":10,"firstName":"Franciszek","surname":"Test"}' >> test-results/test-result-"$now".txt
curl --header "Content-Type: application/json" --request POST --data '{"id":10,"firstName":"Franciszek","surname":"Test"}' http://localhost:8080/persons >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/reservations --data {"id":11,"personId":"10","orderTime":"'"$now"'","expirationTime":"'"$nowExpiration"'","isPaid":false,"discount":false,"tickets":[{"id":21,"reservationId":11,"seat":{"id":30,"room":{"id":1,"number":1},"seatRow":"VI","seatPlace":5},"screeningId":2,"price":25}]}' >> test-results/test-result-"$now".txt
curl --header "Content-Type: application/json" --request POST --data '{"id":11,"personId":"10","orderTime":"'"$now"'","expirationTime":"'"$nowExpiration"'","isPaid":false,"discount":false,"tickets":[{"id":21,"reservationId":11,"seat":{"id":30,"room":{"id":1,"number":1},"seatRow":"VI","seatPlace":5},"screeningId":2,"price":25}]}' http://localhost:8080/reservations >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/tickets/by_reservation/11' >> test-results/test-result-"$now".txt
curl http://localhost:8080/tickets/by_reservation/11 >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/reservations/11' --data '{"id":11,"discountCode":"Tested_code_123*"}' >> test-results/test-result-"$now".txt
curl --header "Content-Type: application/json" --request PATCH --data '{"id":11,"discountCode":"Tested_code_123*"}' http://localhost:8080/reservations/11 >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt

echo $'http://localhost:8080/reservations/11/price' >> test-results/test-result-"$now".txt
curl http://localhost:8080/reservations/11/price >> test-results/test-result-"$now".txt
echo $'\n' >> test-results/test-result-"$now".txt