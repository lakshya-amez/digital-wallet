#!/usr/bin/env bash

mvn clean install
mvn exec:java -Dexec.mainClass="AntiFraud" -Dexec.args="paymo_input/batch_payment.csv paymo_input/stream_payment.csv paymo_output/output1.txt paymo_output/output2.txt paymo_output/output3.txt paymo_output/output4.txt paymo_output/output5.txt"