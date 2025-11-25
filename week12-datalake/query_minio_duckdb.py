import duckdb

BUCKET = "datalake"
OBJECT = "psam_p40.csv"  

con = duckdb.connect()

con.execute("INSTALL httpfs;")
con.execute("LOAD httpfs;")

con.execute("SET s3_endpoint='localhost:9000';")
con.execute("SET s3_use_ssl=false;")
con.execute("SET s3_url_style='path';")  
con.execute("SET s3_access_key_id='minioadmin';")
con.execute("SET s3_secret_access_key='minioadmin123';")


query = f"""
    SELECT PUMA, REGION, ST
    FROM read_csv_auto('s3://{BUCKET}/{OBJECT}')
    LIMIT 5
"""

print("\n== 5 dòng đầu tiên ==")
print(con.execute(query).fetchdf())
