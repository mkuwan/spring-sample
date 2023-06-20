#!/bin/bash
mongoimport --db appdb --collection customer --drop \
            --file /docker-entrypoint-initdb.d/01_import_init_data.json \
            --jsonArray