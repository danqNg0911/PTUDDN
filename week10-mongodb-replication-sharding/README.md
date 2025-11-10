Nguyễn Hữu Hải Đăng - 23020524
Tuần 10 : MongoDB - Replication và Sharding

1. Replication

      **Status : 1 primary port 27017, 2 secondary 27018 và 27019**
      ![h1](https://raw.githubusercontent.com/danqNg0911/PTUDDN/main/img/week10/rep-1.png)

      **Test với dữ liệu**

      Priamry : port 27017 (hình trên)

      Secondary 1 : port 27018
      ![h1](https://raw.githubusercontent.com/danqNg0911/PTUDDN/main/img/week10/rep-2.png)

      Secondary 2 : port 27019
      ![h1](https://raw.githubusercontent.com/danqNg0911/PTUDDN/main/img/week10/rep-3.png)

2. Sharding

      **Status** : 3 config server : primary port 26051, secondary port (26052, 26053), 2 Shard Replica Set, mỗi shard có 2 node (27021->27024), và 1 Routing Server (mongos) để ứng dụng hoặc người dùng truy vấn (27020)
      ![h1](https://raw.githubusercontent.com/danqNg0911/PTUDDN/main/img/week10/shard-1.png)
      ![h1](https://raw.githubusercontent.com/danqNg0911/PTUDDN/main/img/week10/shard-2.png)

      **Test với dữ liệu**

      
   


