#!/bin/bash

echo GetRestStatus.bash

#!/bin/bash

#-------------------------------------------------------------
# Example script to call restserver API
# adminuser
#-------------------------------------------------------------

curl -H "Content-Type: application/json"  \
        -X POST http://localhost:8080/event \
        -u master@stingraydb.io:stingraypw \
-d  @- <<'EOF'

{  "tenant_name":"Tenant 3",
    "user_id":"paul",
    "password":"donald",
    "role":"ADMIN"
}

EOF


