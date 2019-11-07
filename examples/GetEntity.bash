#!/bin/bash

echo GetRestStatus.bash

#!/bin/bash

#-------------------------------------------------------------
# Example script to call restserver API
# adminuser
#-------------------------------------------------------------

curl -H "Content-Type: application/json"  \
        -X GET http://localhost:8080/entity?entity_id=127638 \
        -u master@stingraydb.io:stingraypw 


