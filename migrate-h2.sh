#!/usr/bin/env bash
java -cp libraries/com/h2database/h2/1.4.200/h2-1.4.200.jar org.h2.tools.Script -url jdbc:h2:./storage/picojobs-h2 -script ./storage/picojobs-h2.zip -options compression zip