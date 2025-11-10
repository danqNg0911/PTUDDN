Nguyễn Hữu Hải Đăng - 23020524
Tuần 10 : MongoDB - Replication và Sharding

1. Replication

      Status : 1 primary port 27017, 2 secondary 27018 và 27019

      Test với dữ liệu

      Priamry : port 27017

      Secondary 1 : port 27018

      Secondary 2 : port 27019

2. Sharding

      Status : 3 config server : primary port 26051, secondary port (26052, 26053), 2 Shard Replica Set, mỗi shard có 2 node (27021->27024), và 1 Routing Server (mongos) để ứng dụng hoặc người dùng truy vấn (27020)

      Test với dữ liệu
   


