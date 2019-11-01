#!/bin/bash

#-------------------------------------------------------------
# Example script to call restserver API
# adminuser
#-------------------------------------------------------------

uuid=$(cat ".uuid")
hostname=$(hostname)
systemtime=$(date)

if [ -z "$uuid" ] 
	then  
		uuid=$(uuidgen) 
		echo $uuid >> .uuid
	fi

curl -H "Content-Type: application/json"  \
        -X POST http://localhost:8080/heartbeat \
        -u master@stingraydb.io:stingraypw \
-d  @<(cat <<EOF

{   "tenant_name": "stingraydb.io", 
    "name":"master@singraydb.io",
    "uuid":"$uuid",
    "hostname":"$hostname",
    "systemtime":"$systemtime",
    "message":"Hello world"
}

EOF
)


