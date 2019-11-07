#!/bin/bash

echo GetRestStatus.bash

#!/bin/bash

#-------------------------------------------------------------
# Example script to call restserver API
# adminuser
#-------------------------------------------------------------

curl -H "Content-Type: application/json"  \
        -X POST http://localhost:8080/entity \
        -u master@stingraydb.io:stingraypw \
-d  @- <<'EOF'
{  "tenant_name":"stingraydb.io",
    "entity_id":"127638",
    "entity_type":"device",
    "entity_description":"generic device",
    "entity_parent_id": "0",
    "device_ip_address" : "127.1.1.1"
}
EOF


